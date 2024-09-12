package com.nmdsky.threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 创建4个固定线程池，提交6个任务
 * @author liaoxuefeng
 */
public class PoolsThread {
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 6; i++) {
			es.submit(new Task("" + i));
		}
		es.shutdown();
	}
}

