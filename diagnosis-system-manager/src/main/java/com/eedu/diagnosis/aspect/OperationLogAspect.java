/*
package com.eedu.diagnosis.aspect;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.service.AuthUserManagerService;
import com.eeduspace.b2b.report.model.systemlog.OperationLogDto;
import com.eeduspace.b2b.report.service.SystemManagerOperationLogOpenService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

*/
/**
 * 系统操作日志
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-09 14:59
 **//*

@Aspect
@Service
@Slf4j
public class OperationLogAspect {
    //注入Service用于把日志保存数据库
    @Autowired
    private AuthUserManagerService authUserManagerService;
    @Autowired
    private SystemManagerOperationLogOpenService systemManagerOperationLogOpenService;
    //本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

    //Service层切点
    @Pointcut("execution(* com.eedu..*.*Impl.*(..))||execution(* com.eeduspace..*.*Impl.*(..))")
    public void serviceAspect() {
    }
    //控制层切点
    @Pointcut("execution(* com.eedu.diagnosis.manager.controller..*.*(..))")
    public void controllerAspect() {
    }


    */
/**
     * 后置通知 用于拦截记录用户的操作
     *
     * @param joinPoint 切点
     *//*

    @AfterReturning("controllerAspect()")
    public void doAfterReturning(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取header中 uersId 获取用户信息 管理端的id
        Integer userId = request.getIntHeader("userId");
        AuthUserManagerBean userManagerBean = authUserManagerService.getUserByUserId(userId);
        //请求的IP
        String ip = request.getRemoteAddr();
        try {

            //获取用户请求方法的参数并序列化为JSON格式字符串
            String params = "";
            if(request.getMethod().equals(RequestMethod.GET.toString())){
                params= JSONObject.toJSONString(request.getParameterMap())+";"+JSONObject.toJSONString(request.getRequestURI());
            }else{
                if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                    for (int i = 0; i < joinPoint.getArgs().length; i++) {
                        params += JSONObject.toJSONString(joinPoint.getArgs()[i]) + ";";
                    }
                }
            }

            // 获取response
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            Integer status = response.getStatus();
            /*/
/*========数据库日志=========*//*
/
            OperationLogDto operationLogDto = new OperationLogDto();
            operationLogDto.setAppName("diagnosis-systm-manager");
            operationLogDto.setUser("请求人:" + (userManagerBean==null?"userId无效"+userId:userManagerBean.getUserName()));
            operationLogDto.setLogType(0);
            operationLogDto.setMethodName((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            operationLogDto.setRequestParams(params);
            operationLogDto.setMethodDescription(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            operationLogDto.setRequestIp(ip);
            operationLogDto.setRequestUri(request.getRequestURI());
            operationLogDto.setRequestPath(request.getPathInfo());
            operationLogDto.setStatus(status+"");
            operationLogDto.setOperationType(0);
            operationLogDto.setCreateTime(new Date());
            //保存数据库
            Thread thread=new Thread(() -> {
                try {
                    systemManagerOperationLogOpenService.svaeOperationLog(operationLogDto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();

        } catch (Exception e) {
            //记录本地异常日志
            logger.error("==后置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    */
/**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     *//*

    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取header中 uersId 获取用户信息 管理端的id
        Integer userId = request.getIntHeader("userId");
        AuthUserManagerBean userManagerBean = authUserManagerService.getUserByUserId(userId);
        //获取请求ip
        String ip = request.getRemoteAddr();
        //获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                params += JSONObject.toJSONString(joinPoint.getArgs()[i]) + ";";
            }
        }
        try {
            // 获取response
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            Integer status = response.getStatus();

               */
/*==========数据库日志=========*//*

            OperationLogDto operationLogDto = new OperationLogDto();
            operationLogDto.setAppName("diagnosis-systm-manager");
            operationLogDto.setUser("请求人:" + (userManagerBean==null?"userId无效"+userId:userManagerBean.getUserName()));
            operationLogDto.setLogType(1);
            operationLogDto.setMethodName((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            operationLogDto.setRequestParams(params);
            operationLogDto.setMethodDescription(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            operationLogDto.setRequestIp(ip);
            operationLogDto.setRequestUri(request.getRequestURI());
            operationLogDto.setRequestPath(request.getPathInfo());
            operationLogDto.setExceptionCode(e.getClass().getName());
            operationLogDto.setExceptionDetail(e.getMessage());
            operationLogDto.setStatus(status+"");
            operationLogDto.setOperationType(1);
            operationLogDto.setCreateTime(new Date());
            //保存数据库
            Thread thread=new Thread(() -> {
                try {
                    systemManagerOperationLogOpenService.svaeOperationLog(operationLogDto);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            thread.start();
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());
        }
         */
/*==========记录本地异常日志==========*//*

        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);

    }






}
*/
