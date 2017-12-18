package com.eedu.diagnosis.manager.model.request.ClassTest.mina;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
 
 
 
 
public class ServerMessageHandler implements IoHandler {
    
    private final static Logger log = Logger.getLogger("ServerMessageHandler.class");
    
   
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.info("服务器发生异常： {}"+ cause.getMessage());
    }
 
   
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("服务器接收到数据： {}"+ message);
        if(message.toString().contains("BASE_ID")){
        	String baseId = message.toString().split("=")[1];
            baseId = baseId.trim();
        	if(IoSessionManager.isHave(baseId) == false){
        		IoSessionManager.putIoSession( baseId, session);
        	}
        	
        }
        
    }
 
   
    public void messageSent(IoSession session, Object message) throws Exception {
        log.info("服务器发送消息： {}"+ message);
    }
 
    
    public void sessionClosed(IoSession session) throws Exception {
        log.info("关闭当前session：{}#{}"+ session.getId()+ session.getRemoteAddress());
        IoSessionManager.remove(session);
        CloseFuture closeFuture = session.close(true);
        closeFuture.addListener(new IoFutureListener<IoFuture>() {
            public void operationComplete(IoFuture future) {
                if (future instanceof CloseFuture) {
                    ((CloseFuture) future).setClosed();
                    log.info("sessionClosed CloseFuture setClosed-->{},"+ future.getSession().getId());
                }
            }
        });
    }
 
    
    public void sessionCreated(IoSession session) throws Exception {
        log.info("创建一个新连接： "+ session.getRemoteAddress() + " : " + session.getId());
        //session.write("welcome to the chat room !");
    }
 
   
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.info("当前连接{}处于空闲状态：{}"+ session.getRemoteAddress()+ status);
    }
 
 
    public void sessionOpened(IoSession session) throws Exception {
        log.info("打开一个session：{}#{}"+ session.getId()+ session.getBothIdleCount());
    }
}
 