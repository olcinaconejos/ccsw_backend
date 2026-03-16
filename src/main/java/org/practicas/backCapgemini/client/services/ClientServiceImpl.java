package org.practicas.backCapgemini.client.services;

import org.practicas.backCapgemini.client.model.Client;
import org.practicas.backCapgemini.client.model.ClientDto;
import org.practicas.backCapgemini.client.repos.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client get(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> findAll() {
        var clients = new ArrayList<Client>();
        clientRepository.findAll().forEach(clients::add);
        return clients;
    }

    @Override
    public void delete(Long id) throws Exception {
        clientRepository.deleteById(id);
    }

    @Override
    public void save(Long id, ClientDto dto) {
        Client existing = clientRepository.findByName(dto.getName());
        if (existing != null && (id == null || !existing.getId().equals(id))) {
            throw new RuntimeException("Ya existe un cliente con el nombre: " + dto.getName());
        }

        Client client;
        if (id == null) {
            client = new Client();
        } else {
            client = this.get(id);
        }

        client.setName(dto.getName());
        this.clientRepository.save(client);
    }
}
