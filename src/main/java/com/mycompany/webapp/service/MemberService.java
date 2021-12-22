package com.mycompany.webapp.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.MemberDao;
import com.mycompany.webapp.dao.db3orders.OrdersDao;
import com.mycompany.webapp.dto.CouponEvent;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.MemberUpdate;
import com.mycompany.webapp.dto.UserGrade;
import com.mycompany.webapp.vo.Grade;
import com.mycompany.webapp.vo.MemberVo;
import com.mycompany.webapp.vo.Orders;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	//열거 타입 선언
	public enum JoinResult {
		SUCCESS,
		FAIL,
		DUPLICATED
	}
	public enum LoginResult {
		SUCCESS,
		FAIL_MID,
		FAIL_MPASSWORD,
		FAIL
	}
	
	@Resource
	private MemberDao memberDao;
	
	@Resource
	private OrdersDao orderDao;
	
	@Autowired
	StringRedisTemplate redisTemplate;
	
	//회원 가입을 처리하는 비즈니스 메소드(로직)
	public JoinResult join(MemberDto member) {
		try {
			//이미 가입된 아이디인지 확인
			List<MemberVo> dbMember = memberDao.selectById(member.getMid()); 
			log.info(member.getMphone());
			//DB에 회원 정보를 저장
			if(dbMember.size()==0) {
				memberDao.insert(member);
				return JoinResult.SUCCESS;
			} else {
				return JoinResult.DUPLICATED;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return JoinResult.FAIL;
		}
	}
	
	public String checkDuplicatedIP(String ip) {
		ValueOperations<String,String> vops = redisTemplate.opsForValue();
		
		String registeredIP = vops.get(ip);
		log.info(registeredIP);
		
		Duration duration = Duration.between(LocalTime.of(0, 0), LocalTime.of(23, 59));
		
		if(registeredIP != null) {
			int num = Integer.parseInt(registeredIP);
			if(num==2) return "duplicated";
			else {
				num++;
				//vops.set(ip, Integer.toString(num));
				//두번째 회원가입을 하고 난 후에는 24시간이 지나야 할 수 있음
				vops.set(ip, Integer.toString(num), duration);
							}
		} else {
			//한번도 가입한 적이 없는 ip인 경우
			vops.set(ip, "1",duration);
		}
		return "possible";
	}

//	public LoginResult login(MemberDto member) {
//		try {
//			log.info("login");
//			//이미 가입된 아이디인지 확인
//			MemberVo dbMember = memberDao.selectById(member.getMid()); 
//			
//			//확인 작업
//			if(dbMember == null) {
//				return LoginResult.FAIL_MID;
//			} else if(!dbMember.getMpassword().equals(member.getMpassword())) {
//				return LoginResult.FAIL_MPASSWORD;
//			} else {
//				return LoginResult.SUCCESS;
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//			return LoginResult.FAIL;
//		}
//	}
	
	public MemberVo selectMember(String mid) {
		return memberDao.selectMember(mid);
	}
	
	public void updateMember(MemberUpdate member) {
		log.info(member.toString());
		memberDao.updateMember(member);
	}
	
	public void wthdMember(String mid) {
		memberDao.wthdMember(mid);
	}
	
	public List<Grade> getGrades() {
		return memberDao.getGrades();
	}
	
	@Transactional
	public UserGrade getUserGrade(String mid) {
		UserGrade userGrade = memberDao.getUserGrade(mid);

		List<Orders> orders = orderDao.selectByMid(mid);
		int totalAmount = 0;
		for(Orders order : orders) {
			//log.info(order.toString());
			if(order.getOstatus().equals("주문 완료")) {
				totalAmount += order.getBeforeDcPrice();
			}
		}
		
		int level = userGrade.getGlevel();
		switch(level) {
		case 1 : userGrade.setRemain(1000000-totalAmount);
				 break;
		case 2 : userGrade.setRemain(2000000-totalAmount);
				 break;
		case 3 : userGrade.setRemain(3000000-totalAmount);
				 break;
		case 4 : userGrade.setRemain(5000000-totalAmount);
				 break;
		case 5 : break;
		}
		//log.info(userGrade.toString());
		
		return userGrade;
	}
	
	public int getUserMileage(String mid) {
		return memberDao.getUserMileage(mid);
	}
	
	public List<CouponEvent> getUserCoupon(String mid) {
		return memberDao.getUserCoupon(mid);
	}
}








