package com.i4joy.akka.kok.camel.worker;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.camel.CamelMessage;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.i4joy.akka.kok.LanguageProperties;
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_playerInfoGet;
import com.i4joy.util.Tools;

public class PlayerLoginWorker extends UntypedActor {

	protected final Log logger = LogFactory.getLog(getClass());

	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理

	public static Props getProps() {
		return Props.create(PlayerLoginWorker.class);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof CamelMessage) {
			JSONObject responseJSon = new JSONObject();
			try {
				CamelMessage camelMessage = (CamelMessage) msg;
				Map<String, Object> map = camelMessage.getHeaders();
				String jsonStr = map.get("msg").toString();
				JSONObject json = JSONObject.fromObject(jsonStr);
				String username = json.getString("username");
				String password = json.getString("password");
				String playerName = json.getString("playerName");
				String serverId = json.getString("serverId");
				if (LoginWorker.checkUserPassword(username, password, mediator)) {
					RC_playerInfoGet.Builder builder = RC_playerInfoGet.newBuilder();
					builder.setPlayerName(playerName);
					Timeout timeout = new Timeout(Duration.create(5, "seconds"));
					Future<Object> future = Patterns.ask(mediator, new Publish("player_info", builder.build()), timeout);
					RC_playerInfoGet playerInfo = (RC_playerInfoGet) Await.result(future, timeout.duration());

					if (playerInfo.getHadName() && playerInfo.getUserName().equals(username) && playerInfo.getPlayerName().equals(playerName) && Integer.parseInt(serverId) == playerInfo.getServerId()) {
						responseJSon.put("retCode", 0);
						responseJSon.put("IP", TextProperties.getText("IOIP"));
						responseJSon.put("PORT", "8000");
						responseJSon.put("retMsg", LanguageProperties.getText("WJDLCG"));
					} else {
						responseJSon.put("retCode", 1);
						responseJSon.put("retMsg", LanguageProperties.getText("WJCJSB"));
					}
					getSender().tell(responseJSon.toString(), getSelf());
				}
			} catch (Exception e) {
				Tools.printError(e, logger, null);
				responseJSon.put("retCode", 1);
				responseJSon.put("retMsg", LanguageProperties.getText("CSCW"));
				getSender().tell(responseJSon.toString(), getSelf());
			}

		}
	}

}