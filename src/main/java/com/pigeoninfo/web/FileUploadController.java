package com.pigeoninfo.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pigeoninfo.entity.PostImg;
import com.pigeoninfo.entity.PostText;
import com.pigeoninfo.entity.UserInfo;
import com.pigeoninfo.sevice.ImageProcessor;
import com.pigeoninfo.sevice.PostImgService;
import com.pigeoninfo.sevice.PostTextService;
import com.pigeoninfo.storage.StorageFileNotFoundException;
import com.pigeoninfo.storage.StorageService;

@Controller
public class FileUploadController {
	
	private final StorageService storageService;
	
	@Autowired
	private PostTextService postTextService;
	
	@Autowired
	private PostImgService postImgService;
	
	@Autowired
	private ImageProcessor imageProcessService;
	
	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/fileup")
	public String listUploadedFiles(Model model) throws IOException {
		model.addAttribute("files", storageService.loadAll().map(
				path->MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, 
						"serveFile", path.getFileName().toString()).build().toString())
				.collect(Collectors.toList()));
		
		return "uploadForm";
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	
	@PostMapping("/fileup")
	public String handleFileUpload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("text") String text,
			RedirectAttributes redirectAttributes) {
		String fileName = storageService.storeReturnFileName(file);
		//String cpFileName = imageProcessService.ImageCompress(fileName);
		String cpFileName = imageProcessService.ImageScale(fileName);
		
		if(!(fileName == null && text == null)) {
			PostImg postImg = new PostImg();
			postImg.setHashedFilename(fileName);
			postImg.setCpFileName(cpFileName);
			postImg.setOriginFilename(StringUtils.cleanPath(file.getOriginalFilename()));
			postImg.setFileExt(StringUtils.getFilenameExtension(fileName));
			postImg = postImgService.save(postImg);
			
			if(postImg != null) {
				// text
				PostText postText = new PostText();
				postText.setContent(text);
				
				// post img
				ArrayList<PostImg> imgList = new ArrayList<PostImg>();
				imgList.add(postImg);
				postText.setPostImgs(imgList);
				
				// current user
				Subject currentUser = SecurityUtils.getSubject();
				UserInfo userInfo = (UserInfo) currentUser.getPrincipal();
				if(userInfo != null) {
					postText.setUserInfo(userInfo);
				}
				
				// date
				postText.setDate(new Date());
				
				postTextService.save(postText);
			}
			
		}
		
//		redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");
		
		return "redirect:/";
	}
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
