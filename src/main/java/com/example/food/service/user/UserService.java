package com.example.food.service.user;

import com.example.food.advice.CommonException;
import com.example.food.constant.Constant;
import com.example.food.domain.Role;
import com.example.food.domain.User;
import com.example.food.dto.command.UserRegisterCommand;
import com.example.food.dto.command.UserUpdateCommand;
import com.example.food.dto.criteria.DefaultQueryCriteria;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.UserView;
import com.example.food.repository.IRoleRepository;
import com.example.food.repository.IUserRepository;
import com.example.food.security.principal.UserPrinciple;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.io.IOException;
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
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName("USER"));
        user.setRoles(userRoles);
        User userSave = userRepository.save(user);
        return UserView.from(userSave);
    }

    public Optional<User> findFirstByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public Page<UserView> findAll(DefaultQueryCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), criteria.getSortable());
        Page<User> pageUsers = userRepository.findAll(pageable);
        if (pageUsers.getTotalElements() == 0) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        List<UserView> list = pageUsers.getContent().stream().map(UserView::from)
                .collect(Collectors.toList());

        Page<UserView> page = new PageImpl<>(list, pageable, pageUsers.getTotalElements());
        return page;

    }

    public UserView findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findFirstByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        } else return UserView.from(optionalUser.get());
    }

    public UserView findById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        } else return UserView.from(optionalUser.get());
    }

    public void delete(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) userRepository.deleteById(id);
        else throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
    }

    public UserView update(String username, @NonNull UserUpdateCommand command) {
        Optional<User> optionalUser = userRepository.findFirstByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        User user = optionalUser.get();

        boolean updated = false;
        if (command.contains("name")) {
            updated = true;
            user.setName(command.getName());
        }
        if (command.contains("phone")) {
            updated = true;
            user.setPhone(command.getPhone());
        }
        if (command.contains("avatar")) {
            updated = true;
            user.setAvatar(command.getAvatar());
        }
        if (updated) {
            return UserView.from(userRepository.save(user));
        } else {
            return UserView.from(user);
        }
    }

    public UserView adminUpdate(@NonNull UUID id, @NonNull UserUpdateCommand command) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        User user = optionalUser.get();

        boolean updated = false;
        if (command.contains("name")) {
            updated = true;
            user.setName(command.getName());
        }
        if (command.contains("phone")) {
            updated = true;
            user.setPhone(command.getPhone());
        }
        if (command.contains("avatar")) {
            updated = true;
            user.setAvatar(command.getAvatar());
        }
        if (updated) {
            return UserView.from(userRepository.save(user));
        } else {
            return UserView.from(user);
        }
    }

    public UserView changeStatus(@NonNull UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        User user = optionalUser.get();
        if (user.getStatus().equals(Constant.UserStatus.ACTIVATE)) {
            user.setStatus(Constant.UserStatus.INACTIVATE);
        } else {
            user.setStatus(Constant.UserStatus.ACTIVATE);
        }
        return UserView.from(userRepository.save(user));
    }

    public UserView uploadAvatar(UUID id, MultipartFile file[]) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        User user = optionalUser.get();
        byte[] fileContent=null;
        try {
            fileContent=file[0].getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String outputFile = Base64.getEncoder().encodeToString(fileContent);
        user.setAvatar("data:image/png;base64,"+outputFile);
        User userSave = userRepository.save(user);
        return UserView.from(userSave);
    }
}

