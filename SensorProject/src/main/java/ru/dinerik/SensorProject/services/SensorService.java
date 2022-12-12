package ru.dinerik.SensorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dinerik.SensorProject.models.Sensor;
import ru.dinerik.SensorProject.repositories.SensorRepository;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);

    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findByName(String name) {
        return sensorRepository.findAll().stream().filter(sensorName->sensorName.getName().equals(name)).findAny().orElse(null);
    }
}