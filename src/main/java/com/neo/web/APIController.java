package com.neo.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.entity.AjaxResponsePostText;
import com.neo.entity.PostImg;
import com.neo.entity.PostText;
import com.neo.entity.PostTextRequest;
import com.neo.entity.UserInfo;
import com.neo.sevice.PostTextService;

@Controller
@RequestMapping("/api")
@RestController
public class APIController {
	
	@Autowired
	PostTextService postTextService;
	
	
	@RequestMapping("/posttext")
	public List<AjaxResponsePostText> pagedPostText(@RequestBody PostTextRequest posttextRequest) {
		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
		Pageable pageable = new PageRequest(posttextRequest.pageIndex,  
				posttextRequest.pageSize, 
				Sort.Direction.DESC, "textid");

		List<PostText> postTexts = postTextService.getByUserInfo(
				userInfo, 
				pageable);
		//Collections.reverse(postTexts);
		
		List<AjaxResponsePostText> lstPostTexts = new ArrayList<AjaxResponsePostText>();
		for(PostText postText : postTexts) {
			AjaxResponsePostText response = new AjaxResponsePostText();
			response.setContent(postText.getContent());
			response.setDate(postText.getDate());
			
			List<String> lstImgs = new ArrayList<String>();
			for(PostImg postImg : postText.getPostImgs()) {
				lstImgs.add(postImg.getHashedFilename());
			}
			response.setPostImages(lstImgs);
			lstPostTexts.add(response);
		}
		
		
//		Gson gson = new Gson();
//		String ret = gson.toJson(lstPostTexts);
		return lstPostTexts;
	}
}
