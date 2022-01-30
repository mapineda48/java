package ocho.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

import ocho.entity.User;

@Data
@AllArgsConstructor
public class UserSecurity implements UserDetails {
    
    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public boolean canDoCRUD(User user){
        return hasRole("ROLE_ADMIN") || user.getEmail() == this.user.getEmail();
    }

    public boolean hasRole(String role) {
        return authorities.stream()
                .anyMatch(r -> r.getAuthority().equals(role));
    }

    public static UserSecurity build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        var role = user.getRole();

        var simpleGrantedAuthority = new SimpleGrantedAuthority(role);

        authorities.add(simpleGrantedAuthority);

        return new UserSecurity(user, authorities);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
