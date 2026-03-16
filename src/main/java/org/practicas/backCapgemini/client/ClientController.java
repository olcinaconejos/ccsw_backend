package org.practicas.backCapgemini.client;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.practicas.backCapgemini.client.model.ClientDto;
import org.practicas.backCapgemini.client.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Client", description = "API of Client")
@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper mapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ClientDto> findAll() {
        var clients = this.clientService.findAll();
        return clients.stream().map(x -> mapper.map(x, ClientDto.class)).toList();
    }

    @RequestMapping(value = { "", "{id}" }, method = RequestMethod.PUT)
    public ResponseEntity<?> save(@PathVariable(required = false) Long id, @RequestBody ClientDto dto) {
        try {
            this.clientService.save(id, dto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) throws Exception {
        this.clientService.delete(id);
    }
}
