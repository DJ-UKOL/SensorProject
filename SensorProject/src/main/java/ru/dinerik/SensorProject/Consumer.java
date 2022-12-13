package ru.dinerik.SensorProject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Consumer {
    public static void main(String[] args) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        // Post запрос
        // Регистрируем новый сенсор
/*        Map<String, String> jsonToSend = new HashMap<>();
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
        }*/



        // Получаем 1000 измерений с сервера
        String url3 = "http://localhost:8080/measurements";
        ResponseEntity<String> response3 = restTemplate.getForEntity(url3, String.class);
        //System.out.println(response3);


        // Задание со звездочкой.
        // Вывести график.
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response3.getBody());   // Преобразуем в дерево
        Iterator<JsonNode> iterator = root.iterator();
        List<Double> listDouble = new ArrayList<>();
        List<Date> listDate = new ArrayList<>();
        while (iterator.hasNext()) {
            JsonNode jsonNode = iterator.next();
            listDouble.add(jsonNode.get("value").doubleValue());
            LocalDateTime localDateTime = LocalDateTime.parse(jsonNode.get("createdAt").asText());
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            listDate.add(date);
        }

        //double[] xData = new double[]{0.0, 1.0, 2.0};
        //double[] yData = new double[]{2.0, 1.0, 0.0};

// Create Chart
        //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", listDouble, listDate);
        final XYChart chart = new XYChartBuilder().width(1200).height(800).title("Area Chart").xAxisTitle("X").yAxisTitle("Y").build();
        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        chart.getStyler().setMarkerSize(8);
        chart.addSeries("a", listDouble);
        // Show it
        new SwingWrapper(chart).displayChart();
    }
}
