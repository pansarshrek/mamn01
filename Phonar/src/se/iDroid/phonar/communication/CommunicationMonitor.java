package se.iDroid.phonar.communication;

import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Queue;

import android.util.Log;


import se.iDroid.phonar.communicationtasks.*;
import se.iDroid.phonar.model.Model;

public class CommunicationMonitor {
	
	private Model model;
	private DatagramSocket socket;
	
	private Queue<SendTask> tasks;

	public CommunicationMonitor(DatagramSocket socket, Model model) {
		Log.d("udp port", "Constructor: Sending from " + socket.getLocalPort());
		tasks = new LinkedList<SendTask>();
		this.model = model;
		this.socket = socket;
	}
	
	public synchronized void sendPendingTasks() {
		while (tasks.isEmpty()) {
			Log.d("udp port", "Sending from " + socket.getLocalPort());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (!tasks.isEmpty()) {
			SendTask task = tasks.poll();
			task.execute(socket);
		}
	}
	
	public synchronized void sendHelloWorld() {
		tasks.add(new HelloWorldTask());
		notifyAll();
	}

	public synchronized void sendCoords() {
		tasks.add(new SendCoordsTask(model));
		notifyAll();
	}
	
	public synchronized void updateCoords() {
		tasks.add(new GetCoordsTask(model));
		notifyAll();
	}
	
	public synchronized void sendCreateGroupCommand() {
		notifyAll();
	}
	
	public synchronized void sendLeaveGroupCommand() {
		notifyAll();
	}
	
	public synchronized void createUser() {
		tasks.add(new CreateUserTask(model));
	}
	
}
