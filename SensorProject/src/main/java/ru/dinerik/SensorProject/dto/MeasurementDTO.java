package ru.dinerik.SensorProject.dto;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.*;

public class MeasurementDTO {

    @Min(value = -100)
    @Max(value = 100)
    private double value;

    private boolean raining;

    @NotNull
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
