package es.nextdigital.demo.Model;

import lombok.Data;

@Data
public class Usuario {
    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
}

