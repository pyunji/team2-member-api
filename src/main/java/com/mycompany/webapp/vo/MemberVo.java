package com.mycompany.webapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVo {
	private String mid;
	private String mpassword;
	private String mname;
	private String memail;
	private String mphone;
	private String mtel;
	private String mzipcode;
	private String maddress1;
	private String maddress2;
	private Date birth;
	private int gender;
	private String ref_id;
	private String login_type;
	private int tos_no;
	private int menabled;
	private String mrole;
	private Date sign_up_date;
	private int grade_level;
}