package com.hashedin.config;

import com.hashedin.constants.Constants;
import com.hashedin.exceptions.Forbidden;
import com.hashedin.exceptions.TokenException;
import com.hashedin.exceptions.Unauthorized;
import com.hashedin.repository.UserInfoRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@Log4j2
public class JwtVerifier {

    @Value("${accessTokenSecret}")
    private String accessTokenSecret;

    @Autowired
    private UserInfoRepository userinforepository;

    /**
     * Generate New Token
     * @param userId - User Id
     * @return - New Access Token
     */
    public String generateToken(Long userId)
    {
        log.info("Generate Token");
        long expirationTime;
        String signingKey;
        expirationTime = 24*60*60*1000L;
        signingKey = accessTokenSecret;
        String id = String.valueOf(userId);
        return Jwts.builder().setSubject(id).setExpiration(new Date(System.currentTimeMillis()+(expirationTime))).signWith(SignatureAlgorithm.HS512, signingKey).compact();
    }

    /**
     * Check access token validity
     * @param token - Token Details
     * @param roleAuthorizedForThisEndpoint - Role passed from the interceptor
     * @return - User Id
     */
    public Long checkTokenValidity(String token, String roleAuthorizedForThisEndpoint)
    {
        log.info("Checking token validity");
        String userId = extractDataFromToken(token).getSubject();
        Long user = Long.parseLong(userId);
        String userRole = userinforepository.checkIfUserExists(user);
        if(userRole.equals(roleAuthorizedForThisEndpoint))
        {
            Date expirationDate = extractDataFromToken(token).getExpiration();

            if(expirationDate.after(new Date( System.currentTimeMillis())))
            {
                return user;
            }
            else
            {
                throw new Unauthorized(Constants.ROLE_UNAUTHORIZED);
            }
        }
        else
        {
            throw new Forbidden(Constants.ROLE_FORBIDDEN);
        }
    }

    /**
     * Extracting the claims from the token
     * @param token - extracting the data from the token(Authorization), i.e., ID of the user
     * @return - The token claims
     */
    public Claims extractDataFromToken(String token)
    {
        log.info("Extracting Data from token");
        String signingKey;
        signingKey = accessTokenSecret;
        try
        {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        }
        catch(ExpiredJwtException e)
        {
            return e.getClaims();
        }
        catch(Exception e)
        {
            throw new TokenException("Invalid Token Exception");
        }
    }
}
