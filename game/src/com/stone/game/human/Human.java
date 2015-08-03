package com.stone.game.human;

import akka.actor.ActorRef;

import com.stone.core.msg.ProtobufMessage;
import com.stone.db.entity.HumanEntity;
import com.stone.game.module.human.IHumanModule;
import com.stone.game.module.human.item.HumanItemModule;
import com.stone.game.module.player.Player;

/**
 * The human;
 * 
 * @author crazyjohn
 *
 */
public class Human {
	/** human guid */
	private long guid;
	private String name;
	/** player */
	private Player player;
	/** itemModule */
	private IHumanModule itemModule;
	
	public Human() {
		// init
		initModule();
	}

	private void initModule() {
		// init item module
		itemModule = new HumanItemModule(this);
	}

	public long getGuid() {
		return guid;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Handle the system internal message;
	 * 
	 * @param message
	 * @param playerActor
	 */
	public void onInternalMessage(Object message, ActorRef playerActor) {
	}

	/**
	 * Handle the net external message;
	 * 
	 * @param msg
	 * @param playerActor
	 * @param dbMaster
	 */
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Init from human entity;
	 * 
	 * @param humanEntity
	 */
	public void onLoad(HumanEntity humanEntity) {
		this.name = humanEntity.getName();
		this.guid = humanEntity.getGuid();
		// load module datas
		this.itemModule.onLoad(humanEntity);
	}
}
