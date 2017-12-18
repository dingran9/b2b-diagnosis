package com.eedu.diagnosis.manager.model.request.ClassTest.mina;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.IoSession;

public class IoSessionManager {
	private static Map<String, IoSession> ioSessions = new HashMap<String, IoSession> ();

	
	public static void putIoSession(String key , IoSession ioSession){
		ioSessions.put(key, ioSession);
	}
	
	public static IoSession getIoSession (String key){
		return ioSessions.get(key.trim());
	}
	
	public static boolean isHave(String key){
		IoSession is = ioSessions.get(key.trim());
		return (is != null);
	}
	public static void remove(IoSession ioSession){
		for (Map.Entry<String, IoSession> en : ioSessions.entrySet()) {
			IoSession is = en.getValue();
			if(is == ioSession || is.getId() == ioSession.getId()){
				ioSessions.remove(en.getKey());
			}
		}
	}
}
