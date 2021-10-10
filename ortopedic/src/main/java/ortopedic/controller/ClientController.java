package ortopedic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ortopedic.entity.Client;
import ortopedic.service.ClientService;

@RestController
@RequestMapping("/api/Client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Client> getById(@PathVariable("id") Integer id) {
        return clientService.getClient(id);
    }

    @PostMapping("/save")
    public Client save(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/update")
    public Client update(@RequestBody Client client) {
        return clientService.update(client);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return clientService.delete(id);
    }

}
