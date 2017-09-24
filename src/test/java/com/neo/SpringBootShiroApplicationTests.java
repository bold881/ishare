package com.neo;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pigeoninfo.entity.UserInfo;
import com.pigeoninfo.sevice.PostTextService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootShiroApplicationTests {

	@Autowired
	private PostTextService postTextService;
	
	@Test
	public void contextLoads() {
		
		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
		
		assertNotNull(postTextService.getByUserInfo(userInfo));	
	}

}
