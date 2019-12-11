package com.mitrais.SmartClinic.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Schedule {

    public static enum days{
        minggu("Minggu"),
        senin("Senin"),
        selasa("Selasa"),
        rabu("Rabu"),
        kamis("Kamis"),
        jumat("Jumat"),
        sabtu("Sabtu");

        private final String displayValue;

        private days(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private days days;

    private String startHour;

    private String finishHour;

    private Integer fee;

    @ManyToOne
    private ClinicUser users;

    @ManyToOne
    private Clinic clinics;
}
