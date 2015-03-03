package com.stone.game.player.login;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.db.msg.internal.InternalCreateRole;
import com.stone.game.handler.IMessageHandlerWithType;
import com.stone.game.msg.ProtobufMessage;
import com.stone.game.player.Player;
import com.stone.proto.Auths.CreateRole;
import com.stone.proto.MessageTypes.MessageType;

public class PlayerCreateRoleHandler implements IMessageHandlerWithType<ProtobufMessage> {
	private final ActorRef dbMaster;

	public PlayerCreateRoleHandler(ActorRef dbMaster) {
		this.dbMaster = dbMaster;
	}
	@Override
	public void execute(ProtobufMessage msg, Player player) throws MessageParseException {
		CreateRole.Builder createRole = msg.parseBuilder(CreateRole.newBuilder());
		dbMaster.tell(new InternalCreateRole(player.getPlayerId(), createRole), msg.getPlayerActor());
	}

	@Override
	public short getMessageType() {
		return MessageType.CG_CREATE_ROLE_VALUE;
	}

}
