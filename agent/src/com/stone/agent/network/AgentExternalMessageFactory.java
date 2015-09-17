package com.stone.agent.network;

import com.stone.agent.msg.external.CAMessage;
import com.stone.core.codec.IMessageFactory;
import com.stone.core.msg.IMessage;

public class AgentExternalMessageFactory implements IMessageFactory {

	@Override
	public IMessage createMessage(int type) {
		switch (type) {
		default:
			return new CAMessage(type);
		}
	}

}
