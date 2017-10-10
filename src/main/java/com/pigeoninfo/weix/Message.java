package com.pigeoninfo.weix;



public class Message {
	public static final String MT_TEXT = "text";
	public static final String MT_IMAGE = "image";
	public static final String MT_VOICE = "voice";
	public static final String MT_VIDEO = "video";
	public static final String MT_SHOTVIDEO = "shortvideo";
	public static final String MT_LOCATION = "location";
	public static final String MT_LINK = "link";
	
	private String serverName;
	
	private String userName;
	
	private String inCreateTime;
	
	private String inMsgType;
	
	private String inMsgContent;
	
	private String inMsgId;
	
	private String rawXml;
	
	public Message(String xmlText) throws Exception {
		Object[] msgObjs = XMLParse.extractInMsg(xmlText);
		serverName = msgObjs[0].toString();
		userName = msgObjs[1].toString();
		inCreateTime = msgObjs[2].toString();
		inMsgType = msgObjs[3].toString();
		inMsgId = msgObjs[4].toString();
		setRawXml(xmlText);
	}

	public String getRawXml() {
		return rawXml;
	}

	public void setRawXml(String rawXml) {
		this.rawXml = rawXml;
	}
	
	public String getTextTypeReply() throws Exception {
		String createTime = Long.toString(System.currentTimeMillis());
		
		if(inMsgContent == null){
			getTextTypeMsgContent();
		}
		
		return XMLParse.generateTxtReply(userName, 
				serverName, 
				createTime, 
				inMsgType,
				inMsgContent);
	}
	
	public String getInMsgType() {
		return inMsgType;
	}
	
	private void getTextTypeMsgContent() throws Exception {
		inMsgContent = XMLParse.extractTextInMsgContent(rawXml);
	}
	
	public boolean isTextTypeMessage() {
		return inMsgType.equalsIgnoreCase(MT_TEXT);
	}
}
