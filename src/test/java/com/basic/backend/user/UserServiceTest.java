package com.basic.backend.user;

import com.basic.backend.user.domain.User;
import com.basic.backend.user.domain.UserDto;
import com.basic.backend.user.repository.UserRepository;
import com.basic.backend.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원 가입")
    @Test
    void add() {
        // given
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDto.UserAddReq request = this.getUserAddRequest();
        String encryptedPw = encoder.encode(request.getPassword());

        Long savedUserSeq = 1L;
        Date savedCreateDate = new Date();
        doReturn(new User(savedUserSeq, request.getUserId(), encryptedPw, request.getName(), request.getPhone(), request.getEmail(), savedCreateDate))
                .when(userRepository)
                .save(any(User.class));

        // when
        UserDto.UserAddRes user = userService.add(request);

        // then
        assertThat(user.getUserId()).isEqualTo(request.getUserId());
//        assertThat(encoder.matches(request.getPassword(), user.getPassword())).isTrue();

        // verify
        verify(userRepository, times(1)).save(any(User.class)); // save가 1번 호출됐는지 검증
        verify(passwordEncoder, times(1)).encode(any(String.class));
    }

    private UserDto.UserAddReq getUserAddRequest() {
        return UserDto.UserAddReq.builder()
                .userId("id")
                .password("password")
                .name("name")
                .phone("phone")
                .email("email")
                .build();
    }

    @DisplayName("사용자 목록 조회")
    @Test
    void getUsers() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        doReturn(userList())
                .when(userRepository)
                .findAll(pageable);

        // when
        Page<UserDto.UserRes> pUsers = userService.getUsersOld(pageable);
        System.out.println("size >> " + pUsers.getContent().size());

        // then
        assertThat(pUsers.getContent().size()).isEqualTo(5);
    }

    private Page<User> userList() {
        List<User> users = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            users.add(new User(i, "id"+i, "password"+i, "name"+i, "phone"+i, "email"+i, new Date()));
        }

        Page<User> pUsers = new PageImpl<>(users);
        return pUsers;
    }

    @DisplayName("사용자 조회")
    @Test
    void getUser() throws Exception {
        // given
        Long savedUserSeq = 1L;
        Date savedCreateDate = new Date();
        doReturn(Optional.of(new User(savedUserSeq, "id", "pw", "name", "phone", "email", savedCreateDate)))
                .when(userRepository)
                .findById(savedUserSeq);

        // when
        UserDto.UserRes rUser = userService.getUser(savedUserSeq);
        System.out.println("rUser >> " + rUser);

        // then
        assertThat(rUser.getUserId()).isEqualTo("id");
    }

    @DisplayName("사용자 수정")
    @Test
    void update() {
        // given
        UserDto.UserUdtReq userUdtReq = UserDto.UserUdtReq.builder()
                .userId("userId")
//                .password("pw1")
                .name("name1")
                .phone("phone1")
                .email("email1")
                .build();

        String userId = "userId";
        doReturn(Optional.of(new User(1L, "userId", "pw", "name", "phone", "email", new Date())))
                .when(userRepository)
                .findByUserId(userId);
        doReturn(new User(1L, "userId", null, userUdtReq.getName(), userUdtReq.getPhone(), userUdtReq.getEmail(), new Date()))
                .when(userRepository)
                .save(any(User.class));

        // when
        UserDto.UserRes userRes = userService.update(userUdtReq);
        System.out.println("userRes >> " + userRes);

        // then
        assertThat(userRes.getName()).isEqualTo("name1");
    }

    @DisplayName("사용자 삭제")
    @Test
    void remove() {
        // given
        String userId = "userId";
        doReturn(Optional.of(new User(1L, "userId", "pw", "name", "phone", "email", new Date())))
                .when(userRepository)
                .findByUserId(userId);

        // when
        userService.remove(userId);

        // then
        verify(userRepository, times(1)).findByUserId(any(String.class));
        verify(userRepository, times(1)).delete(any(User.class));
    }

}
