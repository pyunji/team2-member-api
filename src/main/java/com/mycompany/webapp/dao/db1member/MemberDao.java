package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.CouponEvent;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.MemberUpdate;
import com.mycompany.webapp.dto.UserGrade;
import com.mycompany.webapp.vo.Grade;
import com.mycompany.webapp.vo.MemberVo;

@Mapper
public interface MemberDao {
	public void insert(MemberDto member);	
	public List<MemberVo> selectById(String mid);
	public MemberVo selectMember(String mid);
	public void updateMember(MemberUpdate member);
	public void wthdMember(String mid);
	public List<Grade> getGrades();
	public UserGrade getUserGrade(String mid);
	public int getUserMileage(String mid);
	public List<CouponEvent> getUserCoupon(String mid);
}
