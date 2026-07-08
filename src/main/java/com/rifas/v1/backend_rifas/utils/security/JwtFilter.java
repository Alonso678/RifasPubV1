package com.rifas.v1.backend_rifas.utils.security;

import com.rifas.v1.backend_rifas.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        final String path = request.getRequestURI();

        // Permitir el acceso a las rutas de autenticación sin necesidad de token
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Si no viene el token en los Headers, ignoramos y continuamos el flujo de filtros
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraemos el string del token quitando los primeros 7 caracteres ("Bearer ")
        jwt = authHeader.substring(7);
        userEmail = jwtUtils.extractEmail(jwt);

        // 3. Si viene un email válido y el usuario no está ya autenticado en el contexto actual
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            System.out.println("====== FILTRO JWT INICIADO ======");
            System.out.println("Email detectado en el Token: " + userEmail);

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println("Usuario encontrado en BD: " + userDetails.getUsername());

            // 4. Si el token coincide matemáticamente con los datos de la BD y está vigente
            if (jwtUtils.validateToken(jwt, userDetails.getUsername())) {
                System.out.println("¡TOKEN VALIDADO CON ÉXITO MATEMÁTICO!");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 5. Autorizamos el acceso en el contenedor de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("❌ ERROR: El Token no es válido para este usuario o expiró");
            }
        }

        // Continuar con la petición
        filterChain.doFilter(request, response);
    }
}
