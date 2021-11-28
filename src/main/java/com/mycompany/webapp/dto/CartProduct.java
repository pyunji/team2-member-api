package com.mycompany.webapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartProduct {
	private String pcolorId;
	private String brandName;
	private String productName;
	private String colorCode;
	private String sizeCode;
	private String img;
	private Integer quantity;
	private Integer appliedPrice;
}
