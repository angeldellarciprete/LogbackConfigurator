package util.logback.configurator;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class SafeLoggingConfigurator
{
	private SafeLoggingConfigurator()
	{
		//
	}
	
	private static final Map<String, String> SAFE_CONFIGS;
	static
	{
	    Map<String, String> map = new HashMap<>();
	    map.put("pc", "logback-pc.xml");
	    map.put("p",  "logback-prod.xml");
	    SAFE_CONFIGS = Collections.unmodifiableMap(map);
	}	
    
	public static void configure(String environment)
	{
		String configLocation = SAFE_CONFIGS.get(environment);
		if (configLocation == null)
		{
			throw new SafeLoggingConfiguratorException("Unknown environment: " + environment, null);
		}

		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.reset();
	       
		// Sonar S4792: Safe because only trusted, classpath resources are used. No user input.
		// NOSONAR
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(context);
		
		URL configUrl = null;
		try
		{
			configUrl = SafeLoggingConfigurator.class.getClassLoader().getResource(configLocation);
			if (configUrl == null)
			{
			    throw new SafeLoggingConfiguratorException("Logback config not found in classpath: " + configLocation, null);
			}
			configurator.doConfigure(configUrl);			
		}
		catch (JoranException e)
		{
			throw new SafeLoggingConfiguratorException("Failed to auto-configure logback", e);
		}		
		
		Logger logger = LoggerFactory.getLogger(SafeLoggingConfigurator.class);
		logger.info("*********************************************************************");
		logger.info("logger initialised");
		logger.info("logback configuration file URL: {}", configUrl);
	}
    
	public static  class SafeLoggingConfiguratorException extends RuntimeException
	{
		private static final long serialVersionUID = -6981817548592584437L;

		public SafeLoggingConfiguratorException(String message, Throwable cause)
		{
			super(message, cause);
		}
	}   
}