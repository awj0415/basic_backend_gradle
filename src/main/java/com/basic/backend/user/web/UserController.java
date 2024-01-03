package com.basic.backend.user.web;

import com.basic.backend.user.domain.UserDto;
import com.basic.backend.user.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "사용자 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Value("${spring.test.profile-name}")
    private String profileName;

    @GetMapping("/profiles")
    public ResponseEntity<String> getProfiles() { return ResponseEntity.ok(profileName); }

    @ApiOperation(
            value = "사용자 등록",
            notes = "사용자를 등록한다"
    )
    @ApiResponse(code = 201, message = "성공입니다")
    @PostMapping("/user")
    public ResponseEntity<UserDto.UserAddRes> add(@RequestBody UserDto.UserAddReq request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.add(request));
    }

    @ApiOperation(
            value = "사용자 리스트 조회",
            notes = "사용자 리스트를 조회한다 (페이징)"
    )
//    @ApiImplicitParams({
//        @ApiImplicitParam(
//            name="page",
//            value = "요청 페이지",
//            dataType = "Integer",
//            paramType = "query", // query:@RequestParam, path:@PathVariable
//            defaultValue = "0"
//        ),
//        @ApiImplicitParam(
//            name="size",
//            value = "row 개수",
//            dataType = "Integer",
//            paramType = "query",
//            defaultValue = "10"
//        )
//    })
    @ApiResponse(code = 200, message = "성공입니다")
    @GetMapping("/users")
    public ResponseEntity<UserDto.GetUsersRes> getUsers(
//            @ApiIgnore @PageableDefault(page = 1) Pageable pageable
            UserDto.GetUsersReq req
    ) {
        return ResponseEntity.ok(userService.getUsers(req));
    }

    @ApiOperation(
            value = "사용자 조회",
            notes = "사용자를 조회한다"
    )
    @ApiResponse(code = 200, message = "성공입니다")
    @GetMapping("/users/{userSeq}")
    public ResponseEntity<UserDto.UserRes> getUser(@PathVariable(name = "userSeq") Long userSeq) throws Exception {
        return ResponseEntity.ok(userService.getUser(userSeq));
    }

    @ApiOperation(
            value = "사용자 수정",
            notes = "사용자를 수정한다"
    )
    @ApiResponse(code = 200, message = "성공입니다")
    @PutMapping("/user")
    public ResponseEntity<UserDto.UserRes> update(@RequestBody UserDto.UserUdtReq userUdtReq) {
        return ResponseEntity.ok(userService.update(userUdtReq));
    }

    @ApiOperation(
            value = "사용자 삭제",
            notes = "사용자를 삭제한다(유저 ID로)"
    )
    @ApiResponse(code = 200, message = "성공입니다")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> remove(@PathVariable(name = "userId") String userId) {
        userService.remove(userId);
        return ResponseEntity.ok(null);
    }

}
