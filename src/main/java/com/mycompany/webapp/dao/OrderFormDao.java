package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.vo.Member;

@Mapper
public interface OrderFormDao {
	Member selectByMid(String mid);
}
