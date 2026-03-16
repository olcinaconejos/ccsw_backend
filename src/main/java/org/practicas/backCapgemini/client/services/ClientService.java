package org.practicas.backCapgemini.client.services;

import org.practicas.backCapgemini.client.model.Client;
import org.practicas.backCapgemini.client.model.ClientDto;

import java.util.List;

public interface ClientService {
    Client get(Long id);

    List<Client> findAll();

    void save(Long id, ClientDto dto);

    void delete(Long id) throws Exception;
}
