package ru.salon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.salon.entity.Record;
import ru.salon.repository.RecordRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class RecordService {

    private final RecordRepository repository;

    @Autowired
    public RecordService(RecordRepository repository) {
        this.repository = repository;
    }


    @PostConstruct
    public void generateTestData() {

    }

    public Record save(Record record) {
        return repository.save(record);
    }

    public void delete(Record record) {
        repository.delete(record);
    }

    public List<Record> findAll() {
        return repository.findAll();
    }

    public List<Record> findAllByEmployeeLogin(String login) {
        return repository.findAllByEmployeeLogin(login);
    }
    public List<Record> findAllByUserLogin(String login) {
        return repository.findAllByUserLogin(login);
    }
    public List<Record> findAllByEmployeeLoginAndRecordDate(String login, String recordDate) {
        return repository.findAllByEmployeeLoginAndRecordDate(login, recordDate);
    }

}
