package com.xiaoxie.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你的问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论回复"),
    NO_LOGIN(2003,"未登录，请先登录"),
    SYS_ERROR(2004,"服务器被你玩坏了"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"你回复评论不存在"),
    ;

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;

    }
}
