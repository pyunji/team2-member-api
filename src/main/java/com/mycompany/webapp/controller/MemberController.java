package com.mycompany.webapp.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mycompany.webapp.dto.CouponEvent;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.MemberUpdate;
import com.mycompany.webapp.dto.UserGrade;
import com.mycompany.webapp.security.JwtUtil;
import com.mycompany.webapp.service.MemberService;
import com.mycompany.webapp.service.MemberService.JoinResult;
import com.mycompany.webapp.vo.Grade;
import com.mycompany.webapp.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

@RestController
//@RequestMapping("/member")
@Slf4j
public class MemberController {

	@Autowired private MemberService memberService;
	
	// 패스워드 암호화 역할 (/security/WebSecurityConfig에서 스프링 빈으로 등록했음)
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Autowired private AuthenticationManager authenticationManager;
	
	//회원 가입
	@RequestMapping("/join1")
	public Map<String, String> join1(MemberDto member) {
		log.info("join1실행");
		
		log.info(member.toString());
		
		member.setMpassword(passwordEncoder.encode(member.getMpassword()));
		JoinResult jr = memberService.join(member);
		Map<String ,String> map = new HashMap<String, String>();
		if (jr == JoinResult.SUCCESS) {
			map.put("result", "success");
		} else if (jr == JoinResult.DUPLICATED){
			map.put("result", "duplicated");
		} else {
			map.put("result", "fail");
		}
		
		return map;
	}
	
	//회원 가입을 위해 처음 들어오는 경로
	@RequestMapping("/join2")
	public Map<String, String> join2(@RequestBody MemberDto member) throws MessagingException {
		log.info("join2실행");
		//log.info(member.toString());
		
		//하나의 ip주소로 중복 회원가입 하는 경우를 막는 부분
		
		//클라이언트 ip주소 확인
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder
											.currentRequestAttributes())
											.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		
		log.info(ip);
		
		String result = memberService.checkDuplicatedIP(ip);
		
		//하루 회원가입 횟수(2번)을 넘긴 경우 / 같은 ip주소로 총 회원 가입 횟수가 10번이 넘은 경우
		if(result.equals("duplicated")) {
			Map<String ,String> map = new HashMap<String, String>();
			map.put("result", "duplicated_ip");
			return map;
		}else {
			//회원가입하는데 문제 x
			return join1(member);
		}
	}
	
	@RequestMapping("/login")
	public Map<String, String> login1(String mid, String mpassword) {
		log.info("login1 실행");
		log.info("mid = " + mid);
		log.info("mpassword = " + mpassword);
		if (mid == null || mpassword == null) {
			throw new BadCredentialsException("아이디 또는 패스워드가 제공되지 않았음");
		}
		
		// <Spring Security 사용자 인증>
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(mid, mpassword);
		// <인증하고 나서의 결과를 반환>
		Authentication authentication = authenticationManager.authenticate(token);
		
		// 스프링 시큐리티는 기본적으로 세션 인증방식을 사용하기 때문에 세션에 해당 인증 정보를 저장하게 된다.
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		
		// <응답 내용>
		String authority = authentication.getAuthorities().iterator().next().toString();
		log.info("authority : " + authority);
		Map<String, String> map = new HashMap<String, String>();
		log.info(token.toString());
		map.put("result", "success");
		map.put("mid", mid);
		map.put("jwt", JwtUtil.createToken(mid, authority));
		log.info("jwt: " + JwtUtil.createToken(mid, authority));
		
		return map;
	}
	
//	@RequestMapping("/login2")
//	public Map<String, String> login2(@RequestBody Member member) {
//		log.info("실행");s
//		return login1(member.getMid(), member.getMpassword());
//	}
	
	@RequestMapping("/checkPW")
	public Map<String, String> checkPW(String mid, String mpassword) {
		log.info("checkPW 실행");
//		log.info("mid = " + mid);
//		log.info("mpassword = " + mpassword);
		
		return login1(mid,mpassword);
	}
	
	@RequestMapping("/member")
	public MemberVo selectMember(HttpServletRequest request) {
		String mid = JwtUtil.getMidFromRequest(request);
		return memberService.selectMember(mid);
	}
	
	@PostMapping("/member")
	public void updateMember(@RequestBody MemberUpdate user) throws ParseException {
		log.info(user.toString());
//		if(user.getBirth()!=null) {
//			Date date = user.getBirth();
//			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//			String strDate = f.format(date);
//			log.info(strDate);
//			Date to = f.parse(strDate);
//			user.setBirth(to);
//			log.info(user.getBirth().toString());
//		}
		memberService.updateMember(user);
	}
	
	@RequestMapping("/member/wthd")
	public void wthdMember(String mid) {
		//log.info("wthd 실행");
		memberService.wthdMember(mid);
	}
	
	@RequestMapping("/grade")
	public List<Grade> memberGrade() {
		return memberService.getGrades();
	}
	
	@RequestMapping("/member/grade")
	public UserGrade getUserGrade(HttpServletRequest request) {
		log.info("getUserGrade실행");
		String mid = JwtUtil.getMidFromRequest(request);
		return memberService.getUserGrade(mid);
	}
	
	@RequestMapping("/member/mileage")
	public int getUserMileage(HttpServletRequest request) {
		log.info("getUserMileage실행");
		String mid = JwtUtil.getMidFromRequest(request);
		return memberService.getUserMileage(mid);
	}
	
	@RequestMapping("/member/coupon")
	public List<CouponEvent> getUserCoupon(HttpServletRequest request){
		log.info("getUserCoupon실행");
		String mid = JwtUtil.getMidFromRequest(request);
		return memberService.getUserCoupon(mid);
	}
}
