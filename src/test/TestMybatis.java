package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPath;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pojo.User;

public class TestMybatis {
	
	private InputStream is ;
	
	private SqlSessionFactory factory;
	
	private SqlSession sqlSession;
	
	@Before
	public void before() throws IOException {
		
		
		System.out.println("打开数据库");
		//通过流获取核心配置文件 sqlMapConfig.xml
		is = Resources.getResourceAsStream("sqlMapConfig.xml");
		//获取数据源
		factory = 
				new SqlSessionFactoryBuilder().build(is);
		//打开sqlsession对象
		sqlSession = factory.openSession();
		System.out.println("数据库连接打开成功");
	}
	@After
	public void after() throws IOException {
		System.out.println("关闭数据库");
		sqlSession.close();
	}
	//单值传递
	@Test
	public void test01() {
		int id = 50000;
		User user = sqlSession.selectOne
				("pojo.UserMapper.findOne", id);
/*		Map m=new HashMap();
		m=sqlSession.selectOne("pojo.UserMapper.findOne1", id);*/
		System.out.println(user);
		System.out.println("============test1  执行===================");
	}
	
	//多值传递  ： 区间查询
	@Test
	public void test02() {
		//使用map集合，进行多值传递
		//map集合配合 #{}使用，获取多个值
		HashMap<String,Integer> map = new HashMap<String,Integer>(8);
		int minAge = 15;
		map.put("baoxunminAge", minAge);
		int maxAge = 25;
		map.put("baoxunmaxAge", maxAge);
		
		List<Map> list = sqlSession.selectList
				("pojo.UserMapper.findUserByAges", map);
		for (Map user : list) {
			System.out.println(user);
		}
		System.out.println("============test2  执行===================");

	}
	
	//动态的插入
	@Test
	public void test03() {
		User user = new User();
		user.setAge(29);
		user.setName("报讯");
		user.setSex("men");
		user.setId(50010);
		sqlSession.insert("pojo.UserMapper.dynamicAddUser", user);
		sqlSession.commit();
	}
	
	//动态的修改
	@Test
	public void test04() {
		User user = new User();
		user.setName("张三");
		user.setId(50012);
		int i =sqlSession.update("pojo.UserMapper.dynamicUpdateUser", user);
		System.out.println("qianmian"+i);
		sqlSession.commit();
		System.out.println(i);
	}
	
	//动态的删除：方式一：array数组
	@Test
	public void test05() {
		int[] ids = new int[2];
		ids[0] = 50000;
		ids[1] = 50001;
		//ids[2] = 50002;
		sqlSession.delete("pojo.UserMapper.dynamicDeleteUserByArray", ids);
		sqlSession.commit();
	}
	
	//动态的删除：方式一：array数组
/*	@Test
	public void test105() {
		int[] ids = {50000,50001,50002};
		List<User> list=sqlSession.selectList("pojo.UserMapper.selectdynamicDeleteUserByArray", ids);
		sqlSession.commit();
	}*/
	
	//动态的删除：方式二：list集合
/*	@Test
	public void test06() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(6);
		list.add(7);
		sqlSession.delete("pojo.UserMapper.dynamicDeleteUserByList", list);
		sqlSession.commit();
	}*/
	
	//动态的删除：方式三：map
/*	@Test
	public void test07() {
		//定义map集合
		HashMap<String, Object> map =new HashMap<String,Object>();
		List<Integer> list = new ArrayList<Integer>();
		list.add(6);
		list.add(7);
		
		//ids 对应着  id的list集合
		map.put("ids", list);
		//需求：删除 sex 为 男 的 数据
		map.put("sex", "男");
		
		sqlSession.delete("pojo.UserMapper.dynamicDeleteUserByMap", map);
		sqlSession.commit();
	}*/
	
}
