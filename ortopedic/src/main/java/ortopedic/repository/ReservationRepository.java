package ortopedic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ortopedic.entity.Reservation;

@Repository
public class ReservationRepository {
    @Autowired
    private ReservationCrudRepository messageCrudRepository;

    public List<Reservation> getAll() {
        return (List<Reservation>) messageCrudRepository.findAll();
    }

    public Optional<Reservation> getReservation(Integer id) {
        return messageCrudRepository.findById(id);
    }

    public Reservation save(Reservation message) {
        return messageCrudRepository.save(message);
    }

    public void delete(Reservation message) {
        messageCrudRepository.delete(message);
    }
}
