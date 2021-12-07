package com.mycompany.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.CartUpdate;
import com.mycompany.webapp.dto.Color;
import com.mycompany.webapp.dto.Size;
import com.mycompany.webapp.vo.Cart;

@Mapper
public interface CartDao {
	List<Map> selectList(String mid);
	List<Color> selectColorsByPcommonId(String pcommonId);
	
	List<Cart> selectBeforeUpdate(CartUpdate cartUpdate);
	int update(CartUpdate cartUpdate);
	int deleteToUpdate(CartUpdate cartUpdate);
	int updateQuantity(CartUpdate cartUpdate);
	
	
	
	List<Size> selectSizesByPcolorId(String PcolorId);
	int updateCountByQuantity(@Param("quantity") int quantity, @Param("pstockId") String pstockId, @Param("mid") String mid);
	int updatePstockId(@Param("newPstockId") String newPstockId, @Param("mid") String mid, @Param("oldPstockId") String oldPstockId);
	int insertCart(Cart cart);
	int deleteByMidAndPstockid(Cart cart);
}
