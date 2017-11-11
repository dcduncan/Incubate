package com.dj.incubate.message

import com.dj.incubate.domain.Body

/**
 * Receives the incoming message and determines the response.
 */
object ResponseMessageCreator {

	/**
	 * Receives the request and generates the message to send back.
	 */
	fun generateJsonPayload(body: Body): String {
		val messageReceive = body.entry?.get(0)?.messaging?.get(0)!!
		return """{
		|  "messaging_type" : "RESPONSE",
		|  "recipient" : {
		|    "id" : "${messageReceive.sender?.id}"
		|  },
		|  "message" : {
		|    "text" : "So you said '${messageReceive.message?.text}'"
		|  }
		|}""".trimMargin()
	}
}