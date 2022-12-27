package ru.salon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Record {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String employeeFullName;

    @Column(nullable = false)
    private String userFullName;

    @Column(nullable = false)
    private String employeeLogin;

    @Column(nullable = false)
    private String userLogin;

    @Column(nullable = false)
    private String recordDate;

    @Column(nullable = false)
    private String recordTime;

    @Column(nullable = false)
    private String serviceType;
}
