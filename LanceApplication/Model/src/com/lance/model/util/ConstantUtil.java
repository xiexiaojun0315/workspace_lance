package com.lance.model.util;


public class ConstantUtil {
    public ConstantUtil() {
        super();
    }
    //用于代码调试，跳过一些验证等，当发布到环境时会设为false
    public static final boolean DEBUG_MODE=true;
    //出于性能和稳定性考虑，某些场景下，是否使用VO缓存的数据
    public static final boolean USE_CACHE=false;
    //出于性能和稳定性考虑，某些场景下，是否使用VO缓存的数据
    public static final boolean EXECUTE_VO=true;
    //当前应用的根URL
    public static final String ROOT_URL="lance";
    //当前应用的REST根URL（桶URL）
    public static final String ROOT_REST_URL="lance/res";
    
    public static final String SIGN_BY_COMPANY = "company";

    public static final String SIGN_BY_SELF = "self";

    public static final String POST_FORM_HOURLY = "hourly";

    public static final String POST_FORM_FIXED = "fixed";
    
    public static final String POST_JOBS_STATUS_DRAFT="draft";
    public static final String POST_JOBS_STATUS_POSTED="posted";
    public static final String POST_JOBS_STATUS_DELETE="delete";
    
    //------------合同状态-------------
    //甲方创建合同
    public static final String CONTRACT_STATUS_DRAFT="draft";
    
    //甲方发给乙方确认合同
    public static final String CONTRACT_OPT_SEND_AUDIT="sendAudit";

    //甲方提交后等待乙方确认
    public static final String CONTRACT_STATUS_TO_AUDIT="toAudit";
    
    //乙方确认合同
    public static final String CONTRACT_OPT_AUDIT="confirm";
    
    //乙方已确认，合同执行开始时间尚未到
    public static final String CONTRACT_STATUS_WAITING="waiting";
    
    //乙方已确认，合同执行开始时间到达，项目进行中
    public static final String CONTRACT_STATUS_ON_PROCESS="onProcess";
    
    //乙方拒绝合同
    public static final String CONTRACT_OPT_REJECT="reject";
    
    //甲方发布合同后被乙方拒绝
    public static final String CONTRACT_STATUS_REJECTED="rejected";
    
    //甲方申请取消合同
    public static final String CONTRACT_OPT_CLIENT_CANCEL="clientCancel";
    
    //甲方申请取消合同
    public static final String CONTRACT_STATUS_CLIENT_CANCEL="clientCancel";
    
    //乙方申请取消合同
    public static final String CONTRACT_OPT_LANCER_CANCEL="lancerCancel";
    
    //乙方申请取消合同
    public static final String CONTRACT_STATUS_LANCER_CANCEL="lancerCancel";
    
    //甲方/乙方同意撤销合同
    public static final String CONTRACT_OPT_AGREE_CANCEL="agreeCancel";
    
    //合同被撤销
    public static final String CONTRACT_STATUS_CANCEL="cancel";
    
    //甲方/乙方不同意撤销合同
    public static final String CONTRACT_OPT_DISAGREE_CANCEL="disagreeCancel";
    
    //甲方申请，合同执行完毕
    public static final String CONTRACT_OPT_CLIENT_DONE="clientDone";
    
    //甲方申请，合同执行完毕
    public static final String CONTRACT_STATUS_CLIENT_DONE="clientDone";
    
    //乙方申请，合同执行完毕
    public static final String CONTRACT_OPT_LANCER_DONE="lancerDone";
    
    //乙方申请，合同执行完毕
    public static final String CONTRACT_STATUS_LANCER_DONE="lancerDone";
    
    //合同执行完毕，未评价
    public static final String CONTRACT_STATUS_DONE_UN_EVAL="doneUnEval";
    
    //合同执行完毕，甲方评价
    public static final String CONTRACT_OPT_CLIENT_EVAL="clientEval";
    
    //合同执行完毕，甲方已评价，乙方未评价
    public static final String CONTRACT_STATUS_DONE_CLIENT_EVAL="doneClientEval";
    
    //合同执行完毕，乙方评价
    public static final String CONTRACT_OPT_LANCER_EVAL="lancerEval";
    
    //合同执行完毕，乙方已评价，甲方未评价
    public static final String CONTRACT_STATUS_DONE_LANCER_EVAL="doneLancerEval";
    
    //合同执行完毕,评价完毕
    public static final String CONTRACT_STATUS_DONE="Done";
    
    //------------里程碑状态-------------
    public static final String MILESTONE_PROCESS_DRAFT="draft";//新纪录默认状态
//    public static final String MILESTONE_PROCESS_TODO="toAudit";//甲方提交，等待乙方确认
//    public static final String MILESTONE_PROCESS_TODO="toAudit";//乙方确认，等待开始            

    //------------工作日志状态-------------
    public static final String REPORT_STATUS_WITHDRAW="withdraw";//已撤回
    public static final String REPORT_STATUS_POSTED="posted";//等待确认
    public static final String REPORT_STATUS_CONFIRM="confirm";//确认待付款
    public static final String REPORT_STATUS_PAYED="payed";//已支付
    public static final String REPORT_STATUS_REJECT="reject";//已拒绝
}
