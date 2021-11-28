package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class CartUpdate {
	private String memberId;
	private String newPstockId;
	private String oldPstockId;
	private int newQuantity;
	private int oldQuantity;
}
