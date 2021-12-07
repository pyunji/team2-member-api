package com.mycompany.webapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductColorData {
	String pcolorid;
	String ccode;
	String colorImg;
	List<ProductStockData> productStockData;
}
