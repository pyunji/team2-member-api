package com.mycompany.webapp.vo;


import java.util.Date;

import lombok.Data;

@Data
public class Mileage {
	private String memberId;
	private Date issueDate;
	private int amount;
	private String name;
	private String content;
	private String category;
	private Date expireDate;
	private String status;
}
