// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.mapper;

import java.io.IOException;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

/**
 * 埋設物情報取得APIにおける設備属性情報のデシリアライズを実装するクラス．
 * <p>
 * 本クラスは、フィールドに付与される@JsonDeserializeの引数として与えられ、カスタムデシリアライズを行う想定．
 * <pre>{@code @JsonDeserialize(using = PrivateAttributeDeserializer.class) }</pre>
 * </p>
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/11/8
 */
@NoArgsConstructor
public class PrivateAttributeDeserializer extends JsonDeserializer<Map<String, String>> {
    
    /**
     * Jsonオブジェクトマッパー．
     */
    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
    /**
     * 設備属性情報のカスタムデシリアライザを実装する．
     * <p>
     * 設備属性情報がnullの場合は、空文字を返却する．
     * </p>
     * 
     * @param jp {@link JsonParser}
     * @param context {@link DeserializationContext}
     * @return マップ形式に変換された設備属性情報 もしくは null
     */
    @Override
    public Map<String, String> deserialize(JsonParser jp,
            DeserializationContext context) throws IOException {
        
        final JsonNode jnode = jp.getCodec().readTree(jp);
        
        if (jnode.isTextual() && !(StringUtils.hasLength(jnode.asText()))) {
            
            return null;
            
        }
        
        final String facilityAttribute = jnode.toPrettyString();
        
        return objectMapper.readValue(facilityAttribute, new TypeReference<Map<String, String>>() {});
        
    }
    
}
