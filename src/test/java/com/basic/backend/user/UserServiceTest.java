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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        doReturn(new User(savedUserSeq, request.getId(), encryptedPw, request.getName(), request.getPhone(), request.getEmail(), savedCreateDate))
                .when(userRepository)
                .save(any(User.class));

        // when
        UserDto.UserAddRes user = userService.add(request);

        // then
        assertThat(user.getId()).isEqualTo(request.getId());
        assertThat(encoder.matches(request.getPassword(), user.getPassword())).isTrue();

        // verify
        verify(userRepository, times(1)).save(any(User.class)); // save가 1번 호출됐는지 검증
        verify(passwordEncoder, times(1)).encode(any(String.class));
    }

    private UserDto.UserAddReq getUserAddRequest() {
        return UserDto.UserAddReq.builder()
                .id("id")
                .password("password")
                .name("name")
                .phone("phone")
                .email("email")
                .build();
    }

    @DisplayName("사용자 목록 조회")
    @Test
    void findAll() {
        // given
        doReturn(userList())
                .when(userRepository)
                .findAll();

        // when
        final List<UserDto.UserRes> userList = userService.getList();

        // then
        assertThat(userList.size()).isEqualTo(5);
    }

    private List<User> userList() {
        List<User> userList = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            userList.add(new User(i, "id"+i, "password"+i, "name"+i, "phone"+i, "email"+i, new Date()));
        }
        return userList;
    }

}
