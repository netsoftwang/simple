package com.palace.raft.node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import com.palace.raft.mode.Member;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerMain {

	int nProcesser = Runtime.getRuntime().availableProcessors();
	public static void main(String[] args) {
		String members = args[0];
	}
	
	public void init(String members) {
		List<Member> memberList = new ArrayList<Member>();
		for(String mem : members.split(",")) {
			memberList.add(new Member(mem));
		}
		
		initLog();
		startNodeServer();
	}
	
	ServerBootstrap serverBootstrap;
	NioEventLoopGroup boosGroup;
	NioEventLoopGroup workGroup;
	private void startNodeServer() {
		
		this.serverBootstrap = new ServerBootstrap();
		this.serverBootstrap.childOption(ChannelOption.SO_TIMEOUT, 1000);
		this.serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		this.serverBootstrap.childOption(ChannelOption.TCP_NODELAY,true);
		this.serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		
		this.boosGroup = new NioEventLoopGroup(nProcesser,new ThreadFactory() {
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("raftServerConnLsen-");
				thread.setPriority(Thread.NORM_PRIORITY);
				thread.setDaemon(true);
				return thread;
			}
		});
		this.boosGroup.setIoRatio(100);
		this.boosGroup.register(new NioServerSocketChannel());

		
		this.workGroup = new NioEventLoopGroup(nProcesser,new ThreadFactory() {
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("raftServerWork-");
				thread.setPriority(Thread.NORM_PRIORITY);
				thread.setDaemon(true);
				return thread;
			}
		});
		
		this.serverBootstrap.group(this.boosGroup, this.workGroup)
			.childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast("idleStateAwareHandler",new IdleStateHandler(120,60,10));
				}
			});
		
		
	}

	public void initLog() {
		
	}
}







