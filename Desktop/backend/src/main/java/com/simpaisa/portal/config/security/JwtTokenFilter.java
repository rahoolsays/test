package com.simpaisa.portal.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

            if (token != null && jwtTokenProvider.validateToken(token, (HttpServletRequest) servletRequest)) {
                Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");

            servletRequest.setAttribute("expired", ex.getMessage());
//            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//            String isRefreshToken = httpServletRequest.getHeader("isRefreshToken");
//            String requestUrl = httpServletRequest.getRequestURI().toString();
//
//            if(isRefreshToken!=null && isRefreshToken.equals("true") && requestUrl.contains("refreshtoken")){
//                allowRefreshToken(ex, httpServletRequest);
//            }else {
//
//            }
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT exception");
        }catch (IllegalArgumentException ex){
            System.out.println("Jwt claims string is empty");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void allowRefreshToken(ExpiredJwtException ex, HttpServletRequest httpServletRequest) {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(user);
        httpServletRequest.setAttribute("claims",ex.getClaims());
        System.out.println("ex.getClaims() = " + ex.getClaims().toString());
    }
}
