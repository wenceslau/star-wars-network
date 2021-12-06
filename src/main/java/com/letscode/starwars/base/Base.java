package com.letscode.starwars.base;

import com.letscode.starwars.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Super classe para ser estendida por qualquer classe do projeto
 * @author Wenceslau
 */
public abstract class Base {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Trace info no log
	 * @param value texto
	 */
	protected void info(String value) {
		logger.info(loggerMessage(value));
	}

	/**
	 * Trace info no log
	 * @param value texto
	 */
	protected void warn(String value) {
		logger.warn(loggerMessage(value));
	}

	/**
	 * Trace info no log
	 * @param value texto
	 */
	protected void debug(String value) {
		logger.info(loggerMessage(value));
	}

	/**
	 * Trace error no log
	 * @param value texto
	 */
	protected void error(String value, Throwable throwable) {
		logger.error(loggerMessage(value), throwable);
	}

	/**
	 * Trace error no log
	 * @param value texto
	 */
	protected void error(String value) {
		logger.error(loggerMessage(value));
	}

	/**
	 * Aplica um sleep
	 * @param value milliseconds
	 */
	protected void sleep(int value) {
		try {
			Thread.sleep(value);
		} catch (InterruptedException ignored) {}
	}

	/**
	 * Monta uma msg para o log
	 * @param value texto
	 * @return texto
	 */
	private String loggerMessage(String value) {
		StringBuilder clsMthLn = new StringBuilder();
		for (StackTraceElement ste : new Throwable().getStackTrace()) {
			if (ste.getClassName().equals(this.getClass().getName()))
				Utils.prepareMsgLogger(clsMthLn, ste);
			break;
		}
		if (clsMthLn.length() == 0)
			Utils.prepareMsgLogger(clsMthLn, new Throwable().getStackTrace()[2]);
		clsMthLn.append(value);
		return clsMthLn.toString();
	}

}
