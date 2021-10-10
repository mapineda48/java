package ortopedic.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ortopedic.entity.Reservation;
import ortopedic.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAll() {
        return reservationRepository.getAll();
    }

    public Optional<Reservation> getReservation(Integer id) {
        return reservationRepository.getReservation(id);
    }

    public Reservation save(Reservation reservation) {

        if (reservation.getId() == null) {
            reservation.setStatus("created");

            return reservationRepository.save(reservation);
        }

        Optional<Reservation> res = reservationRepository.getReservation(reservation.getId());

        if (res.isEmpty()) {
            return reservationRepository.save(reservation);
        } else {
            return reservation;
        }
    }

    public Reservation update(Reservation reservation) {
        Optional<Reservation> res = reservationRepository.getReservation(reservation.getId());

        if (!res.isEmpty()) {
            Reservation record = res.get();

            Date startDate = reservation.getStartDate();
            Date devolutionDate = reservation.getDevolutionDate();
            Integer score = reservation.getScore();
            String status = reservation.getStatus();

            if (startDate != null) {
                record.setStartDate(startDate);
            }
            
            if (devolutionDate != null) {
                record.setDevolutionDate(devolutionDate);
            }

            if (score != null) {
                record.setScore(score);
            }

            if (status != null) {
                record.setStatus(status);
            }

            reservationRepository.save(record);

            return record;
        } else {
            return reservation;
        }

    }

    public boolean delete(Integer id) {
        Boolean aBoolean = getReservation(id).map(reservation -> {
            reservationRepository.delete(reservation);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}
