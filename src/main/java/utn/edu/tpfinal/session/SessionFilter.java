package utn.edu.tpfinal.session;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class SessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionManager sessionManager;

    private static final String userTypeClient = "client";

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String sessionToken = request.getHeader("Authorization");
        Session session = sessionManager.getSession(sessionToken);
        if (null != session) {
            if (userTypeClient.equals(session.getLoggedUser().getUserTypeString())) {
                filterChain.doFilter(request, response);
            } else {
                // Return 403 - Forbidden - The request contained valid data and was understood by the server, but the server is refusing action
                // In our case a backoffice token is being used to acces client side of the application.
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }else{
            // Return 401 - Unauthorized: when authentication is required and has failed or has not yet been provided
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}