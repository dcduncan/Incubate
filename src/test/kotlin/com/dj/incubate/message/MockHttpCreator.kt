package com.dj.incubate.message

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import okhttp3.Call
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.mockito.ArgumentCaptor

/**
 * Creates mock classes for http related items.
 */
object MockHttpCreator {

	/**
	 * Creates a mock [Call] that returns a dummy success
	 * response when [Call.execute] is invoked.
	 */
	fun createMockCall(): Call {
		val request =
				Request.Builder()
						.url("http://url.com")
						.build()
		val response =
				Response.Builder()
						.request(request)
						.protocol(Protocol.HTTP_1_0)
						.message("Some message")
						.code(200)
						.build()

		return mock {
			on { execute() } doReturn response
		}
	}

	/**
	 * Create a mock [Call.Factory] where [Request]s are
	 * captured by the captor and the provided [Call] is returned
	 * when [Call.Factory.newCall] is invoked.
	 */
	fun createMockCallFactory(call: Call, requestCaptor: ArgumentCaptor<Request>): Call.Factory {
		return mock {
			on { newCall(requestCaptor.capture()) } doReturn call
		}
	}
}