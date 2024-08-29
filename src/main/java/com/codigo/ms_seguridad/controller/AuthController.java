package com.codigo.ms_seguridad.controller;

import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entities.Usuario;
import com.codigo.ms_seguridad.service.AuthenticationService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api/authentication/v1/")
@RequiredArgsConstructor
@RefreshScope
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signupuser")
    public ResponseEntity<Usuario> signUpUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpUser(signUpRequest));
    }
    @PostMapping("/signupadmin")
    public ResponseEntity<Usuario> signUpAdmin(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpAdmin(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @GetMapping("/saludoFree")
    public ResponseEntity<String> getTodos(){
        return ResponseEntity.ok("HOLA PERSONAJE ESTAS EN UN RUTA LIBRE!!!");
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("validate") String validate){
        return ResponseEntity.ok(authenticationService.validateToken(validate));
    }
    @GetMapping("/claveFirma")
    public ResponseEntity<String> getClaveFirma(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String dato = Base64.getEncoder().encodeToString(key.getEncoded());
        return ResponseEntity.ok(dato);
    }
}
