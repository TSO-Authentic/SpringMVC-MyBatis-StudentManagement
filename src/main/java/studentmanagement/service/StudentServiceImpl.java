package studentmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import studentmanagement.persistant.dao.StudentInterface;
import studentmanagement.persistant.dto.StudentRequestDto;
import studentmanagement.persistant.dto.StudentResponseDto;

public class StudentServiceImpl implements StudentService {

	@Override
	public int insert(StudentRequestDto dto) {
		int result = 0;
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
		session.getConfiguration().addMapper(StudentInterface.class);
			result = session.getMapper(StudentInterface.class).insert(dto);
			session.commit();
		}
		return result;

	}

	@Override
	public int update(StudentRequestDto dto) {
		int result = 0;
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.getConfiguration().addMapper(StudentInterface.class);
			result = session.getMapper(StudentInterface.class).update(dto);
			session.commit();
		}
		return result;
	}

	@Override
	public int delete(StudentRequestDto dto) {
		int result = 0;
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
		session.getConfiguration().addMapper(StudentInterface.class);
			result = session.getMapper(StudentInterface.class).delete(dto);
			session.commit();
		}
		return result;
	}

	@Override
	public List<StudentResponseDto> selectAll() {
		List<StudentResponseDto> list = new ArrayList<StudentResponseDto>();

		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.getConfiguration().addMapper(StudentInterface.class);
			list = session.getMapper(StudentInterface.class).selectAll();
			session.commit();
		}
		return list;
	}

	@Override
	public List<StudentResponseDto> selectOne(StudentRequestDto dto) {

		 List<StudentResponseDto> res = new ArrayList<>();
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			session.getConfiguration().addMapper(StudentInterface.class);
			res = session.getMapper(StudentInterface.class).selectOne(dto);
			session.commit();
		}
		return res;
	}

}
