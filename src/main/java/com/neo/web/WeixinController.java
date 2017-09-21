package com.neo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pigeoninfo.weix.AesException;
import com.pigeoninfo.weix.WXBizMsgCrypt;

@Controller
@RequestMapping("/wx")
public class WeixinController {
	
	private WXBizMsgCrypt wxBizMsgCrypt;
	
	@Autowired
	public void getWxBizMsgCrypt(WXBizMsgCrypt wxBizMsgCrypt) {
		this.wxBizMsgCrypt = wxBizMsgCrypt;
	}
	
	@GetMapping("/echostr")
	@ResponseBody
	public String getVerify(@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timeStamp, 
			@RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echoStr) {
		System.out.println(signature+timeStamp+nonce+echoStr);
		try {
			return wxBizMsgCrypt.verifyUrl(signature, timeStamp, nonce, echoStr);
		} catch (AesException e) {
			e.printStackTrace();
			return "error";
		}
	}
}
