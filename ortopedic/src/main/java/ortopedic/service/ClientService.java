package ortopedic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ortopedic.entity.Client;
import ortopedic.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    public Optional<Client> getClient(Integer id) {
        return clientRepository.getClient(id);
    }

    public Client save(Client client) {

        if (client.getIdClient() == null) {
            return clientRepository.save(client);
        }

        Optional<Client> res = clientRepository.getClient(client.getIdClient());

        if (res.isEmpty()) {
            return clientRepository.save(client);
        } else {
            return client;
        }
    }

    public Client update(Client client) {
        Optional<Client> res = clientRepository.getClient(client.getIdClient());

        if (!res.isEmpty()) {
            Client record = res.get();

            String name = client.getName();
            String email = client.getEmail();
            Integer age = client.getAge();

            if (name != null) {
                record.setName(name);
            }

            if (email != null) {
                record.setEmail(email);
            }

            if (age != null) {
                record.setAge(age);
            }

            clientRepository.save(record);

            return record;
        } else {
            return client;
        }

    }

    public boolean delete(Integer id) {
        Boolean aBoolean = getClient(id).map(client -> {
            clientRepository.delete(client);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}
