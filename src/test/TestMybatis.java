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
		
		
		System.out.println("�����ݿ�");
		//ͨ������ȡ���������ļ� sqlMapConfig.xml
		is = Resources.getResourceAsStream("sqlMapConfig.xml");
		//��ȡ����Դ
		factory = 
				new SqlSessionFactoryBuilder().build(is);
		//��sqlsession����
		sqlSession = factory.openSession();
		System.out.println("���ݿ����Ӵ򿪳ɹ�");
	}
	@After
	public void after() throws IOException {
		System.out.println("�ر����ݿ�");
		sqlSession.close();
	}
	//��ֵ����
	@Test
	public void test01() {
		int id = 50000;
		User user = sqlSession.selectOne
				("pojo.UserMapper.findOne", id);
/*		Map m=new HashMap();
		m=sqlSession.selectOne("pojo.UserMapper.findOne1", id);*/
		System.out.println(user);
		System.out.println("============test1  ִ��===================");
	}
	
	//��ֵ����  �� �����ѯ
	@Test
	public void test02() {
		//ʹ��map���ϣ����ж�ֵ����
		//map������� #{}ʹ�ã���ȡ���ֵ
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
		System.out.println("============test2  ִ��===================");

	}
	
	//��̬�Ĳ���
	@Test
	public void test03() {
		User user = new User();
		user.setAge(29);
		user.setName("��Ѷ");
		user.setSex("men");
		user.setId(50010);
		sqlSession.insert("pojo.UserMapper.dynamicAddUser", user);
		sqlSession.commit();
	}
	
	//��̬���޸�
	@Test
	public void test04() {
		User user = new User();
		user.setName("����");
		user.setId(50012);
		int i =sqlSession.update("pojo.UserMapper.dynamicUpdateUser", user);
		System.out.println("qianmian"+i);
		sqlSession.commit();
		System.out.println(i);
	}
	
	//��̬��ɾ������ʽһ��array����
	@Test
	public void test05() {
		int[] ids = new int[2];
		ids[0] = 50000;
		ids[1] = 50001;
		//ids[2] = 50002;
		sqlSession.delete("pojo.UserMapper.dynamicDeleteUserByArray", ids);
		sqlSession.commit();
	}
	
	//��̬��ɾ������ʽһ��array����
/*	@Test
	public void test105() {
		int[] ids = {50000,50001,50002};
		List<User> list=sqlSession.selectList("pojo.UserMapper.selectdynamicDeleteUserByArray", ids);
		sqlSession.commit();
	}*/
	
	//��̬��ɾ������ʽ����list����
/*	@Test
	public void test06() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(6);
		list.add(7);
		sqlSession.delete("pojo.UserMapper.dynamicDeleteUserByList", list);
		sqlSession.commit();
	}*/
	
	//��̬��ɾ������ʽ����map
/*	@Test
	public void test07() {
		//����map����
		HashMap<String, Object> map =new HashMap<String,Object>();
		List<Integer> list = new ArrayList<Integer>();
		list.add(6);
		list.add(7);
		
		//ids ��Ӧ��  id��list����
		map.put("ids", list);
		//����ɾ�� sex Ϊ �� �� ����
		map.put("sex", "��");
		
		sqlSession.delete("pojo.UserMapper.dynamicDeleteUserByMap", map);
		sqlSession.commit();
	}*/
	
}
