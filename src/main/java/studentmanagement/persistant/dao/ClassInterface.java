package studentmanagement.persistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import studentmanagement.persistant.dto.ClassRequestDto;
import studentmanagement.persistant.dto.ClassResponseDto;


public interface ClassInterface {
	String insert="insert into class (id,name) values(#{id},#{name})";
	String selectOne = "select * from class where id=#{id}";
	String selectAll="select * from class";
	
	@Insert(insert)
	public int insert(ClassRequestDto dto);
	
	@Select(selectAll)
	@Results(value= {
			@Result(property = "id",column = "id"),
			@Result(property = "name",column = "name")
	})
	public List<ClassResponseDto> selectAll();
	
	@Select(selectOne)
	@Results(value= {
			@Result(property = "id",column = "id"),
			@Result(property = "name",column = "name")
	})
	public List<ClassResponseDto> selectOne(ClassRequestDto dto);
}
