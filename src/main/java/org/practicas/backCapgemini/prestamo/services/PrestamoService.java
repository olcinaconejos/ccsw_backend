package org.practicas.backCapgemini.prestamo.services;

import org.practicas.backCapgemini.prestamo.model.Prestamo;
import org.practicas.backCapgemini.prestamo.model.PrestamoDto;
import org.practicas.backCapgemini.prestamo.model.PrestamoSearchDto;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface PrestamoService {
    List<Prestamo> findAll();

    Page<Prestamo> find(PrestamoSearchDto dto, String gameName, String clientName, LocalDateTime date);

    Page<Prestamo> findPage(PrestamoSearchDto dto);

    void save(Long id, PrestamoDto dto);

    void deleteById(Long id) throws Exception;

    Prestamo get(Long id);
}
