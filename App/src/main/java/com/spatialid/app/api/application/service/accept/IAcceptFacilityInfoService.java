// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.accept;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spatialid.app.api.presentation.dto.accept.AcceptFacilityInfoRequestDto;
import com.spatialid.app.api.presentation.dto.accept.RegistTaskResponseDto;

/**
 * 埋設物情報取得受付用のServiceインターフェース.
 * 
 * @author ukai jun
 * @version 1.1 2024/09/24
 */
@Service
public interface IAcceptFacilityInfoService {

    /**
     * 設備データ出力タスクAPIを呼び出すメソッド.
     * 
     * @param requestDto
     * @param br
     * @return taskId タスクID
     * @throws JsonProcessingException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException 
     */
    public ResponseEntity<RegistTaskResponseDto> registOutputTasks(AcceptFacilityInfoRequestDto requestDto, String servicerId)
            throws JsonProcessingException, IOException, URISyntaxException;

    /**
     * SQSにメッセージを送信するメソッド.
     * 
     * 
     * @param taskId
     * @throws JsonProcessingException
     * @throws Exception
     */
    public void sendMessageSQS(RegistTaskResponseDto registTaskResponseDto)throws Exception ;

}
