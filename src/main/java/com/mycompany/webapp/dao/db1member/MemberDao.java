package com.mycompany.webapp.dao.db1member;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Member;

@Mapper
public interface MemberDao {
	public int insert(Member member);	
	public Member selectById(String mid);
}
