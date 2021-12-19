package com.mycompany.webapp.dao.db3orders;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.vo.Orders;

@Mapper
public interface OrdersDao {
	List<Orders> selectByMid(String mid);
}
