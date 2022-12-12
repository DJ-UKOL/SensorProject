package ru.dinerik.SensorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dinerik.SensorProject.models.Measurement;
import ru.dinerik.SensorProject.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        List<Measurement> measurementList = measurementRepository.findAll();
        // Добавить класс Sensor
        return measurementRepository.findAll();
    }


    @Transactional
    public void save(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
        System.out.println(measurement);
        measurementRepository.save(measurement);
    }

    public int findRainyDays() {
        return measurementRepository.findRainyDays();
    }
}