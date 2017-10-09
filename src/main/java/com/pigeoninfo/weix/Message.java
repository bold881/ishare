package com.pigeoninfo.weix;



public class Message {
	public static final String MT_TEXT = "<![CDATA[text]]>";
	public static final String MT_IMAGE = "<![CDATA[image]]>";
	public static final String MT_VOICE = "<![CDATA[voice]]>";
	public static final String MT_VIDEO = "<![CDATA[video]]>";
	public static final String MT_SHOTVIDEO = "<![CDATA[shortvideo]]>";
	public static final String MT_LOCATION = "<![CDATA[location]]>";
	public static final String MT_LINK = "<![CDATA[link]]>";
	
	private String serverName;
	
	private String userName;
	
	private Long inCreateTime;
	
	private String inMsgType;
	
	private String inMsgContent;
	
	private String inMsgId;
	
	
}
