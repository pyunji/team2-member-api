package com.mycompany.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stock {
	private String productStockId;
	private int quantity;
}
