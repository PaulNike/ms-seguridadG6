package com.codigo.ms_seguridad.polimultiple.impl;

import com.codigo.ms_seguridad.entities.Persona;
import com.codigo.ms_seguridad.polimultiple.PersonaService;
import com.codigo.ms_seguridad.polimultiple.PersonaService2;

public class PersonaService2Impl implements PersonaService2 {
    @Override
    public PersonaService crearPersona() {
        return new PersonaService() {
            @Override
            public Persona crearActor() {
                //CREO UNA PERSONA
                return null;
            }
        };
    }
}
