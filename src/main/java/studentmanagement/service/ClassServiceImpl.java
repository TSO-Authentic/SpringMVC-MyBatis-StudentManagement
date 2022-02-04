package studentmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import studentmanagement.persistant.dao.ClassInterface;
import studentmanagement.persistant.dto.ClassRequestDto;
import studentmanagement.persistant.dto.ClassResponseDto;

public class ClassServiceImpl implements ClassService {

	@Override
	public int insert(ClassRequestDto dto) {
		int result = 0;
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.getConfiguration().addMapper(ClassInterface.class);
			result = session.getMapper(ClassInterface.class).insert(dto);
			session.commit();
		}
		return result;

	}

	@Override
	public List<ClassResponseDto> selectAll() {
		List<ClassResponseDto> list = new ArrayList<>();
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.getConfiguration().addMapper(ClassInterface.class);
			list = session.getMapper(ClassInterface.class).selectAll();
		}
		return list;
	}

	@Override
	public List<ClassResponseDto> selectOne(ClassRequestDto dto) {
		List<ClassResponseDto> list = new ArrayList<>();
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.getConfiguration().addMapper(ClassInterface.class);
			list = session.getMapper(ClassInterface.class).selectOne(dto);
		}
		return list;
	}

}
