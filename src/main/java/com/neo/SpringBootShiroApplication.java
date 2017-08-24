package com.neo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import storage.FileSystemStorageService;
import storage.StorageProperties;
import storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootShiroApplication.class, args);
	}
	
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactoryBean() {
		return new HibernateJpaSessionFactoryBean();
	}
	
	@Autowired
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}
	
	@Autowired
	@Bean
	public StorageService storageService(StorageProperties storageProperties) {
		return new FileSystemStorageService(storageProperties);
	}
}
