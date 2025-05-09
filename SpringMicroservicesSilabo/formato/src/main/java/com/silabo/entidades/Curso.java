package com.silabo.entidades;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;
    private String nombre;
    private String tipo;
    private Integer numHorasTeoria;
    private Integer numHorasPractica;
    private Integer numHorasLaboratorio;
    private Integer numHorasCampo;

    @Column(name = "numCreditos")
    private BigDecimal numCreditos;

    private String ciclo;
    private String estado;

    private Integer periodoacademicoid;
    private Integer planestudiosid;
    private Integer institucionid;
    private Integer departamentoid;

    private String sumilla;
    private String modalidad;
    private String etiquetas;

    @ManyToMany
    @JoinTable(
        name = "cursocompetencia",
        joinColumns = @JoinColumn(name = "cursoid"),
        inverseJoinColumns = @JoinColumn(name = "competenciaid")
    )
    private List<Competencia> competencias;


}
