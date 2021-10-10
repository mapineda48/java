package ortopedic.repository;

import org.springframework.data.repository.CrudRepository;
import ortopedic.entity.Category;

public interface CategoryCrudRepository extends CrudRepository<Category, Integer> {
}
