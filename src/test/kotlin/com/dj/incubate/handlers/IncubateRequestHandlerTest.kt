package com.dj.incubate.handlers

import com.dj.incubate.configuration.Environment
import com.dj.incubate.domain.Body
import com.dj.incubate.domain.MessageReceiveBodyProvider.createBody
import com.dj.incubate.domain.MessageReceiveBodyProvider.createRequestInputFromBody
import com.dj.incubate.message.MockHttpCreator.createMockCall
import com.dj.incubate.message.MockHttpCreator.createMockCallFactory
import com.dj.incubate.message.RequestVerifier
import com.dj.incubate.message.ResponseMessageCreator
import okhttp3.Call
import okhttp3.Request
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.EnvironmentVariables
import org.mockito.ArgumentCaptor
import java.util.UUID

/**
 * Tests for [IncubateRequestHandler].
 */
class IncubateRequestHandlerTest {

	@get:Rule
	private val environmentVariables = EnvironmentVariables()

	private lateinit var requestHandler: IncubateRequestHandler

	private lateinit var requestInput: LinkedHashMap<String, Any>
	private lateinit var body: Body
	private lateinit var call: Call
	private lateinit var requestCaptor: ArgumentCaptor<Request>
	private lateinit var callFactory: Call.Factory

	/**
	 * Sets up common variables for tests.
	 */
	@Before
	fun setUp() {
		Environment.EnvironmentVariable.values().forEach {
			val value = UUID.randomUUID().toString()
			environmentVariables.set(it.name, value)
		}

		body = createBody()
		requestInput = createRequestInputFromBody(body)
		call = createMockCall()
		requestCaptor = ArgumentCaptor.forClass(Request::class.java)
		callFactory = createMockCallFactory(call, requestCaptor)
		requestHandler = IncubateRequestHandler({ callFactory })
	}

	/**
	 * Tests main flow of application from entry to message sent.
	 */
	@Test
	fun testMessageSent() {
		val response = requestHandler.handleRequest(requestInput, null)

		response.statusCode `should equal` 200
		val actualRequest = requestCaptor.value
		val jsonPayload = ResponseMessageCreator.generateJsonPayload(body)
		RequestVerifier.validateResponse(actualRequest, jsonPayload)
	}
}