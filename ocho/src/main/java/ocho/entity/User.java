package ocho.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @Indexed(unique = true)
    private String email;

    private String password;

    private String role;

    private String zone;

    private Date birthtDay;

    private String monthBirthtDay;

    private String address;

    private String cellPhone;
}
