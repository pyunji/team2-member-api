package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.EventDao;
import com.mycompany.webapp.vo.Event;

@Service
public class EventService {
	@Resource private EventDao eventDao;
	
	public List<Event> selectAllEvent() {
		return eventDao.selectAllEvent();
	}
	
	public List<Event> selectForMain() {
		return eventDao.selectForMain();
	}
	
	public Event selectEvent(int eno) {
		return eventDao.selectEvent(eno);
	}
}
