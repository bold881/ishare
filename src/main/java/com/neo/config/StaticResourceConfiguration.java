package com.neo.config;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import storage.StorageProperties;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
	
	private final String rootLocation;
	
	@Autowired
	public StaticResourceConfiguration(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation()).toString();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/postimg/**")
			//.addResourceLocations("/" + this.rootLocation + "/");
			.addResourceLocations("file:/postimg/");
	}
}
