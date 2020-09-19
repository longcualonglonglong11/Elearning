package com.myclass.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myclass.dto.UserDto;
import com.myclass.repository.RoleRepository;
import com.myclass.service.CloudinaryService;
import com.myclass.service.RoleService;
import com.myclass.service.UserService;

@Controller
@RequestMapping("admin/user")
public class AdminUserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping("")
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("users", userService.findAllDto());
		return "admin/user/index";
	}

	@GetMapping("add")
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("roles", roleService.findAllDto());
		modelMap.addAttribute("user", new UserDto());
		return "admin/user/add";
	}

	@PostMapping("add")
	public String add(ModelMap modelMap, @Valid @ModelAttribute("user") UserDto dto, BindingResult error) {

		if (error.hasErrors()) {
			System.out.println(error);
			modelMap.addAttribute("roles", roleService.findAllDto());
			return "admin/user/add";
		}
		String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10));
		dto.setPassword(hashed);
		String oldImage = dto.getAvatar();
		String cloudinaryImgURL = "";
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		dto.setAvatar(cloudinaryImgURL);
		if (userService.addDto(dto)) {
			return "redirect:/admin/user";
		}
		modelMap.addAttribute("roles", roleService.findAllDto());

		modelMap.addAttribute("message", "Can't insert ... !");
		return "admin/user/add";
	}

	@GetMapping("edit/{id}")
	public String edit(Model modelMap, @PathVariable("id") int id) {
		UserDto dto = userService.findDtoById(id);

		if (dto != null) {
			modelMap.addAttribute("roles", roleService.findAllDto());
			modelMap.addAttribute("user", dto);
			System.out.println("IMAGGGGGGGGGGG Before" + dto.getAvatar());

			return "admin/user/edit";
		}
		modelMap.addAttribute("message", "A user not exists !");
		return "redirect:/admin/user";

	}

	@PostMapping("edit")
	public String edit(Model modelMap, @Valid @ModelAttribute("user") UserDto dto, BindingResult error) {
		if (error.hasErrors()) {
			modelMap.addAttribute("roles", roleService.findAllDto());
			return "admin/user/edit";
		}
		String hashed = dto.getPassword();
		if (!dto.getPassword().startsWith("$"))
			hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10));
		dto.setPassword(hashed);
		String oldImage = dto.getAvatar();
		String cloudinaryImgURL = "";
		if (!cloudinaryService.checkFile(dto.getFileDatas())) {
			cloudinaryImgURL = cloudinaryService.uploadFile(dto.getFileDatas());
		} else {
			cloudinaryImgURL = oldImage;
		}
		System.out.println("IMAGGGGGGGGGGG After" + dto.getAvatar());
		dto.setAvatar(cloudinaryImgURL);
		if (userService.updateDto(dto)) {
			return "redirect:/admin/user";
		}
		modelMap.addAttribute("roles", roleService.findAllDto());
		modelMap.addAttribute("message", "Can't update ... !");
		return "admin/user/edit";

	}

	@GetMapping("delete/{id}")
	public String delete(Model modelMap, @PathVariable("id") int id) {
		if (!userService.deleteById(id))
			modelMap.addAttribute("message", "Can't delete ... !");
		return "redirect:/admin/user";

	}
}
