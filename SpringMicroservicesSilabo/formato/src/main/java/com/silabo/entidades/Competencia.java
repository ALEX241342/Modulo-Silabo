package com.silabo.entidades;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "competencia")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    private Integer planid;

    private Integer institucionid;

    private Integer departamentoid;

    @Column(nullable = false, length = 2)
    private String tipo;


}
