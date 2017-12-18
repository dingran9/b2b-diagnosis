package com.eedu.diagnosis.manager.responsecode;

/**
 * Author: dingran
 * Date: 2015/10/21
 * Description:返回状态码
 */
public enum ResponseCode {


    /* (code,message,httpCode)
    *
    * pay attention to :
    * the message must  end with '.'
    * */


    SUCCESS("Success", "200", "Success"),

    //service error , 500 begin
    SERVICE_ERROR("Service.Error", "500", "The request service process error."),
    //server end
    LOGIN_FAILD("login_faild","20002", "username or password is incorrect"),

    //client error, 400 begin
    UNKNOWN_OPERATION("Operation.UnKnown","40001","The request operation is unsupported."),
    PARAMETER_MISS("Parameter.Miss", "40002","The request parameter is miss."),
    PARAMETER_INVALID("Parameter.Invalid", "40003", "The request parameter is invalid." ),
    STATE_INCORRECT("State.Incorrect", "40004", "The request resource in an incorrect state for the request."),
    RESOURCE_NOTFOUND("Resource.NotFound", "40005", "The request resource is not exist."),
    RESOURCE_INUSE("Resource.InUse", "40006", "The request resource is in use."),
    RESOURCE_DUPLICATE("Resource.Duplicate", "40007", "The request resource is duplicate."),
    RESOURCE_LIMITEXCEEDED("Resource.LimitExceeded", "40008", "The request resource is limitExceeded."),
    RESOURCE_NOTBOND("Resource.NotBond", "40102", "The resource is not bound."),
    TOKEN_INVALID("Token.Invalid", "40009", "The token is invalid."),
    RESOURCE_IMPERFECT("Resource.Imperfect","40010", "Resource is imperfect."),//资源不完整
    FORBIDDEN_AUTHFAILURE("Forbidden.AuthFailure", "40301", "The request credentials is unauthorized，please check your credentials."),

    FORBIDDEN_NOPERMISSION("Forbidden.NoPermission", "40302", "The request resource has no permissions."),
    FORBIDDEN_SIGNATURE_DOESNOT_MATCH("Forbidden.SignatureDoesNotMatch", "40303", "The request signature does not match,please refer to the api reference."),

    FORBIDDEN_TOKENFAILURE("Forbidden.TokenFailure", "40304", "The request token is unauthorized，please check your token."),
    FORBIDDEN_TOKENTIMEOUT("Forbidden.TokenTimeout", "40305", "The request token is timeout."),

    FORBIDDEN_LOCKED("Forbidden.Locked", "40306", "The request account is locked."),
    REPEAT_LOGIN("Repeat.Login", "40101", "The user is logged in."),

    FORBIDDEN_NOSCAN("Forbidden.NoScan", "40307", "The request resource has no scan."),

    ALREADY_FRIEND("Already.Friends", "40308", "You are already friends."),
    INTEGRAL_INSUFFICIENT("Integral.Insufficient", "40309", "Deficiency of integral."),//积分不足
    STAMINA_INSUFFICIENT("Stamina.Insufficient", "40310", "Lack of physical strength."),//体力不足
    TASK_ALREADY_RECEIVE("Task.AlreadyReceive", "40311", "Task has been received."),//任务已领取
  
    USER_FIGHT("User.IsFighting", "40312", "User Fighting."),//用户战斗中
    USER_OFF_LINE("User.OffLine", "40313", "User offLine."),//用户已离线

    NOT_LOGIN("Not.Login", "20001", "The user is not login."),
    DOES_NOT_CONFORM_TO_VIP("Not.Vip", "00000", "Does not conform to vip."),
    THE_GOODS_OFF_THE_SHELF("the.goods.off.the.shelf","20005","the goods off the shelf"),//已经下架
    THE_GOODS_ALREADY_BUY("the.goods.already.buy","20006","the goods already buy"),//已经商品购买
    THE_GOODS_ALREADY_PLACE_AN_ORDER("the.goods.already.Place.an.order","20007","the goods already place an order"),//已经商品下单
    THE_GOODS_ALREADY_DATE_BY_ORDER("the.goods.already.date.by.order","20008","the goods already date by order"),//已经下单了该时间
    THE_GOODS_ALREADY_DATE_BY_BUY("the.goods.already.date.by.buy","20009","the goods already date by buy"),//已经购买了该时间
    COURSETEST_FINISHED("coursetest is finished","80001","课中测已经完成"),
    //client end
    THE_GOODS_ALREADY_OVERDUE("the.goods.already.overdue","20010","the goods already overdue"),//该商品已经过期
    NOTMARKFINISH(" mark not  finished","90001","mark not  finished"),//卷子未批完无法生成综合报,
    ACCOUNT_LOGIN_ELSEWHERE("Account login elsewhere","600002","account login elsewhere"), //login elsewhere
    TOKEN_EXPIRED("Token expired","600003","token expired"),
    SIGNATURE_IS_WRONG("Signature is wrong","600004","signature is wrong"),
    IINCOMPLETE_INFORMATION("Incomplete information","600005","incomplete information"),
    INVALID_ACCOUNT_NUMBER("Invalid account number","600006","invalid account number");

    //client end


    public final String code;
    public final String httpCode;
    public final String message;
    ResponseCode(String code, String httpCode, String message) {
        this.code = code;
        this.httpCode = httpCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return  code;
    }

    public static ResponseCode toEnum(String name) {
        for (ResponseCode res : ResponseCode.values()) {
            if (res.toString().equalsIgnoreCase(name)) {
                return res;
            }
        }
        return null;
    }
}
