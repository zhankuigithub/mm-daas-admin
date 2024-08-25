package com.mm.system.domain.enums;


public class PatternEnum {

    /** 手机号正则 */
    public static final String PHONE_REG = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";

    /** 中文正则 */
    public static final String CHINESE_REG = "^[\u4e00-\u9fa5]+$";

    /** 英文数字组合 */
    public static final String EN_NUMBER_REG = "^[a-zA-Z0-9]+$";

}
