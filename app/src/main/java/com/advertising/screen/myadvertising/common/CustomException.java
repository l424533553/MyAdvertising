package com.advertising.screen.myadvertising.common;

public class CustomException extends Exception {
    /*无参构造函数*/
    public CustomException(){
        super();
    }
    
    //用详细信息指定一个异常
    public CustomException(String message){
        super(message);
    }
    
    //用指定的详细信息和原因构造一个新的异常
    public CustomException(String message, Throwable cause){
        super(message,cause);
    }
    
    //用指定原因构造一个新的异常
    public CustomException(Throwable cause) {
        super(cause);
    }
}