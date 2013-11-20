package com.bh.battle.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.bh.battle.Constants;
import com.bh.battle.Game;

public class GameConnection implements Runnable {

	public static enum GameConnectionType {
		SERVER("Server"), CLIENT("Client");
		
		public String strRep;
		GameConnectionType(String str) {
			strRep = str;
		}
	}
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private GameConnectionType type;
	
	private boolean running;
	private Thread thread;
	
	public void initiateAsServer() {
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(Constants.PORT);
			Socket cs = ss.accept();
			dis = new DataInputStream(cs.getInputStream());
			dos = new DataOutputStream(cs.getOutputStream());
			type = GameConnectionType.SERVER;
		} catch (IOException e) {
			Game.getLogger().severe(Constants.SERVER_PREFIX + " Fatal Server Socket Error");
			e.printStackTrace();
		}
	}
	
	public void initiateAsClient() {
		try {
			@SuppressWarnings("resource")
			Socket ss = new Socket(InetAddress.getByName(Constants.CLIENT_TARGET), Constants.PORT);
			dis = new DataInputStream(ss.getInputStream());
			dos = new DataOutputStream(ss.getOutputStream());
			type = GameConnectionType.SERVER;
		} catch (IOException e) {
			Game.getLogger().severe(Constants.CLIENT_PREFIX + " Fatal Client Socket Error");
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(running) {
			String recvStr = recv();
			System.out.println(recvStr);
		}
	}
	
	public void send(String str) {
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String recv() {
		String data = "";
		try {
			dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void start() {
		if(running)
			return;
		thread = new Thread(this, "GAME_" + type.strRep);
		thread.start();
	}
	
	public void stop() {
		
	}
	
	public GameConnectionType getType() {
		return type;
	}
}
