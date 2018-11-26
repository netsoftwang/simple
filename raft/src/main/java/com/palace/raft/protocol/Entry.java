package com.palace.raft.protocol;

import com.palace.raft.protocol.base.Envelope;

public class Entry extends Envelope {
	
	public int term;
	public int type;
	public int committedIndex;
	public int lastApplyIndex;
}
