package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.study.springboot.dto.SimpleBbsDto;

@Mapper
public interface ISimpleBbsDao
{
	public List<SimpleBbsDto> listDao();
	public SimpleBbsDto viewDao(String id);
	//	게시물 수정 -> Map컬렉션을 사용하므로 #{key값1}, #{key값2}...로 사용한다.
	public int writeDao(Map<String, String> map);
	public int deleteDao(@Param("_id")String id);
	public int articleCount();
}
