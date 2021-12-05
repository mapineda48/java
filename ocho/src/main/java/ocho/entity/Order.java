package ocho.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Order {
    public static String PENDING = "Pendiente";
    public static String APROVED = "Aprobada";
    public static String REJECTED = "Rechazada";
    
    @Id
    private BigInteger id;

    private Date registerDay;

    private String status;

    private User salesMan;

    private Map<Integer, Laptop> products;

    private Map<Integer, Integer> quantities;

}