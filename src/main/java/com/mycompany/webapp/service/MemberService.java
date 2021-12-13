package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.MemberDao;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.vo.MemberVo;

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
}








