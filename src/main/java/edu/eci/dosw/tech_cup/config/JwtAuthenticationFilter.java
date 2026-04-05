package edu.eci.dosw.tech_cup.config;

import edu.eci.dosw.tech_cup.entity.user.RoleEntity;
import edu.eci.dosw.tech_cup.entity.user.UserEntity;
import edu.eci.dosw.tech_cup.repository.UserRepository;
import edu.eci.dosw.tech_cup.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            String userEmail = jwtService.extractSubject(token);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<UserEntity> userOpt = userRepository.findByEmail(userEmail);
                if (userOpt.isPresent()) {
                    UserEntity user = userOpt.get();
                    if (user.isActive() && jwtService.isAccessTokenValid(token, user)) {

                        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                                .map(RoleEntity::getName)
                                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                                .collect(Collectors.toList());

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userEmail,
                                        null,
                                        authorities
                                );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception ignored) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}