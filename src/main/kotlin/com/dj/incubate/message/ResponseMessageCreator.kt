package com.dj.incubate.message

import com.dj.incubate.domain.Body
import com.dj.incubate.message.types.MessageRequestType

/**
 * Receives the incoming message and determines the response.
 */
object ResponseMessageCreator {

	/**
	 * Receives the request and generates the message to send back.
	 */
	fun generateJsonPayload(body: Body): String {
		val messageReceive = body.entry?.get(0)?.messaging?.get(0)!!
		val responseText = MessageRequestType.decipherResponseText(body)

		return """{
		|  "messaging_type" : "RESPONSE",
		|  "recipient" : {
		|    "id" : "${messageReceive.sender?.id}"
		|  },
		|  "message" : {
		|    "text" : "$responseText"
		|  }
		|}""".trimMargin()
	}
}