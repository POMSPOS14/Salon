package ru.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.salon.entity.Record;
import ru.salon.entity.User;

import java.util.List;
import java.util.Optional;


public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAll();
    List<Record> findAllByEmployeeLogin(String login);
    List<Record> findAllByUserLogin(String login);

    List<Record> findAllByEmployeeLoginAndRecordDate(String login, String recordDate);

}
