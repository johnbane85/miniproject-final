package vttp.project.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.project.server.models.Location;
import vttp.project.server.respositories.LocationRepository;

@Service
public class LocationService {

  @Autowired
  private LocationRepository locationRepo;

  public List<Location> getLocations() {

    return (List<Location>) locationRepo.getLocations();
  }

  public Location getLocationByName(String location_name) {

    return (Location) locationRepo.getLocationByName(location_name);
  }

}
