package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.vo.Cart;
import com.mycompany.webapp.dto.CartUpdate;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Product;
import com.mycompany.webapp.dto.Size;
import com.mycompany.webapp.vo.Category;

@Mapper
public interface CartDao {
	List<Product> selectList(String mid);
	List<Color> selectColorsByPcommonId(String pcommonId);
	
	List<Cart> selectBeforeUpdate(CartUpdate cartUpdate);
	int update(CartUpdate cartUpdate);
	int deleteToUpdate(CartUpdate cartUpdate);
	int updateQuantity(CartUpdate cartUpdate);
	
	
	
	List<Size> selectSizesByPcolorId(String PcolorId);
	int updateCountByQuantity(@Param("quantity") int quantity, @Param("pstockId") String pstockId, @Param("mid") String mid);
	int updatePstockId(@Param("newPstockId") String newPstockId, @Param("mid") String mid, @Param("oldPstockId") String oldPstockId);
	Category selectCategoryByPcolorId(String pcolorId);
	int insertCart(Cart cart);
	int deleteByMemberIdAndProductStockId(Cart cart);
}
