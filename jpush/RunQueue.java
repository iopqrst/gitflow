package com.bskcare.ch.util.jpush;

import java.util.Queue;

public class RunQueue implements Runnable {
	private Queue<Objective> queue;

	public RunQueue(Queue<Objective> queue) {
		this.queue = queue;
	}

	public void run() {
		Objective objective;
		if (!queue.isEmpty()) {
			//peek：返回队列头部的元素 如果队列为空，则返回null
			while ((objective = queue.peek()) != null) {
				// try {
				// System.out.println("暂停3秒开始");
				// Thread.sleep(3000);
				// System.out.println("暂停3秒结束");
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				objective.sendJpush();
				//poll：移除并返问队列头部的元素 如果队列为空，则返回null
				queue.poll();
				System.out.println("队列还剩" + queue.size());
			}
		}
	}

}
