package ortopedic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ortopedic.entity.Reservation.Reservation;
import ortopedic.entity.Reservation.ReportClient;
import ortopedic.entity.Reservation.ResultStatus;

public interface ReservationCrudRepository extends CrudRepository<Reservation, Integer> {
   
    @Query(value = "SELECT Count(*) AS total, c AS client FROM Reservation r JOIN Client c ON r.client.idClient = c.idClient GROUP BY r.client.idClient ORDER BY total DESC")
    public Iterable<ReportClient> reportClients();

    @Query(value = "SELECT (SELECT count(*) FROM Reservation r WHERE r.status = 'cancelled') AS cancelled, (SELECT count(*) FROM Reservation r WHERE r.status = 'completed') AS completed;", nativeQuery = true)
    public ResultStatus countStatus();

    @Query(value = "SELECT * FROM reservation WHERE start_date BETWEEN ?1 AND ?2", nativeQuery = true)
    public Iterable<Reservation> findAllBetweenDate(String start, String end);
}
