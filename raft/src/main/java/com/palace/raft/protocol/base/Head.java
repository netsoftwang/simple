package com.palace.raft.protocol.base;

import java.util.Map;

public class Head {
	int len ;
	String version="1.0";
	Map<String,Object> optionMap;
}
