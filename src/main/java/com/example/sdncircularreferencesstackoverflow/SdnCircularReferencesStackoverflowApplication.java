package com.example.sdncircularreferencesstackoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
@EntityScan(basePackageClasses = Type.class)
public class SdnCircularReferencesStackoverflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdnCircularReferencesStackoverflowApplication.class, args);
	}

}
