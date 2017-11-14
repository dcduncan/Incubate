package com.dj.incubate.message.types

import com.dj.incubate.domain.Body

enum class MessageRequestType {
	CONSIDER {
		override fun isAllowed() = true

		override fun determineResponse(messageText: String): String {
			val regex = "incubate consider: (?<item>[\\w\\s\\d]+) for (?<duration>\\d+)\\s*(?<timeUnit>\\w+)".toRegex()
			val matchResult = regex.matchEntire(messageText)
					?: return """
						To add another item for incubation please follow the format
						"incubate consider: <item> for <duration> <time unit>"
						ex: "incubate consider: Trip to Italy for 3 weeks"
						""".trimIndent()

			val item = matchResult.groups["item"]?.value
			val duration = matchResult.groups["duration"]?.value
			val timeUnit = matchResult.groups["timeUnit"]?.value

			return "Okay, we'll consider $item for $duration $timeUnit"
		}

		override fun isTypeOf(messageText: String) = messageText.startsWith("incubate consider:")
	},
	STATUS {
		override fun determineResponse(messageText: String): String {
			TODO("not implemented")
		}

		override fun isTypeOf(messageText: String) = messageText.startsWith("incubate status:")
	},
	PROMOTE {
		override fun determineResponse(messageText: String): String {
			TODO("not implemented")
		}

		override fun isTypeOf(messageText: String) = messageText.startsWith("incubate promote:")
	},
	DEMOTE {
		override fun determineResponse(messageText: String): String {
			TODO("not implemented")
		}

		override fun isTypeOf(messageText: String) = messageText.startsWith("incubate demote:")
	},
	UNKNOWN {
		override fun determineResponse(messageText: String) =
				"I am uncertain what you want me to do with this. Try out one of these commands:\\n" +
						"incubate consider: <item> for <duration> <time unit>"
		override fun isTypeOf(messageText: String) = messageText == ""
	}
	;

	internal abstract fun isTypeOf(messageText: String): Boolean
	internal open fun isAllowed() = false
	abstract fun determineResponse(messageText: String): String

	companion object {

		/**
		 * Figures out what type of message this request is.
		 */
		fun decipherResponseText(body: Body): String {
			val messageReceive = body.entry?.get(0)?.messaging?.get(0)!!
			val text = messageReceive.message?.text ?: ""

			val requestType =
					MessageRequestType.values()
							.filter { it.isAllowed() }
							.firstOrNull { it.isTypeOf(text) }
							?: MessageRequestType.UNKNOWN

			return requestType.determineResponse(text)
		}
	}
}