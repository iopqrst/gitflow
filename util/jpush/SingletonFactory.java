package com.bskcare.ch.util.jpush;

import java.util.LinkedList;
import java.util.Queue;

public class SingletonFactory {
	private static Queue<Objective> queue;
	private static RunQueue runQueue;
	private final static Object syncLock = new Object();

	private SingletonFactory() {
	}

	public static Queue<Objective> getQueue() {
		if (queue == null) {
			synchronized (syncLock) {
				if (queue == null) {
					queue = new LinkedList<Objective>();
				}
			}

		}
		return queue;
	}

	static RunQueue getRunQueue() {
		if (runQueue == null) {
			synchronized (syncLock) {
				if (runQueue == null) {
					runQueue = new RunQueue(getQueue());
				}
			}

		}
		return runQueue;
	}
}
