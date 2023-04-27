package com.grouproom.xyz.domain.azt.controller;

import com.grouproom.xyz.domain.azt.dto.request.AztRequest;
import com.grouproom.xyz.domain.azt.service.AztService;
import com.grouproom.xyz.global.model.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
@RequestMapping("/azt")
public class AztController {

    private final Logger logger = Logger.getLogger("com.grouproom.xyz.domain.azt.controller.AztController");
    private final AztService aztService;

    @PostMapping
    public BaseResponse<?> addAzt(@RequestBody AztRequest aztRequest) {
        logger.info("addAzt 호출");
        Long loginSeq = 1L;
        try {
            return new BaseResponse<>(aztService.addAzt(loginSeq, aztRequest));
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "실패", "");
        }
    }
}
