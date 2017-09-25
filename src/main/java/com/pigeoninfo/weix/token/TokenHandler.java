package com.pigeoninfo.weix.token;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenHandler {
	
	public static final String wxToken = "7173543247dff881";
	
	public static final String wxEncodingAesKey = "g8I0dPNYtDAf9kRRGNaTQFJUccXfAhAE93oKpYlzmZQ";
	
	public static final String wxAppId = "wx38ddcf8adc399efa";
	
	private static final String secret = "4db48ccd093e3135024aaff841a80483";
	
	private static String tokenUri = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	
	private AccessToken accessToken;
	
	private HttpHeaders headers;
	
	private HttpStatus status;
	
	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@Autowired
	private RestTemplate restTemplate;
	
	public TokenHandler() {
		this.headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
	}
	
	//@Scheduled(fixedRate=7200000)
	public void refreshAccessToken() {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
	    ResponseEntity<String> responseEntity = 
	    		restTemplate.exchange(prepareFullTokenUri(), HttpMethod.GET, requestEntity, String.class);
	    this.setStatus(responseEntity.getStatusCode());
	    if(status == HttpStatus.OK) {
	    	try {
				JSONObject jsonObj = new JSONObject(responseEntity.getBody());
				accessToken.setAccessToken(jsonObj.getString("access_token"));
				accessToken.setExpiresIn(jsonObj.getInt("expires_in"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    }
	    
	}

	private String prepareFullTokenUri() {
		String fullUri = tokenUri;
		fullUri += "&appid=" + wxAppId;
		fullUri += "&secret=" + secret;
		return fullUri;
	}
}
