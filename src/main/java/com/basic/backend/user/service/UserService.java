package com.basic.backend.user.service;

import com.basic.backend.user.domain.User;
import com.basic.backend.user.domain.UserDto;
import com.basic.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto.UserAddRes add(final UserDto.UserAddReq userAddReq) {
        final User user = User.builder()
                .userId(userAddReq.getUserId())
                .password(passwordEncoder.encode(userAddReq.getPassword()))
                .name(userAddReq.getName())
                .phone(userAddReq.getPhone())
                .email(userAddReq.getEmail())
                .createDate(new Date())
                .build();

        User savedUser = userRepository.save(user);
        return UserDto.UserAddRes.builder()
                .userSeq(savedUser.getUserSeq())
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .phone(savedUser.getPhone())
                .email(savedUser.getEmail())
                .build();
    }

    public Page<UserDto.UserRes> getUsersOld(Pageable pageable) {
        Page<User> pUsers = userRepository.findAll(pageable);
        pUsers.getTotalElements();

        return pUsers.map(user -> UserDto.UserRes.builder()
                .userSeq(user.getUserSeq())
                .userId(user.getUserId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
//                .createDate(user.getCreateDate())
                .build());
    }

    public UserDto.GetUsersRes getUsers(UserDto.GetUsersReq req) {
        int rowCntPerPage = 5;
        PageRequest pageRequest = PageRequest.of(req.getCurrentPage() - 1, rowCntPerPage, Sort.Direction.DESC, "userSeq");
        Page<User> pUsers = userRepository.findAllByUserIdContainingOrNameContaining(req.getSearchText(), req.getSearchText(), pageRequest);

        List<UserDto.GetUsersRes.User> users = pUsers.map(user -> UserDto.GetUsersRes.User.builder()
                .userSeq(user.getUserSeq())
                .userId(user.getUserId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build())
                .getContent();

        return UserDto.GetUsersRes.builder()
                .users(users)
                .totalCount(pUsers.getTotalElements())
                .rowCntPerPage(rowCntPerPage)
                .build();
    }

    public UserDto.UserRes getUser(Long userSeq) throws Exception {
        Optional<User> oUser = userRepository.findById(userSeq);

        return oUser.map(user -> UserDto.UserRes.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
//                .createDate(user.getCreateDate())
                .build())
                .orElseThrow(Exception::new);
    }

    public UserDto.UserRes update(UserDto.UserUdtReq userUdtReq) {
        Optional<User> oUser = userRepository.findByUserId(userUdtReq.getUserId());
        if (!oUser.isPresent()) new Exception("update >> no user!");

        User user = oUser.get();
        user.setName(userUdtReq.getName());
//        user.setPassword(userUdtReq.getPassword());
        user.setPhone(userUdtReq.getPhone());
        user.setEmail(userUdtReq.getEmail());
        User rUser = userRepository.save(user);

        return UserDto.UserRes.builder()
                .userId(rUser.getUserId())
                .name(rUser.getName())
                .phone(rUser.getPhone())
                .email(rUser.getEmail())
//                .createDate(rUser.getCreateDate())
                .build();
    }

    public void remove(String userId) {
        Optional<User> oUser = userRepository.findByUserId(userId);
        if (!oUser.isPresent()) new Exception("remove >> no user");

        userRepository.delete(oUser.get());
    }

}
