package ru.dinerik.SensorProject.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.dinerik.SensorProject.dto.MeasurementDTO;
import ru.dinerik.SensorProject.dto.SensorDTO;
import ru.dinerik.SensorProject.models.Measurement;
import ru.dinerik.SensorProject.models.Sensor;
import ru.dinerik.SensorProject.services.MeasurementService;
import ru.dinerik.SensorProject.services.SensorService;
import ru.dinerik.SensorProject.util.ErrorResponse;
import ru.dinerik.SensorProject.util.NotCreatedException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController     // Возвращаем данные, а не представления
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

/*    @GetMapping()
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.findAll()     // Jackson конвертирует эти объекты в JSON
            .stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }*/

    @GetMapping()
    public List<Measurement> getMeasurements() {
        return measurementService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDays() {
        return measurementService.findRainyDays();
    }

/*
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new NotCreatedException(errorMsg.toString());
        }
        measurementService.save(convertToMeasurement(measurementDTO));
        // отправляем HTTP ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }
*/

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new NotCreatedException(errorMsg.toString());
        }
//        if(sensorService.findAll().stream().map(Sensor::getName).toList().contains(measurementDTO.getSensor().getName())) {
            measurementService.save(convertToMeasurement(measurementDTO));
       /* } else {
            throw new NotCreatedException("Not found such sensor's name " + measurementDTO.getSensor().getName() + ". Please registration sensor's name.");
        }*/
        // отправляем HTTP ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);    //
    }


    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        // Проверка имени сенсора при добавлении измерения в бд.
        String nameSensor = measurementDTO.getSensor().getName();
        if(sensorService.findAll().stream().map(Sensor::getName).toList().contains(nameSensor)) {
            return modelMapper.map(measurementDTO, Measurement.class);
        }
        throw new NotCreatedException("Not found such sensor's name " + nameSensor + ". Please registration sensor's name.");
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}