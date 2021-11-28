package com.mycompany.webapp.dto;

import com.mycompany.webapp.vo.Pager;

import lombok.Data;

@Data
public class CategoryDepth {
	private String depth1;
	private String depth2;
	private String depth3;
	private Pager pager;
}
