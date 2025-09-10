package util.logback.configurator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SafeLoggingConfiguratorTest
{
	@Test
	void testAdd()
	{
		assertEquals(5, SafeLoggingConfigurator.add(2, 3), "2 + 3 should equal 5");
	}
}
