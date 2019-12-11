package com.mitrais.SmartClinic.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Registration {
    /**public static enum status{
        BOOK("Book"),
        VISIT("Visit"),
        CHECKED("Doctor checking"),
        WAIT("Waiting Payment"),
        PAID("Paid");

        private final String displayValue;

        private status(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }**/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private String checkupDate;

    @ManyToOne
    private ClinicUser doctors;

    @ManyToOne
    private ClinicUser patients;
}
