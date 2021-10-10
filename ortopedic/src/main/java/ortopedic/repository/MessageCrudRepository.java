package ortopedic.repository;

import org.springframework.data.repository.CrudRepository;
import ortopedic.entity.Message;

public interface MessageCrudRepository extends CrudRepository<Message, Integer> {
}
