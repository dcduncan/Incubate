package com.dj.incubate.domain

import com.google.gson.GsonBuilder

/**
 * Creates [Body] objects with similar creation functions for testing.
 */
object MessageReceiveBodyProvider {

	/**
	 * Sets up a simple [Body] for testing.
	 */
	fun createBody(): Body {
		val sender = Sender(id="82398432994032")
		val recipient = Recipient(id="23905095328")
		val message = Message(mid = "mid.\$caaFSanf432Gad", text = "some message", seq = 20034)
		val messageReceive = MessageReceive(sender = sender, recipient = recipient, timestamp = 1510284763868, message = message)
		val entry = Entry(id = "250691668796539", time = 1510284763867, messaging = arrayOf(messageReceive))
		return Body(`object` = "page", entry = arrayOf(entry))
	}

	/**
	 * Creates the main request input from a [Body].
	 */
	fun createRequestInputFromBody(body: Body): LinkedHashMap<String, Any> {
		return linkedMapOf("body" to createJsonFromBody(body))
	}

	/**
	 * Creates a json string from the [Body].
	 */
	private fun createJsonFromBody(body: Body): String {
		val gson = GsonBuilder().setPrettyPrinting().create()
		return gson.toJson(body)
	}
}