package com.dj.incubate.message

import com.dj.incubate.configuration.Environment.EnvironmentVariable.PAGE_ACCESS_TOKEN
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody

/**
 * Responsible for sending messages back to messenger.
 */
object MessageSender {

	/**
	 * Sends a message back to the original sender.
	 * @return `true` if the request was successful. `false` otherwise.
	 */
	fun sendResponse(callFactory: () -> Call.Factory, jsonPayload: String): Boolean {
		val pageAccessToken = PAGE_ACCESS_TOKEN.getValue()
		val mediaType = MediaType.parse("application/json; charset=utf-8")
		val body = RequestBody.create(mediaType, jsonPayload)
		val request = Request.Builder()
				.url("https://graph.facebook.com/v2.6/me/messages?access_token=$pageAccessToken")
				.post(body)
				.build()
		val response = callFactory().newCall(request).execute()
		return response.isSuccessful
	}
}