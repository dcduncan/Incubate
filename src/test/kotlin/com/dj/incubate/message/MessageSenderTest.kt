package com.dj.incubate.message

import com.dj.incubate.configuration.Environment.EnvironmentVariable
import com.dj.incubate.message.MockHttpCreator.createMockCall
import com.dj.incubate.message.MockHttpCreator.createMockCallFactory
import com.dj.incubate.message.RequestVerifier.validateResponse
import okhttp3.Request
import org.amshove.kluent.`should be true`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.EnvironmentVariables
import org.mockito.ArgumentCaptor

/**
 * Tests for [MessageSender].
 */
class MessageSenderTest {

	@get:Rule
	private val environmentVariables = EnvironmentVariables()

	/**
	 * Sets up common variables for tests.
	 */
	@Before
	fun setUp() {
		environmentVariables.set(EnvironmentVariable.PAGE_ACCESS_TOKEN.name, "page_access_token")
	}

	/**
	 * Tests that the appropriate response was sent.
	 */
	@Test
	fun testSendResponse() {
		val call = createMockCall()
		val requestCaptor = ArgumentCaptor.forClass(Request::class.java)
		val callFactory = createMockCallFactory(call, requestCaptor)
		val jsonPayload = """{"object":"thing"}"""
		val wasSuccessful = MessageSender.sendResponse({ callFactory }, "$jsonPayload")

		val actualRequest = requestCaptor.value
		wasSuccessful.`should be true`()
		validateResponse(actualRequest, jsonPayload)
	}
}