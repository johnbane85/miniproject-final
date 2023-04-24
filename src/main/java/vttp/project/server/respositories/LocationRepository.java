package vttp.project.server.respositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.project.server.models.Location;

@Repository
public class LocationRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<Location> getLocations() {

    Criteria c = Criteria.where(null);

    Query query = Query.query(c);

    return mongoTemplate.find(query, Document.class, "locations")
        .stream()
        .map(l -> Location.create(l))
        .toList();
  }

  public Location getLocationByName(String location_name) {

    Criteria c = Criteria.where("location_name").is(location_name);

    Query query = Query.query(c);

    return mongoTemplate.findOne(query, Location.class, "locations");
  }

}
