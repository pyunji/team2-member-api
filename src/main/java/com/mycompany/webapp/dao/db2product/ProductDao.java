package com.mycompany.webapp.dao.db2product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.CartItem;
import com.mycompany.webapp.dto.ProductColorData;

@Mapper
public interface ProductDao {
	CartItem selectPnameAndBname(String pcommonid, String pcolorid, String pstockid);
	List<ProductColorData> selectProductColorStockData(String pcommonid);
}
