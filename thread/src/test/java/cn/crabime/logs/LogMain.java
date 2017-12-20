package cn.crabime.logs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by crabime on 12/20/17.
 */
public class LogMain {
    private static final Log log = LogFactory.getLog("E");
    private static final Log normalLogger = LogFactory.getLog("normal");
    public static void main(String[] args) {
        log.error("i should appear in error log file");
        normalLogger.info("i should appear in output.log file");
    }
}
