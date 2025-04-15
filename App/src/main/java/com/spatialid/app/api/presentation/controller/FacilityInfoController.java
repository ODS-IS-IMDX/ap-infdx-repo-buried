// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.spatialid.app.api.application.service.facilityinfo.IBaseFactory;
import com.spatialid.app.api.application.service.facilityinfo.IBaseHandler;
import com.spatialid.app.api.constants.FacilityInfoConstants;
import com.spatialid.app.api.presentation.dto.facilityinfo.AttributeDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.FacilityInfoChainDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetFacilityInfoRequestDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetFacilityInfoResponseDto;
import com.spatialid.app.common.constant.RestApiConstants;
import com.spatialid.app.common.exception.subexception.ParamErrorException;
import com.spatialid.app.common.validation.Sequence;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 埋設物情報取得のエンドポイントを定義したコントローラークラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/21
 */
@RestController
public class FacilityInfoController {
    
    /**
     * チェーン作成を行うファクトリ．
     */
    private final IBaseFactory facilityInfoChainFactory;
    
    public FacilityInfoController(IBaseFactory facilityInfoChainFactory) {
        
        this.facilityInfoChainFactory = facilityInfoChainFactory;
        
    }
    
    /**
     * 埋設物情報取得APIのエンドポイントを定義する．
     * 
     * @param request 埋設物情報取得のリクエストDto
     * @param br バリデーション結果
     * @param httpServletRequest 埋設物情報取得のリクエスト
     * @return レスポンス
     * @throws Exception
     */
    @GetMapping(FacilityInfoConstants.FACILITY_INFO_URI)
    public ResponseEntity<GetFacilityInfoResponseDto> getFacilityInfo(
            @Validated(Sequence.class) @ModelAttribute GetFacilityInfoRequestDto request,
            BindingResult br,
            HttpServletRequest httpServletRequest) throws Exception {
        
        if (br.hasErrors()) {
            
            throw new ParamErrorException(br);
            
        }
        
        final String servicerId = (String) httpServletRequest.getAttribute(RestApiConstants.SERVICER_ID);
        
        final FacilityInfoChainDto facilityInfoChainDto = FacilityInfoChainDto.builder()
                .sids(request.getSids())
                .servicerId(servicerId)
                .searchArea(request.getSearchArea())
                .epsgCode(request.getEpsgCode() != null ? request.getEpsgCode().intValue() : null)
                .infraCompanyIds(request.getInfraCompanyIds())
                .returnZoomLevel(request.getReturnZoomLevel())
                .updateDate(request.getUpdateDate())
                .build();
        
        final IBaseHandler<FacilityInfoChainDto, AttributeDto> chainedHandler = switch(facilityInfoChainDto.getSids()) {
            
            case null -> facilityInfoChainFactory.createHandlerChain(FacilityInfoConstants.POLYGON_TYPE);
            default -> facilityInfoChainFactory.createHandlerChain(FacilityInfoConstants.SID_TYPE);
            
        };
        
        final GetFacilityInfoResponseDto responseDto = GetFacilityInfoResponseDto.builder()
                .dataModelType(RestApiConstants.TEST1)
                .attribute(chainedHandler.handle(facilityInfoChainDto))
                .build();
        
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
        
    }
    
}
