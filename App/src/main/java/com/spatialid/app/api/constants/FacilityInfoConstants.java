// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.constants;

/**
 * 埋設物情報取得APIの定数を定義したクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/24
 */
public class FacilityInfoConstants {
    
    /**
     * 埋設物情報取得APIのパス．
     */
    public static final String FACILITY_INFO_URI = "/facility-information";
    
    /**
     * 空間・属性情報参照のAPI名．
     */
    public static final String SID_ATTRIBUTE_NAME = "空間・属性情報参照";
    
    /**
     * 空間・属性情報参照APIのパス．
     */
    public static final String SID_ATTRIBUTE_URI = "/gen/api/generic/v1/select-facility";
    
    /**
     * ポリゴン面積取得のAPI名．
     */
    public static final String POLYGON_AREA_NAME = "ポリゴン面積取得";
    
    /**
     * ポリゴン面積取得APIのパス．
     */
    public static final String POLYGON_AREA_URI = "/gen/api/polygon/v1/get-area";
    
    /**
     * 座標→空間ID変換のAPI名．
     */
    public static final String SID_NAME = "座標→空間ID変換";
    
    /**
     * 座標→空間ID変換APIのパス．
     */
    public static final String SID_URI = "/gen/api/polygon/v1/get-spatialid";
    
    /**
     * データ整備範囲外を識別する正規表現．
     */
    public static final String REGEX_INVALID_DATA_RANGE = ".*InvalidDataRange.*";
    
    /**
     * 行ロックを識別する正規表現．
     */
    public static final String REGEX_DATA_LOCKED = ".*DataLocked.*";
    
    /**
     * 埋設物情報取得APIにおけるズームレベルの上限．
     */
    public static final int ZL_UPPER_LIMIT = 26;
    
    /**
     * 埋設物情報取得APIにおけるズームレベルの下限．
     */
    public static final int ZL_LOWER_LIMIT = 18;
    
    /**
     * 埋設物情報取得APIにおけるポリゴン面積の許容値．
     */
    public static final int POLYGON_AREA_LIMIT = 20000;
    
    /**
     * 空間IDの指定であることを示す処理タイプ．
     */
    public static final String SID_TYPE = "SID";
    
    /**
     * ポリゴン座標の指定であることを示す処理タイプ．
     */
    public static final String POLYGON_TYPE = "POLYGON";
    
}
