// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.accept;

import lombok.Data;

/**
 * 設備データ出力タスク登録を呼び出した際のレスポンスを格納するDtoクラス.
 * 
 * @auther ukai jun
 * @version 1.1 2024/10/16
 */
@Data
public class RegistTaskResponseDto {

    /** タスクID */
    String taskId;
}
