package studentmanagement.persistant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import studentmanagement.persistant.dto.StudentRequestDto;
import studentmanagement.persistant.dto.StudentResponseDto;


public interface StudentInterface {
String insert="insert into student\r\n"
		+ "		(student_id,student_name,class_name,register_date,status)\r\n"
		+ "		values(#{studentId},#{studentName},#{className},#{registerDate},#{status})\r\n"
		+ "";
String update="update student set\r\n"
		+ "		student_name=#{studentName},class_name=#{className},register_date=#{registerDate},status=#{status}\r\n"
		+ "		where student_id=#{studentId}\r\n"
		+ "";
String delete="		delete from student where student_id=#{studentId}\r\n"
		+ "";
String selectAll="select * from student";
String selectOne="select * from student where student_id=#{studentId} or student_name=#{studentName} or class_name=#{className}"
		+ "";


@Insert(insert)
public int insert(StudentRequestDto dto);
@Update(update)
public int update(StudentRequestDto dto);
@Delete(delete)
public int delete(StudentRequestDto dto);
@Select(selectAll)
@Results(value= {
		@Result(property = "studentId",column = "student_id"),
		@Result(property = "studentName",column = "student_name"),
		@Result(property = "className",column = "class_name"),
		@Result(property = "registerDate",column = "register_date"),
		@Result(property = "status",column = "status"),
})
public List<StudentResponseDto> selectAll( ) ;

@Select(selectOne)
@Results(value= {
		@Result(property = "studentId",column = "student_id"),
		@Result(property = "studentName",column = "student_name"),
		@Result(property = "className",column = "class_name"),
		@Result(property = "registerDate",column = "register_date"),
		@Result(property = "status",column = "status"),

})
public List<StudentResponseDto> selectOne(StudentRequestDto dto);

}
