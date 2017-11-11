package com.dj.incubate.domain

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.Arrays

private val bodyKey = "body"

object RequestMapper {

	/**
	 * Create a [Body] from the [LinkedHashMap] presumably derived from the AWS lambda request.
	 *
	 * This function expects that the key [bodyKey] is present inside this map and that its value
	 * conforms to the schema modeled by [Body].
	 */
	fun createBodyFromRequest(request: LinkedHashMap<String, Any>): Body {
		val objectMapper = ObjectMapper()
		val bodyJson = request[bodyKey].toString()
		return objectMapper.readValue(bodyJson, Body::class.java)
	}
}

/**
 * Container class for the whole request.
 */
data class Body(var `object`: String? = null, var entry: Array<Entry>? = null) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Body

		if (`object` != other.`object`) return false
		if (!Arrays.equals(entry, other.entry)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = `object`?.hashCode() ?: 0
		result = 31 * result + (entry?.let { Arrays.hashCode(it) } ?: 0)
		return result
	}
}

/**
 * Container class for the entry. Holds all of the messages received.
 */
data class Entry(var id: String? = null, var time: Long? = null, var messaging: Array<MessageReceive>? = null) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Entry

		if (id != other.id) return false
		if (time != other.time) return false
		if (!Arrays.equals(messaging, other.messaging)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id?.hashCode() ?: 0
		result = 31 * result + (time?.hashCode() ?: 0)
		result = 31 * result + (messaging?.let { Arrays.hashCode(it) } ?: 0)
		return result
	}
}

/**
 * Container class for Message receive event.
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/messages">Facebook Message Api</a>
 */
data class MessageReceive(var sender: Sender? = null, var recipient: Recipient? = null, var timestamp: Long? = null, var message: Message? = null)

/**
 * Sender for the [MessageReceive] even.
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/messages">Facebook Message Api</a>
 */
data class Sender(var id: String? = null)

/**
 * Recipient for the [MessageReceive] even.
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/messages">Facebook Message Api</a>
 */
data class Recipient(var id: String? = null)

/**
 * Message for the [MessageReceive] even.
 * @see <a href="https://developers.facebook.com/docs/messenger-platform/reference/webhook-events/messages">Facebook Message Api</a>
 */
data class Message(var mid: String? = null, var text: String? = null, var seq: Long? = null)