package com.blog;

import com.blog.entities.Role;
//import com.blog.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	private final PasswordEncoder passwordEncoder;
	//private final RoleRepository roleRepository ;

	@Autowired
	public BlogAppApisApplication(PasswordEncoder passwordEncoder /*, RoleRepository roleRepository*/){
		this.passwordEncoder = passwordEncoder ;
		//this.roleRepository = roleRepository ;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

	@Bean
	public Role getRole(){
		return new Role();
	}

	@Override
	public void run(String... args) {
		System.out.println(passwordEncoder.encode("123456"));

//		Role role = new Role();
//		role.setId(AppConstans.ADMIN_USER);
//		role.setName("ROLE_ADMIN");
//
//		Role role1 = new Role();
//		role1.setId(AppConstans.NORMAL_USER);
//		role1.setName("ROLE_NORMAL");
//
//		roleRepository.save(role);
//		roleRepository.save(role1);

	}

}
