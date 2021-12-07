package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.CartDao;
import com.mycompany.webapp.dao.db2product.ProductDao;
import com.mycompany.webapp.dto.CartItem;

@Service
public class CartService {
	
	@Resource private CartDao cartDao;

	@Resource private ProductDao productDao;
	
	public List<CartItem> getList(String mid) {
		List<CartItem> dataFromMemberDB = cartDao.selectPidList(mid);
		for(CartItem cartItemFromMDB : dataFromMemberDB) {
			String pstockid = cartItemFromMDB.getPstockid();
			String pcommonid = cartItemFromMDB.getPcommonid();
			String pcolorid = pcommonid + "_" + cartItemFromMDB.getOccode();
			cartItemFromMDB.setPcolorid(pcolorid);
			CartItem dataFromPDB1 = productDao.selectPnameAndBname(pcommonid, pcolorid, pstockid);
			cartItemFromMDB.setBname(dataFromPDB1.getBname());
			cartItemFromMDB.setPname(dataFromPDB1.getPname());
			cartItemFromMDB.setImg1(dataFromPDB1.getImg1());
			cartItemFromMDB.setPprice(dataFromPDB1.getPprice());
			cartItemFromMDB.setStock(dataFromPDB1.getStock());
			cartItemFromMDB.setProductColorData(productDao.selectProductColorStockData(pcommonid));
		}
		return dataFromMemberDB;
		
	}

}
