package com.grouproom.xyz.domain.tc.service;

import com.grouproom.xyz.domain.tc.dto.reqeust.AddTcOpenRequest;
import com.grouproom.xyz.domain.tc.dto.reqeust.AddTcRequest;
import com.grouproom.xyz.domain.tc.dto.response.AddTcResponse;
import com.grouproom.xyz.domain.tc.dto.response.OpenedTcDetailsResponse;
import com.grouproom.xyz.domain.tc.dto.response.OpenedTcListResponse;
import com.grouproom.xyz.domain.tc.dto.response.TcListResponse;
import com.grouproom.xyz.domain.tc.entity.TcContent;
import com.grouproom.xyz.global.model.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TcService {

    void saveTcContentFiles(TcContent tcContent, FileType fileType, List<String> filePaths);

    AddTcResponse addTc(Long userSeq, AddTcRequest addTcRequest, List<MultipartFile> images, List<MultipartFile> audios);

    void addTcContent(Long userSeq, Long tcSeq, String content, List<MultipartFile> images, List<MultipartFile> audios);

    OpenedTcDetailsResponse findOpenedTcDetails(Long userSeq, Long tcSeq);

    OpenedTcDetailsResponse findRandomOpenedTcDetails(Long userSeq);

    OpenedTcListResponse findOpenedTcList(Long userSeq);

    TcListResponse findWaitingTcList(Long userSeq);

    TcListResponse findTcList(Long aztSeq);

    TcListResponse findMyTcList(Long userSeq);

    String addTcOpen(Long userSeq, Long tcSeq, AddTcOpenRequest addTcOpenRequest);
}
