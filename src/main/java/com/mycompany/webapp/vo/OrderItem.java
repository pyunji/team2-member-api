package com.mycompany.webapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OrderItem {
	private String productStockId;
	private String ordersId;
	private int count;
	private int totalPrice;
	private Date orderByTime;
	private Date orderDeleteTime;
}
