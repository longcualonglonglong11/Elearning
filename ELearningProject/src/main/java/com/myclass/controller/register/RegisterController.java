package com.myclass.controller.register;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.dto.CourseDto;
import com.myclass.dto.RegisterDto;
import com.myclass.dto.UserDto;
import com.myclass.service.RegisterService;
import com.myclass.service.UserService;

@Controller
@RequestMapping("register")
public class RegisterController {
	@Autowired
	RegisterService registerService	;
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("register", new RegisterDto());
		return "register/index";
	}
	@PostMapping(value = "")
	public String save(Model modelMap, @Valid @ModelAttribute("register") RegisterDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute("register", dto);
			return "register/index";
		}
		if (registerService.register(dto)) {
			
			return "redirect:/login";
		}
		
		modelMap.addAttribute("message", "Can't  ... !");
		modelMap.addAttribute("register", dto);
		return "register/index";

	}
}
