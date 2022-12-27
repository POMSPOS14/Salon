package ru.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.salon.entity.Record;
import ru.salon.entity.Service;

import java.util.List;


public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findAll();

}
