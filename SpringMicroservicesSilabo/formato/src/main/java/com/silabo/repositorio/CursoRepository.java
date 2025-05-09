package com.silabo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silabo.entidades.Curso;
import java.util.Optional;


public interface CursoRepository extends JpaRepository<Curso, Integer>{
    Optional<Curso> findById(int id);
}

