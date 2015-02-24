package j2w.team.modules.http;

/**
 * Created by sky on 15/2/24.
 */
public interface J2WErrorHandler {

	Throwable handleError(J2WError cause);

	J2WErrorHandler DEFAULT = new J2WErrorHandler() {
		@Override public Throwable handleError(J2WError cause) {
			return cause;
		}
	};
}