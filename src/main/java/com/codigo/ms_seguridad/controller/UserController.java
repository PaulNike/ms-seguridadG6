package com.codigo.ms_seguridad.controller;

import com.codigo.ms_seguridad.entities.Usuario;
import com.codigo.ms_seguridad.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/v1/")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @GetMapping("/todos")
    public ResponseEntity<List<Usuario>> getTodos(){
        return ResponseEntity.ok(authenticationService.todos());
    }

}
