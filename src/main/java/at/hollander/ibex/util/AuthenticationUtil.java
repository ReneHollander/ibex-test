package at.hollander.ibex.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

public class AuthenticationUtil {

    private static final List<GrantedAuthority> GRANTED_AUTHORITIES_USER = Collections.singletonList(new SimpleGrantedAuthority("USER"));
    private static final List<GrantedAuthority> GRANTED_AUTHORITIES_ADMIN = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));

    public static boolean isUser(Authentication authentication) {
        return authentication.getAuthorities().equals(GRANTED_AUTHORITIES_USER);
    }

    public static boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().equals(GRANTED_AUTHORITIES_ADMIN);
    }

    public static boolean isUser() {
        return isUser(SecurityContextHolder.getContext().getAuthentication());
    }

    public static boolean isAdmin() {
        return isAdmin(SecurityContextHolder.getContext().getAuthentication());
    }

}
