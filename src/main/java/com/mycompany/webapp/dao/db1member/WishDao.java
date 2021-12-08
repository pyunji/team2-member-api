package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.WishItem;
import com.mycompany.webapp.vo.Wish;

@Mapper
public interface WishDao {
	List<Wish> selectAllByMid(String mid);
	void insertItem(Wish wish);
	void deleteItem(Wish wish);
	int selectCount(Wish wish);
}
