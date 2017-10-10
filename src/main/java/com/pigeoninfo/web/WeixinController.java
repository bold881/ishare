package com.pigeoninfo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pigeoninfo.weix.AesException;
import com.pigeoninfo.weix.Message;
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
		
			try {
				if(wxBizMsgCrypt.checkSignature(signature, timeStamp, nonce)) {
					return echoStr;
				} else {
					return "invalid message!";
				}
			} catch (AesException e) {
				e.printStackTrace();
				return "error!";
			}
	}
	
	@PostMapping("/echostr")
	@ResponseBody
	public String messageRecive(@RequestParam("signature") String msgSignature,
			@RequestParam("timestamp") String timeStamp, 
			@RequestParam("nonce") String nonce,
			@RequestBody String requestBody) throws Exception {
		try {
			String decryptMsg = wxBizMsgCrypt.decryptMsg(
					msgSignature, timeStamp, nonce, requestBody);
			System.out.print("decrypt msg: " + decryptMsg);
			
			Message inMessage = new Message(decryptMsg);
			if(inMessage != null) {
				if(inMessage.isTextTypeMessage()) {
					String txtReply = inMessage.getTextTypeReply();
					return wxBizMsgCrypt.encryptMsg(txtReply, timeStamp, nonce);
				}
			}
			
		} catch (AesException e) {
			e.printStackTrace();
		}
		
		return "success";
	}
}
