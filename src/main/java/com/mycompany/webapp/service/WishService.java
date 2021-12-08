package com.mycompany.webapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.WishDao;
import com.mycompany.webapp.dao.db2product.ProductDao;
import com.mycompany.webapp.dto.WishItem;
import com.mycompany.webapp.vo.Wish;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WishService {
	@Resource private WishDao wishDao;
	@Resource private ProductDao productDao;
	
	public List<WishItem> getList(String mid) {
		List<WishItem> list = new ArrayList<>();
		List<Wish> wishes = wishDao.selectAllByMid(mid);
		for(Wish wish : wishes) {
			list.add(productDao.selectItemByPstockid(wish.getPstockid()));		
		}
		
		return list;
	}
	
	public void insertItem(Wish wish) {
		wishDao.insertItem(wish);
	}
	
	public void deleteItem(Wish wish) {
		wishDao.deleteItem(wish);
	}
	
	public int selectCount(Wish wish) {
		return wishDao.selectCount(wish);
	}
}
