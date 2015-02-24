package j2w.team.modules.http;

/**
 * Created by sky on 15/2/24. 请求拦截器
 */
public interface J2WRequestInterceptor {

	void intercept(RequestFacade request);

	interface RequestFacade {
		/** 向请求添加标题。这不会取代任何现有的标题. */
		void addHeader(String name, String value);

		/** 添加参数 - 编码格式 utf-8 **/
		void addPathParam(String name, String value);

		/** 添加参数 - 不编码 **/
		void addEncodedPathParam(String name, String value);

	}
}
