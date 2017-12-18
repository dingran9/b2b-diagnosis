package com.eedu.diagnosis.manager.model.request.ClassTest.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	 

	static {
		SocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
		// 添加编码过滤器 处理乱码、编码问题
		filterChain.addLast("codec", new ProtocolCodecFilter(new MessageCodecFactory()));
		// 设置核心消息业务处理器
		acceptor.setHandler(new ServerMessageHandler());
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10 * 30);

		KeepAliveFilter heartBeat = new KeepAliveFilter(new KeepAliveMessageFactory() {

			@Override
			public boolean isResponse(IoSession session, Object message) {
				return false;
			}

			@Override
			public boolean isRequest(IoSession session, Object message) {
				return false;
			}

			@Override
			public Object getResponse(IoSession session, Object request) {
				return null;
			}

			@Override
			public Object getRequest(IoSession session) {
				return null;
			}
		}, IdleStatus.BOTH_IDLE);

		heartBeat.setForwardEvent(true);

		heartBeat.setRequestInterval(15);
		acceptor.getFilterChain().addLast("heartbeat", heartBeat);

		try {
			// 绑定端口3456
			acceptor.bind(new InetSocketAddress(3456));
			System.out.println("----Mina服务器已经启动！！！");
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

}