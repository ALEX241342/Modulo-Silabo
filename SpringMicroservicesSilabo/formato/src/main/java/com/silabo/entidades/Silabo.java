package com.silabo.entidades;

import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Silabo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_silabo")
    private Integer idSilabo;

    @Column(name = "nombre_documento_silabo", length = 100, unique = true)
    private String nombreDocumentoSilabo;

    
    
}
