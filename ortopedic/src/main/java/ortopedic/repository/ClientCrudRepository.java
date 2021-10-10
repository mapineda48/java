package ortopedic.repository;

import org.springframework.data.repository.CrudRepository;
import ortopedic.entity.Client;

public interface ClientCrudRepository extends CrudRepository<Client, Integer> {
}
