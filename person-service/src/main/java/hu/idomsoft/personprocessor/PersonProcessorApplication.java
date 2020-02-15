package hu.idomsoft.personprocessor;

import brave.sampler.Sampler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.idomsoft.common.dao.AllampolgDictionary;
import hu.idomsoft.common.dao.AllampolgDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("hu.idomsoft")
@EnableMongoRepositories(basePackageClasses = AllampolgDictionaryRepository.class)
@EnableFeignClients("hu.idomsoft")
@EnableDiscoveryClient
public class PersonProcessorApplication implements CommandLineRunner {

    @Autowired
    private AllampolgDictionaryRepository allampolgDictionaryRepository;

    public static void main(String[] args) {
        SpringApplication.run(PersonProcessorApplication.class, args);
    }

    @Override
    public void run(String[] args) throws IOException {
        loadAllampolgDictionary();
    }

    private void loadAllampolgDictionary() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AllampolgDictionary dictionary = objectMapper.readValue(ResourceUtils.getFile(
                "classpath:kodszotar21_allampolg.json"), AllampolgDictionary.class);
        allampolgDictionaryRepository.insert(dictionary);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}