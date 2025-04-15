// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.accept;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spatialid.app.api.constants.AcceptFacilityInfoConstants;
import com.spatialid.app.api.presentation.dto.accept.AcceptFacilityInfoRequestDto;
import com.spatialid.app.api.presentation.dto.accept.RegistTaskResponseDto;
import com.spatialid.app.common.constant.RestApiConstants;
import com.spatialid.app.common.property.RestClientProperty;
import com.spatialid.app.common.property.secrets.RestClientSecretsProperty;
import com.spatialid.app.common.property.secrets.SqsClientSecretsProperty;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

/**
 * 埋設物情報取得受付用のServiceクラス.
 * 
 * @author ukai jun
 * @version 1.1 2024/09/24
 */
@Service
public class AcceptFacilityInfoServiceImpl implements IAcceptFacilityInfoService {

    /**
     * RestClientの情報を保持するクラス.
     */
    private final RestClientProperty restClientProperty;

    /**
     * RestClientのシークレット情報を保持するクラス.
     */
    private final RestClientSecretsProperty restClientSecretsProperty;

    /**
     * SqsClientのシークレット情報を保持するクラス．
     */
    private final SqsClientSecretsProperty sqsClientSecretsProperty;

    public AcceptFacilityInfoServiceImpl(RestClientProperty restClientProperty, 
            RestClientSecretsProperty restClientSecretsProperty,
            SqsClientSecretsProperty sqsClientSecretsProperty) {
        
        this.restClientProperty = restClientProperty;
        this.restClientSecretsProperty = restClientSecretsProperty;
        this.sqsClientSecretsProperty = sqsClientSecretsProperty;
        
    }
    
    @Override
    public ResponseEntity<RegistTaskResponseDto> registOutputTasks(AcceptFacilityInfoRequestDto requestDto,
            String servicerId) throws JsonMappingException, JsonProcessingException, URISyntaxException {

        // setRequestBodyメソッドを呼び出し、リクエストを構成
        ObjectNode requestJson = setRequestBody(requestDto, servicerId);

        // レスポンス初期化
        ResponseEntity<RegistTaskResponseDto> responseDto = new ResponseEntity<RegistTaskResponseDto>(
                HttpStatus.INTERNAL_SERVER_ERROR);
        // クライアントを作成
        RestClient restClient = RestClient.create();
        
        URI uri = new URI( 
                restClientProperty.getProtocol(),
                null,
                restClientSecretsProperty.getCommonDomain(),
                Integer.valueOf(restClientProperty.getPort()),
                AcceptFacilityInfoConstants.OUTPUT_TASKS_URI,
                null,
                null
                );
        
        try {
            // リクエストを送信
            responseDto = restClient.post()
                    .uri(uri.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestJson)
                    .retrieve()
                    .toEntity(RegistTaskResponseDto.class);

        } catch (Exception e) {

        }

        return responseDto;
    }

    /**
     * 設備データ出力タスクAPIに渡すリクエストを設定するメソッド.
     * 
     * @param requestDto リクエストDto
     * @param servicerId 利用者システムID
     * @return ObjectNode 設備データ出力タスクAPIへ送るリクエスト
     * @throws JsonProcessingException
     */
    private ObjectNode setRequestBody(AcceptFacilityInfoRequestDto requestDto, String servicerId)
            throws JsonProcessingException {

        // タスク作成日時へ代入する現在時刻の設定
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(RestApiConstants.DATE_FORMAT);
        String formatNow = now.format(dtf2);

        // requestDtoのフィールド内容をJson化
        ObjectMapper mapper = new ObjectMapper();
        String requestString = mapper.writeValueAsString(requestDto);
        ObjectNode parsedRequest = (ObjectNode) mapper.readTree(requestString);

        // リクエスト内容を構成
        ObjectNode requestJson = mapper.createObjectNode();

        requestJson.put("taskCreateDate", formatNow);
        requestJson.put("taskStatus", RestApiConstants.TASK_STATUS_UNTREATED);
        requestJson.put("processClass", RestApiConstants.PROCESSCLASS);
        requestJson.put("servicerId", servicerId);
        requestJson.put("request", parsedRequest);

        return requestJson;

    }

    @Override
    public void sendMessageSQS(RegistTaskResponseDto registTaskResponseDto) {

            // タスクIDを取得
            String taskId = registTaskResponseDto.getTaskId();

            // シークレットマネージャーから取得したキューURL
            String queueUrlValue = sqsClientSecretsProperty.getQueueUrl();

            // SQSクライアントの作成
            SqsClient sqsClient = SqsClient.builder().region(Region.AP_NORTHEAST_1).build();

            SendMessageRequest request = SendMessageRequest
                    .builder()
                    .queueUrl(queueUrlValue)
                    .messageBody(taskId)
                    //メッセージグループID
                    .messageGroupId(AcceptFacilityInfoConstants.MESSAGE_GROUP_ID)
                    // メッセージ重複排除ID
                    .messageDeduplicationId(String.valueOf(UUID.randomUUID()))
                    .build();

            sqsClient.sendMessage(request);

            sqsClient.close();

    }

}
