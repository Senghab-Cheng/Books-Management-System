package kh.edu.paragoniu.librarymanagementsystem.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class RoleAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String ADMIN_DASHBOARD = "/admin/dashboard";
    private static final String USER_DASHBOARD = "/user/dashboard";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String expectedRole = request.getParameter("expectedRole");
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        String actualRole = isAdmin ? "ADMIN" : "USER";
        if (expectedRole != null && !expectedRole.isBlank() && !expectedRole.equalsIgnoreCase(actualRole)) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            String loginPath = "ADMIN".equalsIgnoreCase(expectedRole) ? "/login/admin" : "/login/user";
            response.sendRedirect(loginPath + "?error=role_mismatch");
            return;
        }

        response.sendRedirect(isAdmin ? ADMIN_DASHBOARD : USER_DASHBOARD);
    }
}
