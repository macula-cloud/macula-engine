package org.macula.engine.mybatis;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.macula.engine.mybatis.entity.User;
import org.macula.engine.mybatis.mapper.UserMapper;
import org.macula.engine.mybatis.vo.UserVO;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = MybatisPlusApplication.class)
public class UserMapperTest {

	@Resource
	private UserMapper userMapper;

	@Test
	@Order(1)
	public void testSelect() {
		List<User> userList = userMapper.selectList(null);
		assertThat(userList.size()).isEqualTo(5);
		userList.forEach(System.out::println);
	}

	@Test
	@Order(2)
	public void testPage() {
		IPage<User> userPages = userMapper.selectPage(new Page<>(1, 2), null);
		assertThat(userPages.getTotal()).isEqualTo(5);
		assertThat(userPages.getPages()).isEqualTo(3);
		userPages.getRecords().forEach(System.out::println);
	}

	@Test
	@Order(100)
	public void testKeyAndFill() throws Exception {
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setAge(20);
		user.setEmail("email01@xxx.com");
		user.setName("name01");
		int i = userMapper.insert(user);
		assertThat(i).isGreaterThan(0);
		assertThat(user.getVersion()).isEqualTo(0);
		users.add(user);

		User user2 = new User();
		user2.setName("name02");
		user2.setId(user.getId());
		int r = userMapper.updateById(user2);
		assertThat(r).isGreaterThan(0);
		assertThat(user2.getVersion()).isEqualTo(1);
		users.add(user2);

		User user3 = new User();
		user3.setName("name03");
		user3.setVersion(1);
		user3.setId(user.getId());
		int r2 = userMapper.updateById(user3);
		assertThat(r2).isGreaterThan(0);
		assertThat(user3.getVersion()).isEqualTo(2);
		users.add(user3);
		User user4 = userMapper.selectById(6L);
		users.add(user4);
		users.forEach(System.out::println);
	}

	@Test
	public void testFindCustom() {
		List<UserVO> vo = userMapper.listById(1L);
		assertThat(vo.size()).isEqualTo(1);
		assertThat(vo.get(0).getName()).isEqualTo("Jone");
	}
}
