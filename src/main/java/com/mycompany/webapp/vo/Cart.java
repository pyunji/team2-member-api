package com.mycompany.webapp.vo;

import lombok.Data;

@Data
public class Cart {
	private String memberId;
	private String productStockId;
	private int quantity;
}
