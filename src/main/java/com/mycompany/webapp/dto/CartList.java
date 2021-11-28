package com.mycompany.webapp.dto;

import com.mycompany.webapp.vo.Brand;
import com.mycompany.webapp.vo.Cart;
import com.mycompany.webapp.vo.ProductColor;
import com.mycompany.webapp.vo.ProductCommon;
import com.mycompany.webapp.vo.ProductStock;

import lombok.Data;

@Data
public class CartList {
	private ProductCommon productCommon;
	private ProductColor productColor;
	private ProductStock productStock;
	private Brand brand;
	private Cart cart;
}
