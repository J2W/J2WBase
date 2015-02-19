package com.squareup.picasso;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import j2w.team.common.utils.AppUtils;
import j2w.team.mvp.presenter.J2WHelper;

/**
 * Created by sky on 15/2/19. Picasso工具
 */
public final class PicassoTools {

	static Picasso singleton = null;
	static OkHttpDownloader okHttpDownloader = null;

	/**
	 * 初始化
	 * 
	 * @return
	 */
	public static Picasso with() {
		if (singleton == null) {
			synchronized (Picasso.class) {
				if (singleton == null) {
					singleton = new Builder().build();
				}
			}
		}
		return singleton;
	}

	/**
	 * 清空内存缓存-不清空磁盘缓存
	 */
	public static void clearCache() throws IOException {
		if (singleton == null || okHttpDownloader == null) {
			with();
		}
		// 清空缓存-内存
		singleton.cache.clear();
	}

	/**
	 * 清空磁盘缓存
	 */
	public static void removeDiskCache() throws IOException {
		clearCache();
		// 清空缓存-sdcard
		okHttpDownloader.getClient().getCache().evictAll();
	}

	public static class Builder {
		/**
		 * 缓存路径 *
		 */
		final static String CACHE_PATH = ".j2w_base/img_cache/";
		/**
		 * 缓存大小 *
		 */
		final static int DISK_CACHE_MAX_SIZE = 200 * 1024 * 1024;

		private Picasso picasso;
		private File file;

		private void defaults() {
			// 创建文件
			if (file == null) {
				if (AppUtils.isSDCardState()) {
					file = new File(Environment.getExternalStorageDirectory(), CACHE_PATH);
				} else {
					file = new File(J2WHelper.getInstance().getApplicationContext().getCacheDir(), CACHE_PATH);
				}
				if (!file.exists()) {
					file.mkdirs();
				}
				Log.i("PicassoTools", file.getPath());
			}
			// 创建okhttp下载器
			if (okHttpDownloader == null) {
				okHttpDownloader = new OkHttpDownloader(file, DISK_CACHE_MAX_SIZE);
			}
			// 创建picasso
			if (picasso == null) {
				Picasso.Builder builder = new Picasso.Builder(J2WHelper.getInstance().getApplicationContext());
				builder.downloader(okHttpDownloader);
				picasso = builder.build();
			}
		}

		public Picasso build() {
			defaults();
			return picasso;
		}
	}
}
