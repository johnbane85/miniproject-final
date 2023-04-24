package vttp.project.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.project.server.models.Weather;
import vttp.project.server.services.WeatherService;

@Controller
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*") // Add CORS header to the response
public class WeatherController {

  @Autowired
  private WeatherService weatherSvc;

  // GET /api/weather/city_name
  @GetMapping(path = "/weather/{city_name}")
  @ResponseBody
  public ResponseEntity<String> getWeatherByCity(@PathVariable String city_name) {

    JsonObject result = null;
    List<Weather> weather_list = weatherSvc.getWeather(city_name);

    Weather weather = weather_list.get(0);

    JsonObjectBuilder builder = Json.createObjectBuilder(weather.toJSON());
    result = builder.build();

    // System.out.printf(">>> weather:%s \n", result.toString());

    return ResponseEntity.ok(result.toString());

  }

}
