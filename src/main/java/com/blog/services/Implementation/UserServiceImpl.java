package com.blog.services.Implementation;

import  com.blog.config.AppConstans;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository ;
    private final ModelMapper modelMapper ;
    private final PasswordEncoder passwordEncoder ;
    private final RoleRepository roleRepository ;
    private Role role ;

    @Autowired
    public UserServiceImpl(UserRepository userRepository , ModelMapper modelMapper , PasswordEncoder passwordEncoder , RoleRepository roleRepository,Role role){
        this.userRepository = userRepository ;
        this.modelMapper = modelMapper ;
        this.passwordEncoder = passwordEncoder ;
        this.roleRepository = roleRepository ;
        this.role = role ;
    }

    @Override
    @Transactional
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(roleRepository.findById(AppConstans.NORMAL_USER).isPresent()){
            role = roleRepository.findById(AppConstans.NORMAL_USER).get();
        }
        user.getRoles().add(role);
        User registeredUser = userRepository.save(user);
        return this.userToUserDto(registeredUser);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = userRepository.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = userRepository.save(user);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::userToUserDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",String.valueOf(userId)));
        userRepository.delete(user);
    }

    public User userDtoToUser(UserDto userDto){
        return  modelMapper.map(userDto,User.class);
    }

    public UserDto userToUserDto(User user){
        return modelMapper.map(user,UserDto.class);
    }

}
