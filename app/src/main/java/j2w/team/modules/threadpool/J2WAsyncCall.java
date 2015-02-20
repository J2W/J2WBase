package j2w.team.modules.threadpool;

/**
 * Created by sky on 15/2/20.
 */
public abstract class J2WAsyncCall extends J2WRunnable {

	public J2WAsyncCall(String methodName) {
		super("J2W Method Name %s", methodName);
	}
}
