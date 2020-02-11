package hu.idomsoft.documentprocessor;

import brave.sampler.Sampler;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.idomsoft.common.dao.OkmanytipusDictionary;
import hu.idomsoft.common.dao.OkmanytipusDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("hu.idomsoft")
@EnableMongoRepositories(basePackageClasses = OkmanytipusDictionaryRepository.class)
@EnableFeignClients("hu.idomsoft")
@EnableDiscoveryClient
public class DocumentProcessorApplication implements CommandLineRunner {

	@Autowired
	private OkmanytipusDictionaryRepository okmanytipusDictionaryRepository;

	public static void main(String[] args) {
		SpringApplication.run(DocumentProcessorApplication.class, args);
	}

	@Override
	public void run(String[] args) throws IOException {
		loadOkmanytipusDictionary();
	}

	private void loadOkmanytipusDictionary() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		OkmanytipusDictionary dictionary = objectMapper.readValue(ResourceUtils.getFile(
				"classpath:kodszotar46_okmanytipus.json"), OkmanytipusDictionary.class);
		okmanytipusDictionaryRepository.insert(dictionary);
	}

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
