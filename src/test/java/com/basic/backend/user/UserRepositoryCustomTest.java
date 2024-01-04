package com.basic.backend.user;

import com.basic.backend.user.domain.User;
import com.basic.backend.user.domain.UserDto;
import com.basic.backend.user.repository.UserRepositoryCustom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryCustomTest {

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;

    @DisplayName("사용자 목록 조회 (custom)")
    @Test
    void findBySearch() {
        // given
//        userRepository.save(user());
        UserDto.GetUsersReq req = new UserDto.GetUsersReq();
        req.setSearchText("user");

        // when
        List<User> users = userRepositoryCustom.findBySearch(req);
        System.out.println("size >> " + users.size());

        // then
        assertThat(users.size()).isEqualTo(2);
    }

}
