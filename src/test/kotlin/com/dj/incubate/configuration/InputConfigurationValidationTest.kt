package com.dj.incubate.configuration

import com.dj.incubate.configuration.Environment.EnvironmentVariable
import org.amshove.kluent.`should be false`
import org.amshove.kluent.`should be true`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.EnvironmentVariables
import java.util.UUID

/**
 * Tests for [InputConfigurationValidator].
 */
class InputConfigurationValidationTest {

	@get:Rule
	private val environmentVariables = EnvironmentVariables()

	private lateinit var input: LinkedHashMap<String, Any>

	/**
	 * Sets up common variables for tests.
	 */
	@Before
	fun setUp() {
		EnvironmentVariable.values().forEach {
			val value = UUID.randomUUID().toString()
			environmentVariables.set(it.name, value)
		}
		input = linkedMapOf()
	}

	/**
	 * Tests validation fails when provided input is null.
	 */
	@Test
	fun testValidationFailsOnNullInput() {
		InputConfigurationValidator.validateConfigurationAndInput(null).`should be false`()
	}

	/**
	 * Tests validation fails when an environment variable is missing.
	 */
	@Test
	fun testValidationFailsOnMissingEnvironmentVariables() {
		EnvironmentVariable.values().forEach {
			val previousValue = it.getValue()
			environmentVariables.set(it.name, null)
			InputConfigurationValidator.validateConfigurationAndInput(input).`should be false`()
			environmentVariables.set(it.name, previousValue)
		}
	}

	/**
	 * Tests validation passes when everything is set.
	 */
	@Test
	fun testValidationPassesWhenEverythingIsSet() {
		InputConfigurationValidator.validateConfigurationAndInput(input).`should be true`()
	}
}