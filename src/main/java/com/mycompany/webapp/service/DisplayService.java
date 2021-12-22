
package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.db1member.DisplayDao;
import com.mycompany.webapp.dto.ContextDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DisplayService {
	@Resource private DisplayDao displayDao;
	public List<ContextDto> getList() {
		
		List<ContextDto> list = displayDao.selectAllContext();
		
		return list;
	}
}