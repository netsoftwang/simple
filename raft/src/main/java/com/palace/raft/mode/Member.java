package com.palace.raft.mode;

import org.apache.commons.lang3.Validate;

public class Member {

	String strId;
	String strIp;
	int nPort;
	
	public Member(String strNode) {
		String[] arr = strNode.split(":");
		Validate.isTrue(arr.length == 3,"节点参数配置错误");
		this.strIp = arr[0];
		this.nPort = Integer.parseInt(arr[1]);
		this.strId = arr[2];
	}
 
	
	
}
