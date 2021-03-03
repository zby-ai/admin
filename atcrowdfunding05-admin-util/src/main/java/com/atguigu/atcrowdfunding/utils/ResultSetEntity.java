package com.atguigu.atcrowdfunding.utils;

import javax.xml.crypto.Data;
import java.io.Serializable;

/**
 * @author zbystart
 * @create 2021-02-06 16:12
 */
public class ResultSetEntity<T> implements Serializable {
    private static final long serialVersionUID = -6849794345754667710L;

    private static final String SUCCEED = "SUCCEED";
    private static final String FAILURE = "FAILURE";
    private String code;
    private String mssage;
    private T data;

    public ResultSetEntity() {
    }

    public ResultSetEntity(String code, String mssage, T data) {
        this.code = code;
        this.mssage = mssage;
        this.data = data;
    }

    public static <E> ResultSetEntity<E> succeedNoData(){
        return new ResultSetEntity<E>(SUCCEED,null,null);
    }
    public static <E> ResultSetEntity<E> failureNoData(){
        return new ResultSetEntity<E>(FAILURE,null,null);
    }

    public static <E> ResultSetEntity<E> succeedYesData(String mssage,E data){
        return new ResultSetEntity<E>(SUCCEED,mssage,data);
    }
    public static <E> ResultSetEntity<E> failureYesData(String mssage){
        return new ResultSetEntity<E>(FAILURE,mssage,null);
    }

    @Override
    public String toString() {
        return "ResultSetEntity{" +
                "code=" + code +
                ", mssage='" + mssage + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMssage() {
        return mssage;
    }

    public void setMssage(String mssage) {
        this.mssage = mssage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
