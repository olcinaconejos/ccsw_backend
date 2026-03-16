package org.practicas.backCapgemini.prestamo.repos;

import org.practicas.backCapgemini.prestamo.model.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PrestamoRepository extends CrudRepository<Prestamo, Long>, JpaSpecificationExecutor<Prestamo> {
    @EntityGraph(attributePaths = { "clName", "gameName", "startDate", "endDate" })
    List<Prestamo> findAll(Specification<Prestamo> spec);

    Page<Prestamo> findAll(Pageable pageable);

    @Query("SELECT p FROM Prestamo p WHERE p.gameName = :gameName AND p.startDate <= :endDate AND p.endDate >= :startDate")
    List<Prestamo> findOverlappingByGame(@Param("gameName") String gameName, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM Prestamo p WHERE p.clName = :clName AND p.startDate <= :endDate AND p.endDate >= :startDate")
    List<Prestamo> findOverlappingByClient(@Param("clName") String clName, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
