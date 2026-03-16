package org.practicas.backCapgemini.client.repos;

import org.practicas.backCapgemini.client.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByName(String name);
}
