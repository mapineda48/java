package ortopedic.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ortopedic.entity.Client;
import ortopedic.entity.Ortopedic;
import ortopedic.entity.Reservation.Reservation;
import ortopedic.entity.Reservation.ResultStatus;
import ortopedic.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrtopedicService ortopedicService;

    public ResultStatus countStatus() {
        return reservationRepository.countStatus();
    };

    public List<Reservation> getAllBetweenDate(String start, String end) {
        return reservationRepository.findAllBetweenDate(start, end);
    }

    public List<Reservation> getAll() {
        return reservationRepository.getAll();
    }

    public Optional<Reservation> getReservation(Integer id) {
        return reservationRepository.getReservation(id);
    }

    public Reservation save(Reservation reservation) {

        if (reservation.getIdReservation() == null) {
            if (reservation.getStatus() == null) {
                reservation.setStatus("created");
            }

            Client client = reservation.getClient();

            if (client != null) {
                Integer id = client.getIdClient();

                if (id != null) {
                    Optional<Client> res = clientService.getClient(id);

                    if (res.isPresent()) {
                        reservation.setClient(res.get());
                    }
                }
            }

            Ortopedic ortopedic = reservation.getOrtopedic();

            if (ortopedic != null) {
                Integer id = ortopedic.getId();

                if (id != null) {
                    Optional<Ortopedic> res = ortopedicService.getOrtopedic(id);

                    if (res.isPresent()) {
                        reservation.setOrtopedic(res.get());
                    }
                }
            }

            return reservationRepository.save(reservation);
        }

        Optional<Reservation> res = reservationRepository.getReservation(reservation.getIdReservation());

        if (res.isEmpty()) {
            return reservationRepository.save(reservation);
        } else {
            return reservation;
        }
    }

    public Reservation update(Reservation reservation) {
        Optional<Reservation> res = reservationRepository.getReservation(reservation.getIdReservation());

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
