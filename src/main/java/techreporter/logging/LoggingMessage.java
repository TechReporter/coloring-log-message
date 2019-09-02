package techreporter.logging;

import org.apache.log4j.Logger;

public class LoggingMessage {

	static Logger logger = Logger.getLogger(LoggingMessage.class);

	public static void errorLogMsg() {
		logger.error("All error related message !!");
	}

	public static void debugLogMsg() {
		logger.debug("All debug related message !!");
	} 

	public static void warnLogMsg() {
		logger.warn("All warn related message !!");
	} 

	public static void infoLogMsg() {
		logger.info("All info related message !!");
	} 

	public static void traceLogMsg() {
		logger.trace("All trace related message !!");
	} 

	public static void fatalLogMsg() {
		logger.fatal("All fatal related message !!");
	} 
	
	public static void main(String[] args) {
		fatalLogMsg();
		traceLogMsg();
		warnLogMsg();
		errorLogMsg();
		debugLogMsg();
		infoLogMsg();
	}
}
