package com.codigo.ms_seguridad.service;

import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entities.Usuario;

import java.util.List;

public interface AuthenticationService {
    //SIGNUP -> INSCRIBIRSE
    Usuario signUpUser(SignUpRequest signUpRequest);
    Usuario signUpAdmin(SignUpRequest signUpRequest);
    List<Usuario> todos();

    //SIGNIN -> INICIAR SESION
    SignInResponse signIn(SignInRequest signInRequest);

    //validar token externos
    boolean validateToken(String token);

}
