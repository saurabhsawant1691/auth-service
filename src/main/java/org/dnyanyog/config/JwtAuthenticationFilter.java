package org.dnyanyog.config;

import java.io.IOException;

import org.dnyanyog.service.JwtService;
import org.dnyanyog.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
		) throws ServletException, IOException {

		String jwt = parseJwt(request);

		if (jwt != null) {
			try {
				String username = jwtService.extractUsername(jwt);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);

					if (jwtService.isTokenValid(jwt, userDetails)) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
						
						// Add token info to response headers for debugging
						response.setHeader("X-Token-Valid", "true");
						response.setHeader("X-Token-User", username);
					}
				}
			} catch (ExpiredJwtException e) {
				request.setAttribute("expired", e.getMessage());
				response.setHeader("X-Token-Error", "EXPIRED");
			} catch (SignatureException e) {
				request.setAttribute("invalid", e.getMessage());
				response.setHeader("X-Token-Error", "INVALID_SIGNATURE");
			} catch (MalformedJwtException e) {
				request.setAttribute("malformed", e.getMessage());
                response.setHeader("X-Token-Error", "MALFORMED");
			} catch (UsernameNotFoundException e) {
				request.setAttribute("userNotFound", e.getMessage());
			} catch (IllegalArgumentException e) {
				request.setAttribute("illegal", e.getMessage());
                response.setHeader("X-Token-Error", "EMPTY_CLAIMS");
			} catch (Exception e) {
				request.setAttribute("exception", e.getMessage());
                response.setHeader("X-Token-Error", "UNKNOWN_ERROR");
			}
		} else {
			response.setHeader("X-Token-Status", "NOT_PROVIDED");
		}

		filterChain.doFilter(request, response);
	}

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        
        return null;
    }
}
