package com.company.media.security.jwt;

import com.company.media.security.user.UserDetailService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final @Lazy JwtUtils jwtUtils;
    private final UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseJwt(request);
            if(StringUtils.hasText(token) && jwtUtils.isTokenValid(token)){
                String username = jwtUtils.getUsernameFromToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                var auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(" : Invalid or expired token");
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
        }
        filterChain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request){
       String authHeader = request.getHeader("Authorization");
       if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
           return authHeader.substring(7);
       }
       return null;
    }
}
