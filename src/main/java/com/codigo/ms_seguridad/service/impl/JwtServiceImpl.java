package com.codigo.ms_seguridad.service.impl;

import com.codigo.ms_seguridad.aggregates.constants.Constants;
import com.codigo.ms_seguridad.entities.Role;
import com.codigo.ms_seguridad.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RefreshScope
public class JwtServiceImpl implements JwtService {


    @Value("${key.signature}")
    private String keySignature;
    @Override
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(addClaim(userDetails))
                .claim("ROLE",userDetails.getAuthorities())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 120000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

    //METODO APRA GENERAR MI LLAVE USANDO MI CLAVE DE LOS PROP
    // CON LA QUE VOY A FIRMAR MI TOKEN
    private Key getSignKey(){
        log.info("CLAVE PARA FIRMAR: " + keySignature);
        byte[] key = Decoders.BASE64.decode(keySignature);
        log.info("CLAVE LISTA PARA FIRMAR: " + Keys.hmacShaKeyFor(key));
        return Keys.hmacShaKeyFor(key);
    }

    //METODO PARA EXTRAER EL PAYLOAD (CLAIMS) DEL TOKEN
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResult){
        final Claims claims = extractAllClaims(token);
        return claimResult.apply(claims);
    }
    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    private Map<String,Object> addClaim(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put(Constants.CLAVE_AccountNonLocked,userDetails.isAccountNonLocked());
        claims.put(Constants.CLAVE_AccountNonExpired,userDetails.isAccountNonExpired());
        claims.put(Constants.CLAVE_CredentialsNonExpired,userDetails.isCredentialsNonExpired());
        claims.put(Constants.CLAVE_Enabled,userDetails.isEnabled());
        return claims;
    }

    //DIFERENCIA ENTRE GENERICOS Y REFERENCIALES.
    private <T> List<T> getLista(){
        return new ArrayList<>();
    }
    private String getnombre(){
        return "hola";
    }


}
