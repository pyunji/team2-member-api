package com.mycompany.webapp.dto;


import java.sql.Date;

import lombok.Data;

@Data
public class CouponEvent {
	private String couponId;
	private int eno;
	private String mid;
	private Date cissueDate;
	private Date cexpireDate;
	private Date cuseDate;
	private String etitle;
	private String econtent;
	private String ethumbnail;
}
