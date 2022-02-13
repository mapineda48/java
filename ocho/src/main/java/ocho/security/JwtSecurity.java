package ocho.security;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ocho.entity.User;

@Component
public class JwtSecurity {
    @Value("${app.jwt.key}")
    private String jwtSecret;

    private Long jwtExpToken;
    private Long jwtExpRefresh;

    JwtSecurity(@Value("${app.jwt.exp.token}") int jwtExpToken, @Value("${app.jwt.exp.refresh}") int jwtExpRefresh){

        // Parse Hours to milliseconds
        this.jwtExpToken = TimeUnit.HOURS.toMillis(jwtExpToken);
        this.jwtExpRefresh = TimeUnit.HOURS.toMillis(jwtExpRefresh);
    }

    public HashMap<String, String> signin(User user) {
        var email = user.getEmail();

        if (email == null) {
            return null;
        }

        String acces_token = createAccessToken(email);

        String refresh_token = createRefreshToken(email);

        var tokens = new HashMap<String, String>();

        tokens.put("accessToken", acces_token);

        tokens.put("refreshToken", refresh_token);

        return tokens;
    }

    private String createToken(String payload, Long ms) {
        var algorithm = getAlgorithm();

        var time = System.currentTimeMillis();

        var exp = new Date(time + ms);

        return JWT.create()
                .withSubject(payload)
                .withExpiresAt(exp)
                .sign(algorithm);
    }

    private String createAccessToken(String payload) {
        return createToken(payload, jwtExpToken);
    }

    private String createRefreshToken(String payload) {
        return createToken(payload, jwtExpRefresh);
    }

    public String refreshToken(String token) {
        var email = verify(token);

        return createAccessToken(email);
    }

    public String verify(String token) {
        var algorithm = getAlgorithm();

        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        var username = decodedJWT.getSubject();

        return username;
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }
}
