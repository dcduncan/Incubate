package com.dj.incubate.configuration

import com.dj.incubate.configuration.Environment.EnvironmentVariable
import com.dj.incubate.configuration.Environment.getUnassignedEnvironmentVariables
import org.amshove.kluent.`should be empty`
import org.amshove.kluent.`should contain all`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.EnvironmentVariables
import java.util.UUID

/**
 * Tests for [EnvironmentVariable].
 */
class EnvironmentTest {

	@get:Rule
	private val environmentVariables = EnvironmentVariables()

	private lateinit var environmentVariableValues : MutableMap<EnvironmentVariable, String>

	/**
	 * Sets up common variables for tests.
	 */
	@Before
	fun setUp() {
		environmentVariableValues = mutableMapOf()
		EnvironmentVariable.values().forEach {
			val value = UUID.randomUUID().toString()
			environmentVariables.set(it.name, value)
			environmentVariableValues[it] = value
		}
	}

	/**
	 * Tests proper retrieval of the environment variables.
	 */
	@Test
	fun testRetrieveEnvironmentVariables() {
		EnvironmentVariable.values().forEach {
			it.getValue()!! `should equal` environmentVariableValues[it]!!
		}
	}

	/**
	 * Tests finding undefined environment variables.
	 */
	@Test
	fun testFindingUndefinedEnvironmentVariables() {
		Environment.getUnassignedEnvironmentVariables().`should be empty`()

		EnvironmentVariable.values().forEach {
			environmentVariables.set(it.name, null)
			getUnassignedEnvironmentVariables() `should contain` it
		}

		getUnassignedEnvironmentVariables() `should contain all` listOf(*EnvironmentVariable.values())
	}
}