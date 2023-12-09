package com.basic.backend.user;

import com.basic.backend.user.domain.UserDto;
import com.basic.backend.user.service.UserService;
import com.basic.backend.user.web.UserController;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Value("${spring.test.profile-name}")
    private String profileName;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @DisplayName("profile 확인")
    @Test
    void getPropertiesConfirm() {
        System.out.printf("profileName : " + profileName);
    }

    @DisplayName("회원 가입 성공")
    @Test
    void addSuccess() throws Exception {
        // given
        UserDto.UserAddReq request = getUserAddRequest();
        UserDto.UserAddRes response = getUserAddResponse();

        doReturn(response)
                .when(userService)
                .add(any(UserDto.UserAddReq.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("userSeq", response.getUserSeq()).exists())
                .andExpect(jsonPath("id", response.getUserId()).exists())
                .andExpect(jsonPath("password", response.getPassword()).exists())
                .andReturn();
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

    private UserDto.UserAddRes getUserAddResponse() {
        return UserDto.UserAddRes.builder()
                .userSeq(1L)
                .userId("id")
                .password("password")
                .build();
    }

    @DisplayName("사용자 목록 조회")
    @Test
    void getUserList() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 1);
        Page<UserDto.UserRes> pUsers = getUsers();
        doReturn(pUsers)
                .when(userService)
                .getUsers(pageable);
        System.out.println("given!");

        // todo No primary or single unique constructor found for interface org.springframework.data.domain.Pageable
        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .param("page", "0")
                        .param("size", "1")
        );
        System.out.println("when!");

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();

        List<UserDto.UserRes> userList = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), List.class);
        assertThat(userList.size()).isEqualTo(1);
    }

    private Page<UserDto.UserRes> getUsers() {
        List<UserDto.UserRes> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserDto.UserRes userRes = UserDto.UserRes.builder()
                    .userId("id" + i)
                    .name("name" + i)
                    .phone("phone" + i)
                    .email("email" + i)
                    .createDate(new Date())
                    .build();
            users.add(userRes);
        }

        Page<UserDto.UserRes> pUsers = new PageImpl<>(users);
        return pUsers;
    }


}
