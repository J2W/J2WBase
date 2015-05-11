package j2w.team.modules.threadpool;

import java.lang.reflect.Method;

/**
 * Created by sky on 15/2/20.
 */
public abstract class J2WAsyncCall extends J2WRunnable {

	public String	mehtodName;

	public J2WStack	j2WStack;

	public Method	method;

	public Method	methodError;

	public Object[] args;

	public J2WAsyncCall(String methodName, J2WStack j2WStack, Method method, Method methodError,Object[] args) {
		super("J2W Method Name %s", methodName);
		this.mehtodName = methodName;
		this.j2WStack = j2WStack;
		this.method = method;
		this.methodError = methodError;
		this.args = args;
	}
}
