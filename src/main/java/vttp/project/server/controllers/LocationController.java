package vttp.project.server.controllers;

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
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.project.server.models.Location;
import vttp.project.server.services.LocationService;

@Controller
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*") // Add CORS header to the response
public class LocationController {

  @Autowired
  private LocationService locationServ;

  // GET /api/locations
  @GetMapping(path = "/locations")
  @ResponseBody
  public ResponseEntity<String> getLocations() {

    JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    locationServ.getLocations().stream()
        .forEach(v -> {
          arrBuilder.add(v.toJSON());
        });

    String response = arrBuilder.build().toString();

    // System.out.printf(">>> response:%s \n", response);

    return ResponseEntity.ok(response);

  }

  // GET /api/location/location_name
  @GetMapping(path = "/location/{location_name}")
  @ResponseBody
  public ResponseEntity<String> getLocationByName(@PathVariable String location_name) {

    JsonObject result = null;
    Location loc = locationServ.getLocationByName(location_name);
    JsonObjectBuilder builder = Json.createObjectBuilder(loc.toJSON());
    result = builder.build();

    // System.out.printf(">>> location:%s \n", result.toString());

    return ResponseEntity.ok(result.toString());
  }

}
