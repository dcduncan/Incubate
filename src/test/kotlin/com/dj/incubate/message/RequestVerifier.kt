package com.dj.incubate.message

import com.dj.incubate.configuration.Environment
import okhttp3.Request
import okio.Buffer
import org.amshove.kluent.`should equal`

/**
 * Helper to validate requests sent.
 */
object RequestVerifier {

	/**
	 * Validates that the sent request is correct and contains
	 * the expected json payload.
	 */
	fun validateResponse(request: Request, jsonPayload: String) {
		val buffer = Buffer()
		request.body()!!.writeTo(buffer)

		buffer.readUtf8() `should equal` jsonPayload
		request.method() `should equal` "POST"
		request.url().toString() `should equal` "https://graph.facebook.com/v2.6/me/messages?access_token=${Environment.EnvironmentVariable.PAGE_ACCESS_TOKEN.getValue()}"
	}
}