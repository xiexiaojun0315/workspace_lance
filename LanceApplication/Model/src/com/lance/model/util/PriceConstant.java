package com.lance.model.util;

import java.math.BigDecimal;

public class PriceConstant {

    /**
     *  每月可申请40个工作
        在搜索结果中进行标准排序
        可添加8个技能（不显示）
     */
    public static final BigDecimal LANCER_PRICE_VIP = new BigDecimal("10");

    /**
     *  每月可申请80个工作
        在搜索结果中排名优先
        可添加5名团队成员
        可添加20个技能
     */
    public static final BigDecimal CONTRACTOR_PRICE_VIP = new BigDecimal("20");
    
    
    public static final BigDecimal CLIENT_SERVICE_PRICE = new BigDecimal("0.0875");

}
