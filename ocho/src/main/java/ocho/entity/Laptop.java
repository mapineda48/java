package ocho.entity;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Laptop {
    @Id
    private BigInteger id;

    private String brand;
    
    private String model;
    
    private String procesor;
    
    private String os;
    
    private String description;
    
    private String memory;
    
    private String hardDrive;
    
    private boolean availability = true;
    
    private double price;
    
    private int quantity;
    
    private String photography;
    
}
