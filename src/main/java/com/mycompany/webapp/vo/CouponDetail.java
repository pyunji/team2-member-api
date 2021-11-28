package com.mycompany.webapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CouponDetail {
	private String couponId;
	private int eventNo;
	private String memberId;
	private Date issueDate;
	private Date expireDate;
	private Date useDate;
}
