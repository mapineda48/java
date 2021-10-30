package ortopedic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ortopedic.entity.Reservation.Reservation;
import ortopedic.entity.Reservation.ReportClient;
import ortopedic.entity.Reservation.ResultStatus;

@Repository
public class ReservationRepository {
    @Autowired
    private ReservationCrudRepository reservationCrudRepository;

    public List<ReportClient> reportClient() {
        return (List<ReportClient>) reservationCrudRepository.reportClients();
    }

    public ResultStatus countStatus() {
        return reservationCrudRepository.countStatus();
    };

    public List<Reservation> findAllBetweenDate(String start, String end) {
        return (List<Reservation>) reservationCrudRepository.findAllBetweenDate(start, end);
    }

    public List<Reservation> getAll() {
        return (List<Reservation>) reservationCrudRepository.findAll();
    }

    public Optional<Reservation> getReservation(Integer id) {
        return reservationCrudRepository.findById(id);
    }

    public Reservation save(Reservation message) {
        return reservationCrudRepository.save(message);
    }

    public void delete(Reservation message) {
        reservationCrudRepository.delete(message);
    }
}
