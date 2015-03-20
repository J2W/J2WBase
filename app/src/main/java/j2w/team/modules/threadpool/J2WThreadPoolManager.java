package j2w.team.modules.threadpool;

import java.util.concurrent.ExecutorService;

/**
 * Created by sky on 15/2/20.调度
 */
public final class J2WThreadPoolManager {

	/**
	 * J2WThreadPoolManager 单例模式
	 */
	private static final J2WThreadPoolManager	instance	= new J2WThreadPoolManager();

	public static J2WThreadPoolManager getInstance() {
		return instance;
	}

	/** 线程服务-网络线程池 **/
	private J2WHttpExecutorService			j2WHttpExecutorService;

	/** 线程服务-并行工作线程池 **/
	private J2WWorkExecutorService			j2WWorkExecutorService;

	/** 线程服务-串行工作线程池 **/
	private J2WSingleWorkExecutorServiece	j2WSingleWorkExecutorServiece;

	public synchronized ExecutorService getHttpExecutorService() {
		if (j2WHttpExecutorService == null) {
			j2WHttpExecutorService = new J2WHttpExecutorService();
		}
		return j2WHttpExecutorService;
	}

	public synchronized ExecutorService getSingleWorkExecutorService() {
		if (j2WSingleWorkExecutorServiece == null) {
			j2WSingleWorkExecutorServiece = new J2WSingleWorkExecutorServiece();
		}
		return j2WSingleWorkExecutorServiece;
	}

	public synchronized ExecutorService getWorkExecutorService() {
		if (j2WWorkExecutorService == null) {
			j2WWorkExecutorService = new J2WWorkExecutorService();
		}
		return j2WSingleWorkExecutorServiece;
	}

	public synchronized void finish() {
		if (j2WHttpExecutorService != null) {
			j2WHttpExecutorService.shutdown();
			j2WHttpExecutorService = null;
		}
		if (j2WSingleWorkExecutorServiece != null) {
			j2WSingleWorkExecutorServiece.shutdown();
			j2WSingleWorkExecutorServiece = null;
		}
	}
}
