package util.logback.configurator;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SafeLoggingConfiguratorTest
{
	private static Logger logger = LoggerFactory.getLogger(SafeLoggingConfiguratorTest.class);
	
	@Test
	void test()
	{
		logger.info("before SafeLoggingConfigurator.configure()");
		
		SafeLoggingConfigurator.configure("pc");
		
		logger.info("after SafeLoggingConfigurator.configure()");
	}
}
