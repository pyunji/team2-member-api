
package com.mycompany.webapp.dao.db1member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.ContextDto;
import com.mycompany.webapp.vo.Wish;

@Mapper
public interface DisplayDao {
	List<ContextDto> selectAllContext();
}