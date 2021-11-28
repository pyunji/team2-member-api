package com.mycompany.webapp.vo;

import lombok.Data;

@Data
public class PaymentMethod {
	String code;
	String company;
	int cardOrAccount; 
}
