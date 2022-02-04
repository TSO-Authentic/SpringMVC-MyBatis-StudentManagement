package studentmanagement.service;

import java.util.List;

import studentmanagement.persistant.dto.UserRequestDto;
import studentmanagement.persistant.dto.UserResponseDto;

public interface UserService {
	public int insert(UserRequestDto dto);
	public int update(UserRequestDto dto);
	public int delete(UserRequestDto dto);
	public List<UserResponseDto> selectAll() ;
	public List<UserResponseDto> selectOne(UserRequestDto dto);
}
