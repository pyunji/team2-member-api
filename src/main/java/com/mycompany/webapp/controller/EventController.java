package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.security.JwtUtil;
import com.mycompany.webapp.service.EventService;
import com.mycompany.webapp.vo.Event;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/event")
@Slf4j
public class EventController {
	@Resource private EventService eventService;
	
	/* 전체 이벤트 조회*/
	@GetMapping("")
	public List<Event> getEvents() {
		return eventService.selectAllEvent();
	}

	@GetMapping("/main")
	public List<Event> getForMain() {
		return eventService.selectForMain();
	}
	
	@RequestMapping("/{eno}")
	public Event getEvent(@PathVariable int eno) {
		log.info("getEvent실행");
		Event event = eventService.selectEvent(eno);
		log.info(event.toString());
		return event;
	}
	
	@PostMapping("/coupon/{eno}")
	public String getCoupon(HttpServletRequest request, @PathVariable int eno) {
		String mid = JwtUtil.getMidFromRequest(request);
		String result = eventService.getCoupon(mid,eno);
		return result;
	}
}
