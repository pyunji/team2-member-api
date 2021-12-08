package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartItem {
	// Member DB에서 접근
	String pstockid;
	String pcolorid;
	String pcommonid;
	Integer quantity;
	String occode; // 아이템의 원래 색상 코드
	String oscode; // 아이템의 원래 사이즈 코드

	// Product DB에서 접근 - product_common
	String pname;
	String bname;

	// Product DB에서 접근 - product_color
	String img1;
	Integer pprice;
	Integer stock;
	
	// Product DB에서 접근
	List<ProductColorData> productColorData;
	/*
	 [
	 	{
	 	"pcolorid": CMQ193JD_DK
	 	"ccode": DK,
	 	"colorImg": url,
	 	"productStockData":
	 		[
	 			{
	 				"pstockid": CMQ193JD_DK_80
	 				"scode": 80,
	 				"pstock": 5
	 			},
	 			{
	 				"pstockid": CMQ193JD_DK_90
	 				"scode": 90,
	 				"pstock": 4
	 			},
	 		]
	 	}
	 ]
	 */
}
