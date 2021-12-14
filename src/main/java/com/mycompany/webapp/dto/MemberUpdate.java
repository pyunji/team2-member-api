package com.mycompany.webapp.dto;


import java.sql.Date;

import lombok.Data;

@Data
public class MemberUpdate {
	private String mid;
	private String memail;
	private String mphone;
	private String mtel;
	private String mzipcode;
	private String maddress1;
	private String maddress2;
	private Date birth;
	private int gender;
	private String ref_id;
}
