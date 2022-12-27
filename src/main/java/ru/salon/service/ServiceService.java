package ru.salon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.salon.entity.Record;
import ru.salon.repository.ServiceRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ServiceService {

    private final ServiceRepository repository;

    @Autowired
    public ServiceService(ServiceRepository repository) {
        this.repository = repository;
    }


    @PostConstruct
    public void generateTestData() {
        save(ru.salon.entity.Service.builder().serviceType("Можельная стрижка").build());
        save(ru.salon.entity.Service.builder().serviceType("Обычня стрижка").build());

    }

    public ru.salon.entity.Service save(ru.salon.entity.Service record) {
        return repository.save(record);
    }

    public List<ru.salon.entity.Service> findAll() {
        return repository.findAll();
    }


}
