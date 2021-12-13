package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class MemberDto {
	private String mid;
	private String mname;
	private String mpassword;
	private int menabled;
	private String mrole;
	private String memail;
	private String mphone;
}
