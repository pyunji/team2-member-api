package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.db1member.EventDao;
import com.mycompany.webapp.vo.Coupon;
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
	
	@Transactional
	public String getCoupon(String mid, int eno) {
		Event event = eventDao.selectEvent(eno);
		if(event.getElimitCount()==event.getEcount()) {
			return "fail";
		}else {
			List<Coupon> coupons = eventDao.selectCoupon(mid, eno);
			if(!coupons.isEmpty()) {
				return "fail_double";
			} else {
				//ecount증가
				eventDao.plusCount(eno);
				//쿠폰 생성
				eventDao.insertCoupon(mid,eno);
				return "success";
			}
		}
	}
}
