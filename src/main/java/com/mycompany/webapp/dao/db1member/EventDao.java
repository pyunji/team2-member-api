package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.vo.Coupon;
import com.mycompany.webapp.vo.Event;

@Mapper
public interface EventDao {
	List<Event> selectAllEvent();
	List<Event> selectForMain();
	Event selectEvent(int eno);
	void plusCount(int eno);
	void insertCoupon(@Param("mid") String mid,@Param("eno") int eno);
	List<Coupon> selectCoupon(@Param("mid") String mid, @Param("eno") int eno);
}
