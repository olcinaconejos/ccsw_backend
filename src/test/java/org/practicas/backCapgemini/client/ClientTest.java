package org.practicas.backCapgemini.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.practicas.backCapgemini.client.model.Client;
import org.practicas.backCapgemini.client.model.ClientDto;
import org.practicas.backCapgemini.client.repos.ClientRepository;
import org.practicas.backCapgemini.client.services.ClientServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void findAllShouldReturnAllClients() {
        var list = new ArrayList<Client>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> clients = clientService.findAll();
        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    private final String CL_NAME = "Eduardo";
    private final Long CL_ID = 1L;

    @Test
    public void saveExistingClientShouldUpdate() {
        var dto = new ClientDto();
        dto.setName(CL_NAME);

        var client = mock(Client.class);
        when(clientRepository.findById(CL_ID)).thenReturn(Optional.of(client));

        clientService.save(CL_ID, dto);

        verify(clientRepository).save(client);
    }

    @Test
    public void saveNotExistingClientShouldUpdate() {
        var dto = new ClientDto();
        dto.setName(CL_NAME);

        var client = ArgumentCaptor.forClass(Client.class);
        clientService.save(null, dto);

        verify(clientRepository).save(client.capture());

        assertEquals(CL_NAME, client.getValue().getName());
    }

    @Test
    public void deleteExistingClientShouldDelete() throws Exception {
        var client = mock(Client.class);

        clientRepository.deleteById(CL_ID);

        verify(clientRepository).deleteById(CL_ID);
    }

    @Test
    public void getExistingClientShouldReturnClient() {
        var client = mock(Client.class);

        when(client.getId()).thenReturn(CL_ID);
        when(clientRepository.findById(CL_ID)).thenReturn(Optional.of(client));

        var clientResponse = clientService.get(CL_ID);

        assertNotNull(clientResponse);
        assertEquals(clientResponse.getId(), CL_ID);
    }

    @Test
    public void getNotExistingClientShouldReturnNull() {
        final Long NOT_EXISTING_ID = 3L;

        when(clientRepository.findById(NOT_EXISTING_ID)).thenReturn(Optional.empty());

        Client client = clientService.get(NOT_EXISTING_ID);

        assertNull(client);
    }
}
