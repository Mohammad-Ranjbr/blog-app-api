package com.blog;

import com.blog.entities.User;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository ;

	@Test
	void contextLoads() {
	}

	@Test
	public void serviceTest(){
		String className = this.userService.getClass().getName();
		String packageName = this.userService.getClass().getPackage().getName();
		System.out.println("Class Name = "+className);
		System.out.println("Package Name = "+packageName);
	}

}
