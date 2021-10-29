package ortopedic.entity.Reservation;

/**
 * https://stackoverflow.com/questions/36328063/how-to-return-a-custom-object-from-a-spring-data-jpa-group-by-query
 */
public interface ResultStatus {
    int getCompleted();
    int getCancelled();
}
