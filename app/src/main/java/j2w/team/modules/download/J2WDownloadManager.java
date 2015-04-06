package j2w.team.modules.download;

import android.net.Uri;

import j2w.team.mvp.presenter.J2WHelper;

/**
 * @创建人 sky
 * @创建时间 15/4/3 下午12:07
 * @类描述 下载管理器
 */
public class J2WDownloadManager implements J2WIDownloadMagnager {

	private static J2WDownloadManager	instance	= new J2WDownloadManager();

	private J2WDownloadRequest			j2WDownloadRequest;

	/**
	 * 初始化
	 * 
	 * @return
	 */
	public static J2WDownloadManager getInstance() {
		return instance;
	}

	private J2WDownloadRequestQueue	mRequestQueue;

	public J2WDownloadManager() {
		mRequestQueue = new J2WDownloadRequestQueue();
		mRequestQueue.start();
	}

	public J2WDownloadManager(int threadPoolSize) {
		mRequestQueue = new J2WDownloadRequestQueue(threadPoolSize);
		mRequestQueue.start();
	}

	/**
	 * 添加指令
	 * 
	 * @param request
	 *            请求指令
	 * @return
	 */
	@Override public int add(J2WBaseRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("请求不能为空");
		}

		return mRequestQueue.add(request);
	}

	/**
	 * 取消指令
	 * 
	 * @param downloadId
	 *            请求ID
	 * @return
	 */
	@Override public int cancel(int downloadId) {
		return mRequestQueue.cancel(downloadId);
	}

	/**
	 * 取消所有指令
	 */
	@Override public void cancelAll() {
		mRequestQueue.cancelAll();
	}

	/**
	 * 查询下载状态
	 * 
	 * @param downloadId
	 *            请求ID
	 * @return
	 */
	@Override public int query(int downloadId) {
		return mRequestQueue.query(downloadId);
	}

	/**
	 * 取消所有待决运行的请求，并释放所有的工作线程
	 */
	@Override public void release() {
		if (mRequestQueue != null) {
			mRequestQueue.release();
			mRequestQueue = null;
		}
	}

	@Override public int download(String url, String fileName, J2WDownloadListener j2WDownloadListener) {

		StringBuilder sFileName = new StringBuilder(fileName);

		if (fileName.startsWith("/")) {
			// 删除斜线 - 防止双斜线
			sFileName.deleteCharAt(0);
		}

		Uri uri = Uri.parse(url);
		StringBuilder stringBuilder = new StringBuilder(J2WHelper.getInstance().getExternalCacheDir().toString());
		stringBuilder.append("/");
		stringBuilder.append(sFileName.toString());
		Uri destinationUri = Uri.parse(stringBuilder.toString());

		j2WDownloadRequest = new J2WDownloadRequest(uri);
		j2WDownloadRequest.setDestinationUrl(destinationUri);
		j2WDownloadRequest.setJ2WDownloadListener(j2WDownloadListener);
		return add(j2WDownloadRequest);
	}

	@Override public int download(String url, String destination, String fileName, J2WDownloadListener j2WDownloadListener) {
		StringBuilder sDestination = new StringBuilder(destination);
		StringBuilder sFileName = new StringBuilder(fileName);

		if (destination.endsWith("/")) {
			// 删除斜线 - 防止双斜线
			sDestination.deleteCharAt(destination.length() - 1);
		}

		if (fileName.startsWith("/")) {
			// 删除斜线 - 防止双斜线
			sFileName.deleteCharAt(0);
		}

		Uri uri = Uri.parse(url);

		StringBuilder stringBuilder = new StringBuilder(sDestination.toString());
		stringBuilder.append("/");
		stringBuilder.append(sFileName.toString());
		Uri destinationUri = Uri.parse(stringBuilder.toString());
		j2WDownloadRequest = new J2WDownloadRequest(uri);
		j2WDownloadRequest.setDestinationUrl(destinationUri);
		j2WDownloadRequest.setJ2WDownloadListener(j2WDownloadListener);
		return add(j2WDownloadRequest);
	}

	@Override public int download(Uri downloadUri, Uri destinationUri, J2WDownloadListener j2WDownloadListener) {
		j2WDownloadRequest = new J2WDownloadRequest(downloadUri);
		j2WDownloadRequest.setDestinationUrl(destinationUri);
		j2WDownloadRequest.setJ2WDownloadListener(j2WDownloadListener);
		return add(j2WDownloadRequest);
	}

}
