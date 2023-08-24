package com.basic.backend.user.service;

import com.basic.backend.user.domain.User;
import com.basic.backend.user.domain.UserDto;
import com.basic.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto.UserAddRes add(final UserDto.UserAddReq userAddReq) {
        final User user = User.builder()
                .id(userAddReq.getId())
                .password(passwordEncoder.encode(userAddReq.getPassword()))
                .name(userAddReq.getName())
                .phone(userAddReq.getPhone())
                .email(userAddReq.getEmail())
                .createDate(new Date())
                .build();

        User savedUser = userRepository.save(user);
        return UserDto.UserAddRes.builder()
                .userSeq(savedUser.getUserSeq())
                .id(savedUser.getId())
                .password(savedUser.getPassword())
                .build();
    }

    public List<UserDto.UserRes> getList() {
        return userRepository.findAll().stream()
                .map(user -> UserDto.UserRes.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .createDate(user.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }

}
