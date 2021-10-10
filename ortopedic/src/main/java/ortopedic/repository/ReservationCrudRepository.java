package ortopedic.repository;

import org.springframework.data.repository.CrudRepository;
import ortopedic.entity.Reservation;

public interface ReservationCrudRepository extends CrudRepository<Reservation, Integer> {
}
