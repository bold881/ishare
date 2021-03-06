package com.pigeoninfo.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pigeoninfo.entity.PostText;
import com.pigeoninfo.entity.SysRole;
import com.pigeoninfo.entity.UserInfo;
import com.pigeoninfo.sevice.PostTextService;
import com.pigeoninfo.sevice.SysRoleService;
import com.pigeoninfo.sevice.UserInfoService;

import javax.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private PostTextService postTextService;
	
	
    @RequestMapping({"/","/index"})
    public String index(Model model){
    	
		Subject currentUser = SecurityUtils.getSubject();
		UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
		if(userInfo == null) {
			return "redirect:/logout";
		}
		Pageable pageable = new PageRequest(0,  3, Sort.Direction.DESC, "textid");
		
		List<PostText> postTexts = postTextService.getByUserInfo(
				userInfo, 
				pageable);
		
    	//Collections.reverse(postTexts);
    	
    	model.addAttribute("postTexts", postTexts);
    	
    	
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