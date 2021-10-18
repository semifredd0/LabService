package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

    Optional<Laboratorio> findById(Long id);

    @Transactional
    @Modifying
    @Query("INSERT INTO laboratorio(codice_iban, indirizzo_stradale, nome_laboratorio, partita_iva, numero_telefono) VALUES ( ?, ?, ?, ?, ?)")
    void addLaboratorio(Laboratorio laboratorio);
}
