
package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.ContextDto;
import com.mycompany.webapp.security.JwtUtil;
import com.mycompany.webapp.service.DisplayService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/display")
@Slf4j
public class DisplayController {	
	
	@Resource private DisplayService displayService;
	/* 관심상품 조회*/
	@GetMapping("")
	public List<ContextDto> wishList(HttpServletRequest request) {
		String mid = JwtUtil.getMidFromRequest(request);
		
		
		List<ContextDto> contextResult= displayService.getList();
		log.info("contextResult = {}", contextResult);
		return contextResult;
	}
	
}