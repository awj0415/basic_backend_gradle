package com.basic.backend.user.web;

import com.basic.backend.common.response.BaseResponse;
import com.basic.backend.common.response.BaseResult;
import com.basic.backend.common.response.BaseResultCode;
import com.basic.backend.user.domain.UserDto;
import com.basic.backend.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<BaseResult<UserDto.UserAddRes>> add(
            @RequestBody @Valid UserDto.UserAddReq request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return BaseResponse.getResponseEntity(
                    BaseResultCode.COMMON_INVALID_PARAMS, bindingResult.getAllErrors());
        }
        return BaseResponse.getResponseEntity(userService.add(request), BaseResultCode.SUCCESS_CREATE);
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
    public ResponseEntity<BaseResult<UserDto.GetUsersRes>> getUsers(
//            @ApiIgnore @PageableDefault(page = 1) Pageable pageable
            UserDto.GetUsersReq req
    ) {
        return BaseResponse.ok(userService.getUsers(req));
    }

    @ApiOperation(
            value = "사용자 조회",
            notes = "사용자를 조회한다"
    )
    @ApiResponse(code = 200, message = "성공입니다")
    @GetMapping("/users/{userSeq}")
    public ResponseEntity<BaseResult<UserDto.UserRes>> getUser(@PathVariable(name = "userSeq") Long userSeq) throws Exception {
        return BaseResponse.ok(userService.getUser(userSeq));
    }

    @ApiOperation(
            value = "사용자 수정",
            notes = "사용자를 수정한다"
    )
    @ApiResponse(code = 200, message = "성공입니다")
    @PutMapping("/user")
    public ResponseEntity<BaseResult<UserDto.UserRes>> update(@RequestBody UserDto.UserUdtReq userUdtReq) {
        return BaseResponse.ok(userService.update(userUdtReq));
    }

    @ApiOperation(
            value = "사용자 삭제",
            notes = "사용자를 삭제한다(유저 ID로)"
    )
    @ApiResponse(code = 200, message = "성공입니다")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> remove(@PathVariable(name = "userId") String userId) {
        userService.remove(userId);
        return BaseResponse.ok();
    }

}
