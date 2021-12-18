package ocho.entity;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {
    @Id
    private BigInteger id;
    
    private String identification;

    private String name;
    
    private Date birthtDay;

    private String monthBirthtDay;
    
    private String address;

    private String cellPhone;

    //@Indexed(unique = true)
    private String email;

    private String password;

    private String zone;

    private String type;

}
