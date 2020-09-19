package com.myclass.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login")
public class LoginController {
	@GetMapping("")
	public String login(@RequestParam(required = false) String error, ModelMap modelMap) {
		String message = "";
		if (error != null && !error.isEmpty()) {
			message = "Login failed. Please try again !!!";
		}
		 modelMap.addAttribute("message", message);		
		 return "login/index";
	}
	@GetMapping("/403")
	public String error403() {
		
		 return "error/403";
	}
}
