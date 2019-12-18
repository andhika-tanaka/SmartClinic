package com.mitrais.SmartClinic.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@DynamicUpdate
public class ClinicUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String dob;

    private String idNumber;

    private String gender;

    private String address;

    private String postalCode;

    private String city;

    private String province;

    private  String phone1;

    private String phone2;

    private Integer active;

    private Role role;
}
