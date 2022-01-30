package ocho.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FilterSecurity extends OncePerRequestFilter {
    private static final String PREFIX_TOKEN = "Bearer ";

    @Autowired
    private JwtSecurity jwtSecurity;

    @Autowired
    private SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            setRoles(request);
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private void setRoles(HttpServletRequest request) {
        var token = getJwtFromHeader(request);

        if (token == null) {
            log.info("skip set role");

            return;
        }

        var email = jwtSecurity.verify(token);

        UserSecurity userDetails = securityService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities());

        var webAuthenticationDetailsSource = new WebAuthenticationDetailsSource().buildDetails(request);

        authentication.setDetails(webAuthenticationDetailsSource);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        var headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (headerAuth == null) {
            log.info("missing header");
            return null;
        }

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(PREFIX_TOKEN)) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
