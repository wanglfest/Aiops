package com.sinosoft.enums;
/**
 * 【枚举值通用案列】
 * @author sinfosoft
 * @date 2017年11月9日 下午2:23:14 
 * @version V1.0
 */
public enum ENUM_EXAMPLE_CODE {
	SUCCESS("0","成功"),
	FAILURE("1","失败"),;
	
	
	private final String code;
    private final String desc;

    ENUM_EXAMPLE_CODE(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    /**
     * 根据key获得desc
     * @param key
     * @return
     */
    public static String getEnumDescByKey(String key) {
        for (ENUM_EXAMPLE_CODE enumItem : ENUM_EXAMPLE_CODE.values()) {
            if (key.equals(enumItem.code())) {
                return enumItem.desc();
            }
        }
        return "";
    }
}
