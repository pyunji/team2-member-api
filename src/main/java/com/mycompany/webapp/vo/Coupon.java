package com.mycompany.webapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Coupon {
	private String couponId;
	private int eno;
	private String mid;
	private Date cissueDate;
	private Date cexpireDate;
	private Date cuseDate;
}
