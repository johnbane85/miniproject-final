package vttp.project.server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {

  @Value("${spring.redis.host}")
  private String redisHost;
  @Value("${spring.redis.port}")
  private Integer redisPort;
  @Value("${spring.redis.database}")
  private Integer redisDatabase;
  @Value("${spring.redis.username}")
  private String redisUsername;
  @Value("${spring.redis.password}")
  private String redisPassword;

  @Bean("redislab")
  public RedisTemplate<String, String> initRedisTemplate() {
    // Configure the Redis database
    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
    redisConfig.setHostName(redisHost);
    redisConfig.setPort(redisPort);
    redisConfig.setDatabase(redisDatabase);
    redisConfig.setUsername(redisUsername);
    redisConfig.setPassword(redisPassword);

    // Create an instance of the Jedis driver
    JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();

    // Create a factory for Jedis connection
    JedisConnectionFactory jedisFac = new JedisConnectionFactory(redisConfig, jedisConfig);
    jedisFac.afterPropertiesSet();

    // Create RedisTemplate
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisFac);

    // StringRedisSerializer
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());

    return redisTemplate;
  }

  @Bean
  public WebMvcConfigurer configureCORS() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/*")
            .allowedOrigins("*");
      }
    };
  }
}
