package com.stone.db.actor;

import java.util.List;

import akka.actor.UntypedActor;

import com.stone.core.data.uuid.IUUIDService;
import com.stone.core.data.uuid.UUIDType;
import com.stone.core.db.service.orm.IEntityDBService;
import com.stone.db.entity.PlayerEntity;
import com.stone.db.msg.internal.player.InternalLoginResult;
import com.stone.db.query.DBQueryConstants;
import com.stone.proto.Auths.Login;

/**
 * The db login actor;
 * 
 * @author crazyjohn
 *
 */
public class DBLoginActor extends UntypedActor {
	private final IEntityDBService dbService;
	private final IUUIDService uuidActor;

	public DBLoginActor(IEntityDBService dbService, IUUIDService uuidActor) {
		this.dbService = dbService;
		this.uuidActor = uuidActor;
	}

	
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Login.Builder) {
			final Login.Builder login = (Login.Builder) msg;
			final List<PlayerEntity> entities = dbService.queryByNameAndParams(DBQueryConstants.QUERY_PLAYER_BY_PUID, new String[] { "puid" },
					new Object[] { login.getPuid() });
			// if not exits this account, just create it
			if (entities == null || entities.size() == 0) {

				// Future<?> future = Patterns.ask(uuidActor, UUIDType.PLAYER,
				// 100000);
				// final ActorRef sender = getSender();
				// future.onComplete(new OnSuccess() {
				// @Override
				// public void onSuccess(Object result) throws Throwable {
				// final PlayerEntity playerEntity = new PlayerEntity();
				// playerEntity.setPuid(login.getPuid());
				// playerEntity.setId(((Success<Long>) result).value());
				// dbService.insert(playerEntity);
				// entities.add(playerEntity);
				// sender.tell(new InternalLoginResult(entities), getSelf());
				// }
				//
				// }, this.getContext().dispatcher());
				final PlayerEntity playerEntity = new PlayerEntity();
				playerEntity.setPuid(login.getPuid());
				playerEntity.setId(this.uuidActor.getNextId(UUIDType.PLAYER));
				dbService.insert(playerEntity);
				entities.add(playerEntity);
				getSender().tell(new InternalLoginResult(entities), getSelf());

			} else {
				getSender().tell(new InternalLoginResult(entities), getSelf());
			}

		}
	}

}
