package com.example.food.service.user;

import com.example.food.advice.CommonException;
import com.example.food.constant.Constant;
import com.example.food.domain.Role;
import com.example.food.domain.User;
import com.example.food.dto.command.UserRegisterCommand;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.UserView;
import com.example.food.repository.IRoleRepository;
import com.example.food.repository.IUserRepository;
import com.example.food.security.principal.UserPrinciple;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Lazy(value = false)
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findFirstByUsername(username);
        if (!userOptional.isPresent()) throw new UsernameNotFoundException(username);
        return UserPrinciple.build(userOptional.get());
    }

    public UserView register(@NonNull UserRegisterCommand command) {
        Set<ConstraintViolation<UserRegisterCommand>> constraintViolations = validator.validate(command);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
        if (userRepository.existsByEmail(command.getEmail()))
            throw new CommonException(Response.EMAIL_IS_EXISTS, Response.EMAIL_IS_EXISTS.getResponseMessage());
        if (userRepository.existsByUsername(command.getUsername()))
            throw new CommonException(Response.USERNAME_IS_EXISTS, Response.USERNAME_IS_EXISTS.getResponseMessage());

        User user = User.builder()
                .username(command.getUsername())
                .password(passwordEncoder.encode(command.getPassword()))
                .name(command.getUsername())
                .avatar(Constant.IMAGE_USER_DEFAULT)
                .phone(command.getPhone())
                .email(command.getEmail())
                .channel(Constant.ChannelName.USER)
                .status(Constant.UserStatus.ACTIVATE)
                .createdAt(Instant.now())
                .createdBy(command.getUsername())
                .build();
        Set<Role> userRoles=new HashSet<>();
        userRoles.add(roleRepository.findByName("USER"));
        user.setRoles(userRoles);
        User userSave = userRepository.save(user);
        return UserView.from(userSave);
    }

    public Optional<User> findFirstByUsername(String username){
        return userRepository.findFirstByUsername(username);
    }

    public List<UserView> findAll(DefaultQueryCriteria criteria) {
        Pageable pageable= PageRequest.of(criteria.getPage()-1, criteria.getSize(), criteria.getSortable());
        Page<User> pageUsers = userRepository.findAll(pageable);
        if(pageUsers.getTotalElements()==0) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        return pageUsers.getContent().stream().map(UserView::from)
                .collect(Collectors.toList());

    }

    public UserView findById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        else return UserView.from(optionalUser.get());
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) userRepository.deleteById(id);
        else throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
    }
}
