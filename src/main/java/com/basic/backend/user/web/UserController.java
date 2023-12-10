package com.basic.backend.user.web;

import com.basic.backend.user.domain.UserDto;
import com.basic.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // todo
//    @Value("${spring.test.profile-name}")
//    private final String profileName;

//    @GetMapping("/profiles")
//    public ResponseEntity<String> getProfiles() { return ResponseEntity.ok(profileName); }

    @PostMapping("/user")
    public ResponseEntity<UserDto.UserAddRes> add(@RequestBody UserDto.UserAddReq request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.add(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto.UserRes>> getUsers(@PageableDefault(page = 1) Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable).getContent());
    }

    @GetMapping("/users/{userSeq}")
    public ResponseEntity<UserDto.UserRes> getUser(@PathVariable(name = "userSeq") Long userSeq) throws Exception {
        return ResponseEntity.ok(userService.getUser(userSeq));
    }

    @PutMapping("/user")
    public ResponseEntity<UserDto.UserRes> update(@RequestBody UserDto.UserUdtReq userUdtReq) {
        return ResponseEntity.ok(userService.update(userUdtReq));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> remove(@PathVariable(name = "userId") String userId) {
        userService.remove(userId);
        return ResponseEntity.ok(null);
    }

}
