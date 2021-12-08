package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.CartDao;
import com.mycompany.webapp.dao.db2product.ProductDao;
import com.mycompany.webapp.dto.CartItem;
import com.mycompany.webapp.dto.CartUpdate;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.ProductToCart;
import com.mycompany.webapp.dto.Size;
import com.mycompany.webapp.vo.Cart;

@Service
public class CartService {
	
	@Resource private CartDao cartDao;

	@Resource private ProductDao productDao;
	
	@Transactional
	public List<CartItem> getList(String mid) {
		List<CartItem> dataFromMemberDB = cartDao.selectPidList(mid);
		for(CartItem cartItem : dataFromMemberDB) {
			String pstockid = cartItem.getPstockid();
			String pcommonid = cartItem.getPcommonid();
			String pcolorid = pcommonid + "_" + cartItem.getOccode();
			cartItem.setPcolorid(pcolorid);
			CartItem dataFromPDB1 = productDao.selectPnameAndBname(pcommonid, pcolorid, pstockid);
			cartItem.setBname(dataFromPDB1.getBname());
			cartItem.setPname(dataFromPDB1.getPname());
			cartItem.setImg1(dataFromPDB1.getImg1());
			cartItem.setPprice(dataFromPDB1.getPprice());
			cartItem.setStock(dataFromPDB1.getStock());
			cartItem.setProductColorData(productDao.selectProductColorStockData(pcommonid));
		}
		return dataFromMemberDB;
		
	}
	
	public List<Color> getColors(String pcommonId) {
		return cartDao.selectColorsByPcommonId(pcommonId);
	}
	
	public List<Size> getSizesByPcolorId(String pcolorId) {
		return cartDao.selectSizesByPcolorId(pcolorId);
	}

	@Transactional
	public int updateQuantity(int quantity, String pstockId, String mid ) {
		return cartDao.updateCountByQuantity(quantity, pstockId, mid);
	}
	
	@Transactional
	public int updateOptions(ProductToCart product, String mid) {
		String oldPstockId = product.getProductStockId();
		String newPstockId = product.getProductCommonId() + "_" + product.getColorCode() + "_" + product.getSizeCode();
		int quantity = product.getQuantity();
		
		CartUpdate cartUpdate = new CartUpdate();
		cartUpdate.setMemberId(mid);
		cartUpdate.setNewPstockId(newPstockId);
		cartUpdate.setOldPstockId(oldPstockId);
		cartUpdate.setNewQuantity(quantity);
		
		List<Cart> selectBeforeUpdate = cartDao.selectBeforeUpdate(cartUpdate);
		if (selectBeforeUpdate.size() < 1) {
			return cartDao.update(cartUpdate);
		} else {
			cartUpdate.setOldQuantity(selectBeforeUpdate.get(0).getQuantity());
			cartDao.updateQuantity(cartUpdate);
			return cartDao.deleteToUpdate(cartUpdate);
		}
	}

	@Transactional
	public int insertCart(Cart cart) {
		return cartDao.insertCart(cart);
	}

	@Transactional
	public void deleteCart(List<Cart> carts) {
		for(Cart cart:carts) {
			cartDao.deleteByMidAndPstockid(cart);
		}
	}
}
