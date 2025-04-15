// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.accept;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 埋設物情報. InformationResponseDtoのフィールドの一つ。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/21
 */
@Data
public class FacilitySidDto {

	/** インフラ事業者ID */
	private String infraCompanyId;

	/** オブジェクトID */
	private String objectId;

	/** オブジェクト名称 */
	private String objectName;

	/** 設備属性 */
	private Map<String, String> facilityAttribute;

	/** データ種別 */
	private String dataType;

	/** 空間ID */
	private List<String> sidList;

}
