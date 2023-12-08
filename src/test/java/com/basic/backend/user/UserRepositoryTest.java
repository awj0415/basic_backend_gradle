package com.basic.backend.user;

import com.basic.backend.user.domain.User;
import com.basic.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // todo add rollback
    @DisplayName("사용자 추가")
    @Test
    void add() {
        // given
        User user = user();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getId()).isEqualTo(user.getId());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getName()).isEqualTo(user.getName());
    }

    private User user() {
        return User.builder()
                .id("id")
                .password("password")
                .name("name")
                .phone("phone")
                .email("email")
                .createDate(new Date())
                .build();
    }

    @DisplayName("사용자 목록 조회")
    @Test
    void findAll() {
        // given
        userRepository.save(user());

        // when
        List<User> userList = userRepository.findAll();

        // then
        assertThat(userList.size()).isEqualTo(2);
    }

    @DisplayName("사용자 조회")
    @Test
    void findById() {
        // given
        User savedUser = userRepository.save(user());
        System.out.println("savedUser >> " + savedUser);

        // when
        Optional<User> oUser = userRepository.findById(savedUser.getUserSeq());

        // then
        assertThat(oUser.isPresent()).isEqualTo(true);
        assertThat(oUser.get().getId()).isEqualTo("id");
    }

    @DisplayName("사용자 수정")
    @Test
    void update() {
        // given
        User savedUser = userRepository.save(user());
        System.out.println("savedUser >> " + savedUser);
        savedUser.setName("name2");

        // when
        User updatedUser = userRepository.save(savedUser);
        System.out.println("updatedUser >> " + updatedUser);

        // then
        assertThat(updatedUser.getName()).isEqualTo("name2");
    }

    // todo delete된게 롤백 되는듯
    @DisplayName("사용자 삭제")
    @Test
    void delete() {
        // given
        User savedUser = userRepository.save(user());
        System.out.println("savedUser >> " + savedUser);

        // when
        userRepository.delete(savedUser);
        System.out.println("deleted!!");

        // then
        Optional<User> oUser = userRepository.findById(savedUser.getUserSeq());
        System.out.println("isPresent >> " + oUser.isPresent());
        assertThat(oUser.isPresent()).isEqualTo(false);
    }

}
