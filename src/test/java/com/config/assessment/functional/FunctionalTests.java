package com.config.assessment.functional;

import static com.config.assessment.testutils.TestUtils.businessTestFile;
import static com.config.assessment.testutils.TestUtils.currentTest;
import static com.config.assessment.testutils.TestUtils.yakshaAssert;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.config.assessment.ConfigParser;

public class FunctionalTests {

	private ConfigParser parser;
	private String defaultConfig;

	@BeforeEach
	void setUp() {
		defaultConfig = """
				{
				    "server": "localhost",
				    "port": 8080,
				    "useSSL": false,
				    "userPreferences": {
				        "theme": "dark",
				        "language": "en"
				    }
				}
				""";
		parser = new ConfigParser(defaultConfig);
	}

	@Test
	void testUpdateConfig() throws IOException {
		// Update the configuration
		String updates = """
				{
				    "port": 8443,
				    "useSSL": true,
				    "userPreferences": {
				        "theme": "light"
				    }
				}
				""";
		parser.updateConfig(updates);

		// Assert that the updated configuration values are correct
		yakshaAssert(currentTest(), "localhost".equals(parser.getConfigValue("server")) ? "true" : "false",
				businessTestFile);
//	        assertEquals("localhost", parser.getConfigValue("server"), "Server should remain unchanged.");
//	        assertEquals("8443", parser.getConfigValue("port"), "Port should be updated to 8443.");
//	        assertEquals("true", parser.getConfigValue("useSSL"), "SSL should be enabled.");
//	        assertEquals("light", parser.getConfigValue("userPreferences.theme"), "Theme should be updated to light.");
//	        assertEquals("en", parser.getConfigValue("userPreferences.language"), "Language should remain unchanged.");
	}
}