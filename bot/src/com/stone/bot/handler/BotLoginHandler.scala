package com.stone.bot.handler

import com.stone.core.msg.IMessage
import com.stone.bot.CrazyBot
import com.stone.proto.MessageTypes.MessageType
import com.stone.game.msg.ProtobufMessage
import com.stone.proto.Auths.LoginResult
import com.stone.proto.Auths.RoleList
import com.stone.proto.Auths.CreateRole

object BotLoginHandler {

  /**
   *  Handle login result;
   */
  Handlers.registHandler(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, (msg: IMessage, bot: CrazyBot) => {
    val protobufMessage = msg.asInstanceOf[ProtobufMessage]
    val login = protobufMessage.parseBuilder(LoginResult.newBuilder());
    if (login.getSucceed) {
      bot.sendMessage(MessageType.CG_GET_ROLE_LIST_VALUE)
    }
  })
  
  /**
   * Handle role list;
   */
  Handlers.registHandler(MessageType.GC_GET_ROLE_LIST_VALUE, (msg: IMessage, bot: CrazyBot) => {
    val protobufMessage = msg.asInstanceOf[ProtobufMessage]
    val roleList = protobufMessage.parseBuilder(RoleList.newBuilder());
    if (roleList.getRoleListCount > 0) {
      val role = roleList.getRoleList(0)
      println(String.format("Get role, name: %s", role.getName))
    } else {
      bot.sendMessage(MessageType.CG_CREATE_ROLE_VALUE, CreateRole.newBuilder().setTemplateId(1).setName("crazyjohn"))
    }
  })
}