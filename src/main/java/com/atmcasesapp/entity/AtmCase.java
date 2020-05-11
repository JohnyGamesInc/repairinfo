package com.atmcasesapp.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "atm_cases")
@Data
public class AtmCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "case_id")
    private Long caseId;

    @NotNull
    @Column(name = "atm_id")
    private String atmId;

    @Column(name = "case_desc")
    private String caseDescription;

    private LocalDateTime start;

    private LocalDateTime finish;

    private String serial;

    @Column(name = "bank_nm")
    private String bankNM;

    private String channel;

    public AtmCase() {
    }
}