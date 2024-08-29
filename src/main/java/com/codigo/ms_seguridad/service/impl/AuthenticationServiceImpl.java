package com.codigo.ms_seguridad.service.impl;

import com.codigo.ms_seguridad.aggregates.constants.Constants;
import com.codigo.ms_seguridad.aggregates.request.SignInRequest;
import com.codigo.ms_seguridad.aggregates.request.SignUpRequest;
import com.codigo.ms_seguridad.aggregates.response.SignInResponse;
import com.codigo.ms_seguridad.entities.Rol;
import com.codigo.ms_seguridad.entities.Role;
import com.codigo.ms_seguridad.entities.Usuario;
import com.codigo.ms_seguridad.repository.RolRepository;
import com.codigo.ms_seguridad.repository.UsuarioRepository;
import com.codigo.ms_seguridad.service.AuthenticationService;
import com.codigo.ms_seguridad.service.JwtService;
import com.codigo.ms_seguridad.service.UsuarioService;
import com.codigo.ms_seguridad.util.AppUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Override
    @Transactional
    public Usuario signUpUser(SignUpRequest signUpRequest) {
        Usuario usuario = getUsuarioEntity(signUpRequest);
        //ASIGANDO ROL QUE EXISTA
        //Rol userRol = getRoles(Role.USER);
        //ASIGNADO FINALMENTE EL ROL ENCONTRADO AL USUARIO:
        usuario.setRoles(Collections.singleton(getRoles(Role.USER)));
        usuario.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario signUpAdmin(SignUpRequest signUpRequest) {
        Usuario admin = getUsuarioEntity(signUpRequest);
        //ASIGNADO FINALMENTE EL ROL ENCONTRADO AL USUARIO:
        admin.setRoles(Collections.singleton(getRoles(Role.ADMIN)));
        admin.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return usuarioRepository.save(admin);
    }

    private Usuario getUsuarioEntity(SignUpRequest signUpRequest) {
        Usuario newUser = new Usuario();
        newUser.setNombres(signUpRequest.getNombres());
        newUser.setApellidos(signUpRequest.getApellidos());
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setTipoDoc(signUpRequest.getTipoDoc());
        newUser.setNumDoc(signUpRequest.getNumDoc());
        newUser.setIsAccountNonExpired(Constants.ESTADO_ACTIVO);
        newUser.setIsAccountNonLocked(Constants.ESTADO_ACTIVO);
        newUser.setIsCredentialsNonExpired(Constants.ESTADO_ACTIVO);
        newUser.setIsEnabled(Constants.ESTADO_ACTIVO);
        return newUser;
    }

    @Override
    public List<Usuario> todos() {
        return usuarioRepository.findAll();
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),signInRequest.getPassword()));
        var user = usuarioRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("ERROR USUARIO NO ENCONTRADO"));
        var token = jwtService.generateToken(user);
        SignInResponse response = new SignInResponse();
        response.setToken(token);
        return response;
    }

    @Override
    public boolean validateToken(String token) {
        final String jwt;
        final String userEmail;
        if(AppUtil.isNotNullOrEmpty(token)){
            jwt = token.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            if(AppUtil.isNotNullOrEmpty(userEmail)){
                UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(userEmail);
                return jwtService.validateToken(jwt,userDetails);
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    private Rol getRoles(Role rolBuscado){
        return rolRepository.findByNombreRol(rolBuscado.name())
                .orElseThrow(() -> new RuntimeException("EL ROL BSUCADO NO EXISTE : "
                        + rolBuscado.name()));
    }
}
