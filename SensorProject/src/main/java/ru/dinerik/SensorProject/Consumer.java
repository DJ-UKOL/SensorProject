package ru.dinerik.SensorProject;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import ru.dinerik.SensorProject.models.Sensor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Consumer {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        // Post запрос
        // Регистрируем новый сенсор
        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name" , "Test Sensor From RestTemplate3");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);
        String url = "http://localhost:8080/sensors/registration";
        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println(response);

        // Отправляем 1000 запросов со случайными измерениями и дождем.
        Map<String, Object> jsonToPost = new HashMap<>();

        for(int i = 0; i < 1000; i++) {
            jsonToPost.put("value", new Random().nextInt(200) - 99);
            jsonToPost.put("raining", new Random().nextBoolean());
            jsonToPost.put("sensor", new Sensor("Test Sensor From RestTemplate3"));
            HttpEntity<Map<String, Object>> request2 = new HttpEntity<>(jsonToPost);
            String url2 = "http://localhost:8080/measurements/add";
            String response2 = restTemplate.postForObject(url2, request2, String.class);
            System.out.println(response2);
        }



        // Получаем 1000 измерений с сервера
        String url3 = "http://localhost:8080/measurements";
        String response3 = restTemplate.getForObject(url3, String.class);
        System.out.println(response3);
    }
}
