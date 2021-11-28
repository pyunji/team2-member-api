package com.mycompany.webapp.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.CartDao;
import com.mycompany.webapp.dto.CartUpdate;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.ProductToCart;
import com.mycompany.webapp.dto.Size;
import com.mycompany.webapp.vo.Cart;
import com.mycompany.webapp.vo.Category;

@Service
public class CartService {
	@Resource private CartDao cartDao;
	public List<Product> getList(String mid) {
		return cartDao.selectList(mid);
	}
	
	public List<Color> getColors(String pcommonId) {
		return cartDao.selectColorsByPcommonId(pcommonId);
	}
	
	/*	public List<Size> getSizesByPcommonId(String pcommonId) {
			return cartDao.selectSizesByPcommonId(pcommonId);
		}*/
	
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
	
	public Category setCategories(String pcolorId) {
		return cartDao.selectCategoryByPcolorId(pcolorId);
	}

	@Transactional
	public int insertCart(Cart cart) {
		return cartDao.insertCart(cart);
	}

	@Transactional
	public void deleteCart(List<Cart> carts) {
		for(Cart cart:carts) {
			cartDao.deleteByMemberIdAndProductStockId(cart);
		}
	}
}
