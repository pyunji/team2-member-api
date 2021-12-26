package com.mycompany.webapp.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
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
	
	@Autowired
	MailService mailService;
	
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
	
	//Redis사용 ip주소 중복 확인
	public String checkDuplicatedIP(String ip) throws MessagingException {
		ValueOperations<String,String> vops = redisTemplate.opsForValue();
		
		String result = "";
		
		String day_ip_cnt = vops.get("day_"+ip);
		String total_ip_cnt = vops.get("total_"+ip);
		
		log.info("day_ip_cnt" + day_ip_cnt);
		log.info("total_ip_cnt" + total_ip_cnt);
		
		Duration duration = Duration.between(LocalTime.of(0, 0), LocalTime.of(23, 59));
		
		if(total_ip_cnt==(null)) {
			//해당 ip주소로 이전에 한번도 가입한 적이 없는 경우
			vops.set("total_"+ip, "1");
			vops.set("day_"+ip, "1");
			result = "possible";
			log.info("here");
		}else {
			//가입한 이력이 있는 ip주소
			int total_ip_num = Integer.parseInt(total_ip_cnt);
//			if(total_ip_num==10) {
//			//test
			if(total_ip_num>=3) {
				//해당 ip주소로 10번 이상 회원 가입 시도
				//관리자에게 이메일 보내기
				log.info("send email");
				mailService.sendTextMail(ip);
				result = "duplicated";
			} else {
				//해당 ip주소로 10번 미만 회원 가입 시도
				if(day_ip_cnt==(null)) {
					//오늘 가입한 적이 없는 경우
					vops.set("day_"+ip, "1");
					vops.set("total_"+ip, Integer.toString(total_ip_num + 1));
					result = "possible";
				}else {
					//오늘 가입한 적이 있는 경우
					int num = Integer.parseInt(day_ip_cnt);
					log.info(day_ip_cnt);
					if(num>=2) result = "duplicated";
					else {
						num++;
						//두번째 회원가입을 하고 난 후에는 24시간이 지나야 함
						log.info(Integer.toString(num));
						vops.set("day_"+ip, Integer.toString(num),duration);
						log.info(vops.get("day_" +ip));
						vops.set("total_"+ip, Integer.toString(total_ip_num + 1));
						result = "possible";
					}
				}
			}			
		}
		//회원 가입이 가능한 경우 총 누적 회원 가입 횟수 count+1; 
		if(result.equals("possible")) {
			String totalCnt = vops.get("total_cnt");
			if(totalCnt==(null)) {
				vops.set("total_cnt", "1");
			} else {
				vops.set("total_cnt", Integer.toString(Integer.parseInt(totalCnt)+1));
			}
		}
		
		return result;
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








