package org.practicas.backCapgemini.prestamo.services;

import org.practicas.backCapgemini.common.criteria.SearchCriteria;
import org.practicas.backCapgemini.prestamo.PrestamoSpecification;
import org.practicas.backCapgemini.prestamo.model.Prestamo;
import org.practicas.backCapgemini.prestamo.model.PrestamoDto;
import org.practicas.backCapgemini.prestamo.model.PrestamoSearchDto;
import org.practicas.backCapgemini.prestamo.repos.PrestamoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {
    @Autowired
    PrestamoRepository prestamoRepository;

    public Prestamo get(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    public Page<Prestamo> findPage(PrestamoSearchDto dto) {
        return this.prestamoRepository.findAll(dto.getPageable().getPageable());
    }

    public Page<Prestamo> find(PrestamoSearchDto dto, String gameName, String clientName, LocalDateTime date) {
        PrestamoSpecification gameNameSpec = null;
        PrestamoSpecification clientNameSpec = null;

        Specification<Prestamo> spec = null;
        if (!gameName.equals("null")) {
            gameNameSpec = new PrestamoSpecification(new SearchCriteria("gameName", ":", gameName));
            spec = gameNameSpec;
        }
        if (!clientName.equals("null")) {
            clientNameSpec = new PrestamoSpecification(new SearchCriteria("clName", ":", clientName));
            spec = spec == null ? clientNameSpec : spec.and(clientNameSpec);
        }

        List<Prestamo> prestamosTmp;
        if (spec != null) {
            prestamosTmp = this.prestamoRepository.findAll(spec);
        } else {
            prestamosTmp = new ArrayList<>();
            this.prestamoRepository.findAll().forEach(prestamosTmp::add);
        }

        List<Prestamo> prestamosFinal;
        if (date != null) {
            prestamosFinal = prestamosTmp.stream().filter(p -> !p.getStart().isAfter(date) && !p.getEnd().isBefore(date)).toList();
        } else {
            prestamosFinal = prestamosTmp;
        }

        var pageRequest = PageRequest.of(dto.getPageable().getPageNumber(), dto.getPageable().getPageSize());
        var pageStart = (int) pageRequest.getOffset();
        var pageEnd = (int) Math.min((pageStart + pageRequest.getPageSize()), prestamosFinal.size());
        var pageBuf = prestamosFinal.subList(pageStart, pageEnd);

        return new PageImpl<>(pageBuf, pageRequest, prestamosFinal.size());
    }

    public List<Prestamo> findAll() {
        var prestamos = new ArrayList<Prestamo>();
        prestamoRepository.findAll().forEach(prestamos::add);
        return prestamos;
    }

    public void save(Long id, PrestamoDto dto) {
        // Validation 1: end date must be >= start date
        if (dto.getEnd().isBefore(dto.getStart())) {
            throw new RuntimeException("La fecha de devolución debe ser posterior a la fecha de préstamo");
        }

        // Validation 2: max 14 days loan period
        long days = ChronoUnit.DAYS.between(dto.getStart(), dto.getEnd());
        if (days > 14) {
            throw new RuntimeException("El periodo de préstamo no puede superar los 14 días");
        }

        // Validation 3: same game cannot be loaned to 2 different clients in overlapping periods
        List<Prestamo> gameOverlaps = prestamoRepository.findOverlappingByGame(dto.getGameName(), dto.getStart(), dto.getEnd());
        gameOverlaps = gameOverlaps.stream().filter(p -> id == null || !p.getId().equals(id)).toList();
        if (!gameOverlaps.isEmpty()) {
            throw new RuntimeException("El juego ya está prestado en esas fechas");
        }

        // Validation 4: a client cannot have more than 2 simultaneous loans
        List<Prestamo> clientOverlaps = prestamoRepository.findOverlappingByClient(dto.getClName(), dto.getStart(), dto.getEnd());
        clientOverlaps = clientOverlaps.stream().filter(p -> id == null || !p.getId().equals(id)).toList();
        if (clientOverlaps.size() >= 2) {
            throw new RuntimeException("El cliente no puede tener más de 2 préstamos simultáneos");
        }

        Prestamo p;
        if (id == null) {
            p = new Prestamo();
        } else {
            p = this.get(id);
        }

        BeanUtils.copyProperties(dto, p);
        this.prestamoRepository.save(p);
    }

    public void deleteById(Long id) throws Exception {
        this.prestamoRepository.deleteById(id);
    }
}
