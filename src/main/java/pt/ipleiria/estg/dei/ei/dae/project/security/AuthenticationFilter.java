package pt.ipleiria.estg.dei.ei.dae.project.security;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import pt.ipleiria.estg.dei.ei.dae.project.ejbs.UserBean;

import javax.crypto.spec.SecretKeySpec;
import java.security.Principal;

@Provider
@Authenticated
@Priority(Priorities.AUTHENTICATION)

//Esta classe permite verificar se o token de autenticação é válido
//ou seja, se o utilizador que está a tentar aceder ao recurso é realmente quem diz ser
//validates if the user is authenticated
public class AuthenticationFilter implements ContainerRequestFilter {
    @EJB
    private UserBean userBean;
    @Context
    private UriInfo uriInfo;
    @Override
    public void filter(ContainerRequestContext requestContext) {
        var header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        // Get token from the HTTP Authorization header
        String token = header.substring("Bearer".length()).trim();
        var user = userBean.findOrFail(getUsername(token));
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return user::getEmail;
            }
            @Override
            public boolean isUserInRole(String s) {
                return org.hibernate.Hibernate.getClass(user).getSimpleName().equals(s);
            }
            @Override
            public boolean isSecure() {
                return uriInfo.getAbsolutePath().toString().startsWith("https");
            }
            @Override
            public String getAuthenticationScheme() { return "Bearer"; }
        });
    }

    private String getUsername(String token) {
        var key = new SecretKeySpec(
                TokenIssuer.SECRET_KEY, TokenIssuer.ALGORITHM);
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            throw new NotAuthorizedException("Invalid JWT");
        }
    }
}
