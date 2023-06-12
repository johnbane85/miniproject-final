package vttp.project.server.respositories;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {

  @Autowired
  @Qualifier("redislab")
  private RedisTemplate<String, String> redisTemplate;

  public void save(String email, String token) {
    ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
    valueOp.set(email.toLowerCase(), token);

    redisTemplate.expireAt(email.toLowerCase(), new Date(new Date().getTime() +
        3600000));
  }

  public Optional<String> get(String email) {
    ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
    String value = valueOp.get(email.toLowerCase());
    if (null == value) {
      return Optional.empty(); // empty box
    }
    return Optional.of(value); // box with data
  }

}
