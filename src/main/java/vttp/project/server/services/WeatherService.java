package vttp.project.server.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.project.server.models.Weather;
import vttp.project.server.respositories.WeatherRepository;

@Service
public class WeatherService {

  private static final String URL = "http://api.weatherapi.com/v1/current.json";

  @Value("${weather.api.key}")
  private String key;

  @Autowired
  private WeatherRepository weatherRepo;

  public List<Weather> getWeather(String city) {

    // Check if the weather data has been cached
    Optional<String> opt = weatherRepo.get(city);
    String payload;

    // Check if the box is empty
    if (opt.isEmpty()) {

      // Create the url with query string
      String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

      String url = UriComponentsBuilder.fromUriString(URL)
          .queryParam("key", key)
          .queryParam("q", encodedCity)
          .queryParam("aqi", "no")
          .toUriString();

      // Create the GET request, GET url
      RequestEntity<Void> req = RequestEntity.get(url).build();

      // Make the call to Weather API
      RestTemplate template = new RestTemplate();
      ResponseEntity<String> resp;

      try {
        // Throws an exception if status code not in between 200 - 399
        resp = template.exchange(req, String.class);
      } catch (Exception ex) {
        System.err.printf("Error: %s\n", ex.getMessage());
        return Collections.emptyList();
      }

      payload = resp.getBody();

      weatherRepo.save(city, payload);

    } else {
      // Retrieve the value from the box
      payload = opt.get();

    }

    // Convert the String to a Reader
    Reader strReader = new StringReader(payload);
    // Create a JsonReader from Reader
    JsonReader jsonReader = Json.createReader(strReader);
    // Read the payload as Json object
    JsonObject weatherResult = jsonReader.readObject().getJsonObject("current");

    Weather weather = new Weather();

    JsonObject condition = weatherResult.getJsonObject("condition");

    weather.setText(condition.getString("text"));
    weather.setIcon(condition.getString("icon"));
    weather.setTemp_c(weatherResult.getInt("temp_c"));

    List<Weather> list = new LinkedList<>();
    list.add(weather);

    return list;
  }

}
