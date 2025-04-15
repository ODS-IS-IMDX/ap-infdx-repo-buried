// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.facilityinfo;

/**
 * 埋設物情報取得におけるハンドラを定義したインターフェース．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/24
 */
/**
 * ハンドラクラスの機能を定義したインターフェース．
 * 
 * @param <T> ハンドラが受け取る引数の型
 * @param <R> ハンドラが返却する返り値の型
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
public interface IBaseHandler<T, R> {
    
    /**
     * ハンドラクラスの処理を提供する．
     * 
     * @param dto 処理対象となるDTO
     * @return 処理結果
     * @throws Exception
     */
    public R handle(T dto) throws Exception;
    
    /**
     * 次に実行するハンドラを設定する機能を提供する．
     * 
     * @param <V> 次に実行するハンドラが受け取る引数の型
     * @param nextHandler 次に実行するハンドラ
     * @return 次に実行するハンドラクラス
     */
    public <V> IBaseHandler<V, R> setNext(IBaseHandler<V, R> nextHandler) throws Exception;
    
}
