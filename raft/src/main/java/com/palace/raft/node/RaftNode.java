package com.palace.raft.node;

public class RaftNode {

	long nodeId ;
	String nodeName;
	int term;
	long lastApplyIndex;
	long lastCommitedIndex;
	
	RaftLog rlog = new RaftLog();
	
	
}
