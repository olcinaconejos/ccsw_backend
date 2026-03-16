package org.practicas.backCapgemini.prestamo;

import org.modelmapper.ModelMapper;
import org.practicas.backCapgemini.prestamo.model.PrestamoDto;
import org.practicas.backCapgemini.prestamo.model.PrestamoSearchDto;
import org.practicas.backCapgemini.prestamo.services.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {
    @Autowired
    PrestamoService prestamoService;

    @Autowired
    ModelMapper mapper;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<PrestamoDto> findWithFilters(@RequestBody(required = true) PrestamoSearchDto dto, @RequestParam(required = false) String gameName, @RequestParam(required = false) String clientName,
            @RequestParam(name = "date", required = false) String date) {
        LocalDateTime actualDate = null;

        if (!date.equals("null")) {
            actualDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        }

        var page = prestamoService.find(dto, gameName, clientName, actualDate);
        return new PageImpl<>(page.getContent().stream().map(e -> mapper.map(e, PrestamoDto.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<PrestamoDto> findAll() {
        return prestamoService.findAll().stream().map(p -> mapper.map(p, PrestamoDto.class)).toList();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public PrestamoDto get(Long id) {
        var p = prestamoService.get(id);
        return mapper.map(p, PrestamoDto.class);
    }

    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public ResponseEntity<?> save(@PathVariable(required = false) Long id, @RequestBody PrestamoDto dto) {
        try {
            prestamoService.save(id, dto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) throws Exception {
        prestamoService.deleteById(id);
    }
}
