package com.neo.web;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neo.entity.SysRole;
import com.neo.entity.UserInfo;
import com.neo.sevice.SysRoleService;
import com.neo.sevice.UserInfoService;

import javax.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class HomeController {
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	
    @RequestMapping({"/","/index"})
    public String index(){
        return"/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
        System.out.println("HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403";
    }

    @GetMapping("/register")
    public String registerUser(){
    	return "/register";
    }
    
    @GetMapping("/adminreg")
    public String getAdminReg(Model model) {
    	model.addAttribute("userinfo", new UserInfo());
    	return "/adminreg";
    }
    
    @PostMapping("/adminreg")
    public String adminReg(@ModelAttribute UserInfo userInfo) throws NoSuchAlgorithmException {
    	
    	if(userInfo != null) {
    		UserInfo existUser = userInfoService.findByUsername(userInfo.getUsername());
    		if(existUser != null) {
    			userInfo.setUid(existUser.getUid());
    		}
    		
    		ArrayList<SysRole> roleList = new ArrayList<SysRole>();
    		SysRole sysRole = sysRoleService.findById(1);
    		roleList.add(sysRole);
    		userInfo.setRoleList(roleList);
    		
    		userInfo.setSalt("8d78869f470951332959580424d4bf4f");
    		userInfo.setPassword(new SimpleHash("MD5", 
    				userInfo.getPassword(), 
    				userInfo.getCredentialsSalt(),
    				2).toHex());
    	
    		
    		userInfoService.saveUserInfo(userInfo);
    	}
    	
    	return "redirect:/index";
    }
}