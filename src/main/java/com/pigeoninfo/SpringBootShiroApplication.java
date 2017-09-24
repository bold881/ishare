package com.pigeoninfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.pigeoninfo.storage.FileSystemStorageService;
import com.pigeoninfo.storage.StorageProperties;
import com.pigeoninfo.storage.StorageService;
import com.pigeoninfo.weix.AesException;
import com.pigeoninfo.weix.WXBizMsgCrypt;
import com.pigeoninfo.weix.token.TokenHandler;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootShiroApplication.class, args);
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
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setForceEncoding(true);
	    characterEncodingFilter.setEncoding("UTF-8");
	    registrationBean.setFilter(characterEncodingFilter);
	    return registrationBean;
	}
	
	@Bean
	public WXBizMsgCrypt wxBizMsgCrypt() {
		try {
			return new WXBizMsgCrypt(TokenHandler.wxToken, 
					TokenHandler.wxEncodingAesKey, 
					TokenHandler.wxAppId);
		} catch (AesException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
}
