package com.bskcare.ch.util.jpush;

import java.util.Queue;

public class AddQueue {
	static Queue<Objective> queue = SingletonFactory.getQueue();

	public static String addqueue(Objective objective) {
		queue.offer(objective);
		if (queue.size() == 1) {
			RunQueue runQueue = SingletonFactory.getRunQueue();
			new Thread(runQueue).start();
		}
		// if(queue.size()>0)
		return "success";
		// return "exception";
	}
}
