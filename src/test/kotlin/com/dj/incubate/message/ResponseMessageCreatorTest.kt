package com.dj.incubate.message

import com.dj.incubate.domain.Body
import com.dj.incubate.domain.MessageReceiveBodyProvider.createBody
import com.dj.incubate.message.ResponseMessageCreator.generateJsonPayload
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

/**
 * Tests for [ResponseMessageCreator].
 */
class ResponseMessageCreatorTest {

	private lateinit var body: Body

	/**
	 * Sets up common variables for tests.
	 */
	@Before
	fun setUp() {
		body = createBody()
	}

	/**
	 * Tests that the message the is created is properly formatted.
	 */
	@Test
	fun testMessageCreated() {
		val jsonPayload = generateJsonPayload(body)
		val messageReceive = body.entry!![0].messaging!![0]
		val expectedJsonPayload = """{
		|  "messaging_type" : "RESPONSE",
		|  "recipient" : {
		|    "id" : "${messageReceive.sender!!.id}"
		|  },
		|  "message" : {
		|    "text" : "So you said '${messageReceive.message!!.text}'"
		|  }
		|}""".trimMargin()

		jsonPayload `should equal` expectedJsonPayload
	}
}