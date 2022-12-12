package ru.dinerik.SensorProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dinerik.SensorProject.models.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

}
