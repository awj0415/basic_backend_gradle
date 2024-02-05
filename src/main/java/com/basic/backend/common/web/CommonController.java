package com.basic.backend.common.web;

import com.basic.backend.common.domain.CommonDto;
import com.basic.backend.common.file.S3FileUtil;
import com.basic.backend.common.response.BaseResponse;
import com.basic.backend.common.response.BaseResult;
import com.basic.backend.common.response.BaseResultCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Common API")
@RestController
@RequiredArgsConstructor
public class CommonController {

    private final S3FileUtil s3FileUtil;

    @PostMapping("/file/upload")
    public ResponseEntity<BaseResult<CommonDto.FileUploadRes>> upload(
            @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        if (multipartFile == null) return BaseResponse.fail(BaseResultCode.COMMON_FILE_NOT_EXIST);

        try {
            String uploadedFileUrl = s3FileUtil.upload(multipartFile, "public");
            return BaseResponse.ok(new CommonDto.FileUploadRes(uploadedFileUrl));
        } catch (IOException e) {
            return BaseResponse.fail(BaseResultCode.COMMON_FILE_PROCESSING_FAIL);
        }
    }

}
