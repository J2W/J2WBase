package j2w.team.modules.threadpool;

import java.util.concurrent.ExecutorService;

import j2w.team.common.log.L;

/**
 * Created by sky on 15/2/20.调度
 */
public final class J2WThreadPoolManager {
	/**
	 * J2WThreadPoolManager 单例模式
	 */
	private static final J2WThreadPoolManager instance = new J2WThreadPoolManager();

	public static J2WThreadPoolManager getInstance() {
		return instance;
	}

	/** 线程服务-并行线程池 **/
	private J2WExecutorService j2WExecutorService;

	/** 线程服务-串行线程池 **/
	private J2WSingleExecutorServiece j2WSingleExecutorServiece;

	public synchronized ExecutorService getExecutorService() {
		if (j2WExecutorService == null) {
			j2WExecutorService = new J2WExecutorService();
		}
		return j2WExecutorService;
	}

	public synchronized ExecutorService getSingleExecutorService() {
		if (j2WSingleExecutorServiece == null) {
			j2WSingleExecutorServiece = new J2WSingleExecutorServiece();
		}
		return j2WSingleExecutorServiece;
	}

	public synchronized void finish() {
        if (j2WExecutorService != null) {
			j2WExecutorService.shutdown();
			j2WExecutorService = null;
		}
		if (j2WSingleExecutorServiece != null) {
			j2WSingleExecutorServiece.shutdown();
			j2WSingleExecutorServiece = null;
		}
	}
}
