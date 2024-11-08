package Talataa.E_Commerce.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter /*extends OncePerRequestFilter */{

    /*private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final UserDetailsService jwtUserDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public JwtRequestFilter(UserDetailsService jwtUserDetailsService, JwtUtil jwtUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Excluir la ruta /security/authenticate del filtro
        if ("/security/authenticate".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }


        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null) {
            if (requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
            } else {
                jwtToken = requestTokenHeader;
            }
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());


            } catch (ExpiredJwtException e) {
                System.out.println(e.getMessage());

            }
        } else {
            System.out.println("JWT Token not found in request headers");

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
    */

}
