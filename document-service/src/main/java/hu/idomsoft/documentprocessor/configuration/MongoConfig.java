package hu.idomsoft.documentprocessor.configuration;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Configuration
public class MongoConfig {

  private static final String MONGO_DB_URL = "localhost";
  private static final String MONGO_DB_NAME = "embeded_db";

  @Bean
  public MongoTemplate mongoTemplate() throws IOException {
    EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
    mongo.setBindIp(MONGO_DB_URL);
    String port = System.getProperty("mongodb.port");
    if (StringUtils.isEmpty(port)) {
      mongo.setPort(Integer.parseInt(port));
    }
    MongoClient mongoClient = mongo.getObject();
    return new MongoTemplate(mongoClient, MONGO_DB_NAME);
  }
}
