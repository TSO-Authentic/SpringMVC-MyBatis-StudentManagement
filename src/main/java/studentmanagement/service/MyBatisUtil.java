package studentmanagement.service;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {

	static SqlSessionFactory sqlSessionFactory;
	public static SqlSessionFactory getSqlSessionFactory() {
	try {
	Reader reader=Resources.getResourceAsReader("mybatis-config.xml");
	sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	} catch (IOException e) {
	System.out.println("mybatis configuration is not found");
	}
	return sqlSessionFactory;
	}
}
