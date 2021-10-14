package com.simpaisa.portal.config.security;

import com.simpaisa.portal.entity.mongo.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Component

public class JwtTokenProvider {
    private String secretKey = "secret";
//    private long validityInMilliSeconds = 0;
   // private long refreshTokenInMilliSeconds = 3600000;
    //60000
    private long validityInMilliSeconds = 3600000;
        //3600000;
    @Autowired
    UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, Set<Role> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime()+ validityInMilliSeconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(Claims claims){

      try {
          claims.getExpiration();
//        Claims claims = Jwts.claims().setSubject(username);
//        claims.put("roles", roles);
          Date now = new Date();
          Date validity = new Date(now.getTime() + validityInMilliSeconds);
          claims.setExpiration(validity);

          return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity).signWith(SignatureAlgorithm.HS256, secretKey)
                  .compact();
      }catch (Exception ex){
//          ex.printStackTrace();
      }
      return "not-expired";

    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, HttpServletRequest servletRequest){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }

            String requestUrl = servletRequest.getRequestURI().toString();
            if(requestUrl.contains("refreshtoken")){
                allowRefreshToken(claims.getBody(), servletRequest);
            }
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");


            //httpServletRequest.setAttribute("expired",ex.getMessage());
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT exception");
        }catch (IllegalArgumentException ex){
            System.out.println("Jwt claims string is empty");
        }
        return false;

    }

    private void allowRefreshToken(Claims claims, HttpServletRequest httpServletRequest) {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(user);
        httpServletRequest.setAttribute("claims",claims);
        System.out.println("ex.getClaims() = " +claims.toString());
    }
}
