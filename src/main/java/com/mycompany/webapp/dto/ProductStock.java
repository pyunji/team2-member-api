package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class ProductStock {
	private String id;
	private String productColorId;
	private String sizeCode;
	private int stock;
}
