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
public class Service {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String serviceType;

}
