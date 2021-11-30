package com.mycompany.webapp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.security.JwtUtil;
import com.mycompany.webapp.service.OrderFormService;
import com.mycompany.webapp.vo.Member;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OrderFormController {
	
	@Resource OrderFormService orderFormService;
	
	@RequestMapping("/getMemberInfo")
	public Member getMemberInfo(HttpServletRequest request){
		String mid = JwtUtil.getMidFromRequest(request);
		return orderFormService.getMemberInfo(mid);
	}
}
