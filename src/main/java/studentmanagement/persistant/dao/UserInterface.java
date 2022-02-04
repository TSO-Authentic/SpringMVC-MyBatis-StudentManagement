package studentmanagement.persistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import studentmanagement.persistant.dto.UserRequestDto;
import studentmanagement.persistant.dto.UserResponseDto;

public interface UserInterface {

	String insert = "insert into user (id,name,password) values(#{id},#{name},#{password})";

	String update = "update user set name=#{name},password=#{password} where id=#{id}";

	String delete = "delete from user where id=#{id}";
	String selectAll = "select * from user";
	String selectOne = "select * from user where id=#{id} or name=#{name}";

	@Insert(insert)
	public int insert(UserRequestDto dto);

	@Update(update)
	public int update(UserRequestDto dto);

	@Delete(delete)
	public int delete(UserRequestDto dto);

	@Select(selectAll)
	@Results(value= {
			@Result(property = "id",column = "id"),
			@Result(property = "name",column = "name"),
			@Result(property = "password",column = "password")
	})
	public List<UserResponseDto> selectAll( );

	@Select(selectOne)
	@Results(value= {
			@Result(property = "id",column = "id"),
			@Result(property = "name",column = "name"),
			@Result(property = "password",column = "password")
	})
	public List<UserResponseDto> selectOne(UserRequestDto dto);

}
