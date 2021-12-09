package com.mycompany.webapp.dao.db1member;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.vo.MemberVo;

@Mapper
public interface OrderFormDao {
	MemberVo selectByMid(String mid);
}
