package com.mycompany.webapp.dao.db2product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.CartItem;
import com.mycompany.webapp.dto.ProductColorData;
import com.mycompany.webapp.dto.WishItem;

@Mapper
public interface ProductDao {
	CartItem selectPnameAndBname(String pcommonid, String pcolorid, String pstockid);
	List<ProductColorData> selectProductColorStockData(String pcommonid);
	WishItem selectItemByPstockid(String pstockid);
}
