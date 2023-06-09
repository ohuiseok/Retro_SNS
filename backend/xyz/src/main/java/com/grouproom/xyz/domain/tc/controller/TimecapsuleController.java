package com.grouproom.xyz.domain.tc.controller;

import com.grouproom.xyz.domain.tc.dto.reqeust.AddTcContentRequest;
import com.grouproom.xyz.domain.tc.dto.reqeust.AddTcOpenRequest;
import com.grouproom.xyz.domain.tc.dto.reqeust.AddTcRequest;
import com.grouproom.xyz.domain.tc.dto.reqeust.TcListRequest;
import com.grouproom.xyz.domain.tc.dto.response.AddTcResponse;
import com.grouproom.xyz.domain.tc.dto.response.OpenedTcDetailsResponse;
import com.grouproom.xyz.domain.tc.dto.response.OpenedTcListResponse;
import com.grouproom.xyz.domain.tc.dto.response.TcListResponse;
import com.grouproom.xyz.domain.tc.service.TcService;
import com.grouproom.xyz.global.model.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/timecapsule")
@RequiredArgsConstructor
public class TimecapsuleController {

    private final TcService tcService;
    private final Logger logger = Logger.getLogger("com.grouproom.xyz.domain.tc.controller.TimecapsuleController");

    @GetMapping()
    public BaseResponse<?> tcList(@ModelAttribute TcListRequest tcListRequest) {
        logger.info("TcList 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        TcListResponse tcListResponse = tcService.findTcList(tcListRequest.getAztSeq());

        return new BaseResponse(tcListResponse);
    }

    @GetMapping("mytimecapsule")
    public BaseResponse<?> myTcList(@RequestParam(name = "tcSeq", required = false) Long tcSeq) {
        logger.info("myTcList 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        TcListResponse tcListResponse = tcService.findMyTcList(userSeq);

        return new BaseResponse(tcListResponse);
    }

    @GetMapping("/opened")
    public BaseResponse<?> openedTcList(@RequestParam(name = "tcSeq", required = false) Long tcSeq) {
        logger.info("openedTcList 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        OpenedTcListResponse openedTcListResponse = tcService.findOpenedTcList(userSeq);

        return new BaseResponse(openedTcListResponse);
    }

    @GetMapping("/waiting")
    public BaseResponse<?> waitingTcList(@RequestParam(name = "tcSeq", required = false) Long tcSeq) {
        logger.info("waitingTcList 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        TcListResponse tcListResponse = tcService.findWaitingTcList(userSeq);

        return new BaseResponse(tcListResponse);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<?> addTc(@RequestPart AddTcRequest addTcRequest, @RequestPart(required = false) List<MultipartFile> images, @RequestPart(required = false) List<MultipartFile> audios) throws Exception {
        logger.info("addTc 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        AddTcResponse addTcResponse = tcService.addTc(userSeq, addTcRequest, images, audios);

        return new BaseResponse(addTcResponse);
    }

    @PostMapping(value = "/{tcSeq}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<?> addTcContent(@PathVariable("tcSeq") Long tcSeq, @RequestPart AddTcContentRequest addTcContentRequest, @RequestPart(required = false) List<MultipartFile> images, @RequestPart(required = false) List<MultipartFile> audios) throws Exception {
        logger.info("addTcContent 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        tcService.addTcContent(userSeq, tcSeq, addTcContentRequest.getContent(), images, audios);

        return new BaseResponse("타임캡슐 내용 추가 성공");
    }

    @GetMapping("/{tcSeq}")
    public BaseResponse<?> openedTcDetails(@PathVariable("tcSeq") Long tcSeq) throws Exception {
        logger.info("openedTcDetails 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        OpenedTcDetailsResponse openedTcDetailsResponse = tcService.findOpenedTcDetails(userSeq, tcSeq);

        return new BaseResponse(openedTcDetailsResponse);
    }

    @GetMapping("/random")
    public BaseResponse<?> randomOpenedTcDetails() throws Exception {
        logger.info("randomOpenedTcDetails 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        OpenedTcDetailsResponse openedTcDetailsResponse = tcService.findRandomOpenedTcDetails(userSeq);

        return new BaseResponse(openedTcDetailsResponse);
    }

    @PostMapping("/open/{tcSeq}")
    public BaseResponse<?> addTcOpen(@PathVariable("tcSeq") Long tcSeq, @RequestBody AddTcOpenRequest addTcOpenRequest) {
        logger.info("addTcOpen 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        String openStatus = tcService.addTcOpen(userSeq, tcSeq, addTcOpenRequest);

        return new BaseResponse(openStatus);
    }
}
