package com.grouproom.xyz.domain.memory.controller;

import com.grouproom.xyz.domain.memory.dto.request.AddMemoryRequest;
import com.grouproom.xyz.domain.memory.dto.request.MemoryListRequest;
import com.grouproom.xyz.domain.memory.dto.request.ModifyMemoryRequest;
import com.grouproom.xyz.domain.memory.dto.response.AddMemoryResponse;
import com.grouproom.xyz.domain.memory.dto.response.MemoryDetailResponse;
import com.grouproom.xyz.domain.memory.dto.response.MemoryListResponse;
import com.grouproom.xyz.domain.memory.service.MemoryService;
import com.grouproom.xyz.global.model.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/memory")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;
    private final Logger logger = Logger.getLogger("com.grouproom.xyz.domain.memory.controller.MemoryController");

    @GetMapping()
    public BaseResponse<?> memoryList(@ModelAttribute MemoryListRequest memoryListRequest) {
        logger.info("memoryList 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemoryListResponse memoryListResponse = memoryService.findMemory(userSeq, memoryListRequest);

        return new BaseResponse(memoryListResponse);
    }

    @GetMapping("/{memorySeq}")
    public BaseResponse<?> memoryDetail(@PathVariable("memorySeq") Long memorySeq) {
        logger.info("memoryDetail 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemoryDetailResponse memoryDetail = memoryService.findMemoryDetail(userSeq, memorySeq);

        return new BaseResponse(memoryDetail);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<?> addMemory(@RequestPart AddMemoryRequest addMemoryRequest, @RequestPart(required = false) List<MultipartFile> images, @RequestPart(required = false) List<MultipartFile> audios) throws Exception {
        logger.info("addMemory 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        AddMemoryResponse addMemoryResponse = memoryService.addMemory(userSeq, addMemoryRequest, images, audios);

        return new BaseResponse(addMemoryResponse);
    }

    @PutMapping(value = "/{memorySeq}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<?> modifyMemory(@PathVariable("memorySeq") Long memorySeq, @RequestPart ModifyMemoryRequest modifyMemoryRequest, @RequestPart(required = false) List<MultipartFile> images, @RequestPart(required = false) List<MultipartFile> audios) throws Exception {
        logger.info("modifyMemory 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        memoryService.modifyMemory(userSeq, memorySeq, modifyMemoryRequest, images, audios);

        return new BaseResponse("추억앨범 수정 성공");
    }

    @DeleteMapping("/{memorySeq}")
    public BaseResponse<?> removeMemory(@PathVariable("memorySeq") Long memorySeq) {
        logger.info("removeMemory 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        memoryService.removeMemory(userSeq, memorySeq);

        return new BaseResponse("추억앨범 삭제 성공");
    }

    @GetMapping("/mymemories")
    public BaseResponse<?> myMemoryList(@RequestParam(name = "memorySeq", required = false) Long memorySeq) {
        logger.info("myMemoryList 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemoryListResponse memoryListResponse = memoryService.findMyMemory(userSeq);

        return new BaseResponse(memoryListResponse);
    }

    @GetMapping("/like")
    public BaseResponse<?> likedMemoryList(@RequestParam(name = "memorySeq", required = false) Long memorySeq) {
        logger.info("likedMemoryList 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemoryListResponse memoryListResponse = memoryService.findLikedMemory(userSeq);

        return new BaseResponse(memoryListResponse);
    }

    @PostMapping("/like/{memorySeq}")
    public BaseResponse<?> addMemoryLike(@PathVariable("memorySeq") Long memorySeq) {
        logger.info("addMemoryLike 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        memoryService.addMemoryLike(userSeq, memorySeq);

        return new BaseResponse("좋아요 등록 성공");
    }

    @DeleteMapping("/like/{memorySeq}")
    public BaseResponse<?> removeMemoryLike(@PathVariable("memorySeq") Long memorySeq) {
        logger.info("removeMemoryLike 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        memoryService.removeMemoryLike(userSeq, memorySeq);

        return new BaseResponse("좋아요 삭제 성공");
    }

    @PostMapping("/comment/{memorySeq}")
    public BaseResponse<?> addMemoryComment(@PathVariable("memorySeq") Long memorySeq, @RequestBody Map<String, String> commentRequest) {
        logger.info("addMemoryComment 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        memoryService.addMemoryComment(userSeq, memorySeq, commentRequest.get("content"));

        return new BaseResponse("댓글 작성 성공");
    }

    @PutMapping("/comment/{commentSeq}")
    public BaseResponse<?> modifyMemoryComment(@PathVariable("commentSeq") Long commentSeq, @RequestBody Map<String, String> commentRequest) {
        logger.info("modifyMemoryComment 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        memoryService.modifyMemoryComment(userSeq, commentSeq, commentRequest.get("content"));

        return new BaseResponse("댓글 수정 성공");
    }

    @DeleteMapping("/comment/{commentSeq}")
    public BaseResponse<?> removeMemoryComment(@PathVariable("commentSeq") Long commentSeq) {
        logger.info("removeMemoryComment 호출");

        Long userSeq = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        memoryService.removeMemoryComment(userSeq, commentSeq);

        return new BaseResponse("댓글 삭제 성공");
    }
}
