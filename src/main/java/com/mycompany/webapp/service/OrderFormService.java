package com.mycompany.webapp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.OrderFormDao;
import com.mycompany.webapp.vo.Member;

@Service
public class OrderFormService {
	
	@Resource OrderFormDao orderFormDao;
	
	public Member getMemberInfo(String mid) {
		return orderFormDao.selectByMid(mid);
	}
}
