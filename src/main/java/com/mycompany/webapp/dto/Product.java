package com.mycompany.webapp.dto;

import com.mycompany.webapp.vo.Brand;
import com.mycompany.webapp.vo.Category;
import com.mycompany.webapp.vo.ProductColor;
import com.mycompany.webapp.vo.ProductCommon;
import com.mycompany.webapp.vo.ProductStock;

import lombok.Data;

@Data
public class Product {
	private ProductCommon productCommon;
	private ProductColor productColor;
	private ProductStock productStock;
	private Brand brand;
	private Category category;
}
