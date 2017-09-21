package com.neo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.pigeoninfo.storage.FileSystemStorageService;
import com.pigeoninfo.storage.StorageProperties;
import com.pigeoninfo.storage.StorageService;
import com.pigeoninfo.weix.AesException;
import com.pigeoninfo.weix.WXBizMsgCrypt;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootShiroApplication {
	
	private static String wxToken = "7173543247dff881";
	
	private static String wxEncodingAesKey = "g8I0dPNYtDAf9kRRGNaTQFJUccXfAhAE93oKpYlzmZQ";
	
	private static String wxAppId = "wx38ddcf8adc399efa";

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
			return new WXBizMsgCrypt(wxToken, wxEncodingAesKey, wxAppId);
		} catch (AesException e) {
			e.printStackTrace();
			return null;
		}
	}
}
