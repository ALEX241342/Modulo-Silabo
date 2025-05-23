package com.silabo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.silabo.entidades.Silabo;
import java.util.Optional;

public interface SilaboRepository extends JpaRepository<Silabo, Integer> {
        Optional<Silabo> findByIdSilabo(int id);
       
}
