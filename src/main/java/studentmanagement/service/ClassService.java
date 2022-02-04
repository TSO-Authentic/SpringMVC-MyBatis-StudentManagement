package studentmanagement.service;

import java.util.List;

import studentmanagement.persistant.dto.ClassRequestDto;
import studentmanagement.persistant.dto.ClassResponseDto;


public interface ClassService {
	public int insert(ClassRequestDto dto);
	public List<ClassResponseDto>selectOne(ClassRequestDto dto);
	public List<ClassResponseDto> selectAll();
}
