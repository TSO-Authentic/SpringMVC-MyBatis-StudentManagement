package studentmanagement.service;

import java.util.List;

import studentmanagement.persistant.dto.StudentRequestDto;
import studentmanagement.persistant.dto.StudentResponseDto;


public interface StudentService {
	public int insert(StudentRequestDto dto);
	public int update(StudentRequestDto dto);
	public int delete(StudentRequestDto dto);
	public List<StudentResponseDto> selectAll( ) ;
	public List<StudentResponseDto> selectOne(StudentRequestDto dto);
}
