package j2w.team.modules.http;

import java.util.regex.Pattern;

/**
 * Created by sky on 15/2/13.
 */
final class MethodInfo {
    
    enum ExecutionType {
        ASYNC,
        SYNC
    }

    private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    private static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);
    private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");
    
    
}
