package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.CartItem;

@Mapper
public interface CartDao {
	List<CartItem> selectPidList(String mid);
}
