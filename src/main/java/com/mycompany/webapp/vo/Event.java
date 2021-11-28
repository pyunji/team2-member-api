package com.mycompany.webapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Event {
	private int no;
	private String title;
	private String content;
	private Date issueDate;
	private Date expireDate;
	private int limitCount;
	private int count;
	private String img;
}
