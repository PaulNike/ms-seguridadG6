package com.codigo.ms_seguridad.controller;

import com.codigo.ms_seguridad.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/v1/")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/saludo")
    public ResponseEntity<String> getTodos(){
        return ResponseEntity.ok("Hola Admin!!!");
    }

}
