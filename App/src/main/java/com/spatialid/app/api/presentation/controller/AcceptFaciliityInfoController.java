// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spatialid.app.api.application.service.accept.IAcceptFacilityInfoService;
import com.spatialid.app.api.constants.AcceptFacilityInfoConstants;
import com.spatialid.app.api.presentation.dto.accept.AcceptFacilityInfoRequestDto;
import com.spatialid.app.api.presentation.dto.accept.AcceptFacilityInfoResponseDto;
import com.spatialid.app.api.presentation.dto.accept.RegistTaskResponseDto;
import com.spatialid.app.common.constant.RestApiConstants;
import com.spatialid.app.common.exception.subexception.InternalApiCallingException;
import com.spatialid.app.common.exception.subexception.ParamErrorException;
import com.spatialid.app.common.validation.Sequence;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 埋設物情報取得APIを管理するControllerクラス。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/22
 * 
 */
@RestController
public class AcceptFaciliityInfoController {

    /** Serviceインターフェース. */
    @Autowired
    IAcceptFacilityInfoService acceptFacilityInfoService;

    /**
     * 埋設物情報取得受付API
     * 
     * @param requestDto 埋設物情報取得受付用リクエストDto
     * @param br         バインディングリザルト
     * @return タスクID
     * @throws Exception
     */
    @PutMapping(AcceptFacilityInfoConstants.ACCEPT_URI)
    public ResponseEntity<AcceptFacilityInfoResponseDto> acceptFacilityInfo(
            @Validated(Sequence.class) @RequestBody AcceptFacilityInfoRequestDto requestDto, BindingResult br,
            HttpServletRequest request) throws Exception {

        // servicerIdを受け取る
        String servicerId = (String) request.getAttribute(RestApiConstants.SERVICER_ID);

        // 1.パラメータチェック
        if (br.hasErrors()) {
            throw new ParamErrorException(br);
        }

        // 2.設備データ出力タスク登録にリクエストを送り、レスポンスを取得
        ResponseEntity<RegistTaskResponseDto> outPutTasksResponse = acceptFacilityInfoService
                .registOutputTasks(requestDto, servicerId);

        // ステータスコード2xx以外の時、エラー処理を行う
        if (!outPutTasksResponse.getStatusCode().is2xxSuccessful()) {

            throw new InternalApiCallingException(AcceptFacilityInfoConstants.OUTPUTTASKS_NAME);
        }

        // レスポンスボディを取得
        RegistTaskResponseDto registTaskResponseDto = outPutTasksResponse.getBody();

        // 3.SQSにメッセージを送信
        acceptFacilityInfoService.sendMessageSQS(registTaskResponseDto);

        // 4.レスポンスデータ項目を設定
        AcceptFacilityInfoResponseDto responseDto = new AcceptFacilityInfoResponseDto();

        String taskId = registTaskResponseDto.getTaskId();

        responseDto.setDataModelType(RestApiConstants.TEST1);
        responseDto.attribute.put("taskId", taskId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}