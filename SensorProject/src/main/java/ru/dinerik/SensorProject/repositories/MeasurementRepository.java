package ru.dinerik.SensorProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dinerik.SensorProject.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, String> {    // Тип данных и тип первичного ключа

    @Query("SELECT COUNT(raining) FROM Measurement WHERE raining IS TRUE")
    int findRainyDays();
}
