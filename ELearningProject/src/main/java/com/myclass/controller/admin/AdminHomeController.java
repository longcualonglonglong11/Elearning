package com.myclass.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {  "admin/home", "/admin" })
public class AdminHomeController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "admin/home/index";
	}
}