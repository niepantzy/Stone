package com.stone.core.actor.msg;

import com.google.protobuf.Message.Builder;

public class ActorSendMessage {
	private final int type;

	private final Builder builder;

	public ActorSendMessage(int type, Builder builder) {
		this.type = type;
		this.builder = builder;
	}

	public int getType() {
		return type;
	}

	public Builder getBuilder() {
		return builder;
	}
}
