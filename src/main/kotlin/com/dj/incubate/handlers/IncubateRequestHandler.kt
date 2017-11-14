package com.dj.incubate.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.dj.incubate.configuration.InputConfigurationValidator
import com.dj.incubate.domain.RequestMapper
import com.dj.incubate.message.MessageSender
import com.dj.incubate.message.ResponseMessageCreator
import okhttp3.Call
import okhttp3.OkHttpClient

/**
 * The [RequestHandler] for the application. Sets up dependencies and delegates.
 */
class IncubateRequestHandler(
		private val callFactory: () -> Call.Factory = { OkHttpClient() }
) : RequestHandler<LinkedHashMap<String, Any>, Response> {

	/**
	 * The main entry point for the lambda execution. Validates input then delegates.
	 */
	override fun handleRequest(input: LinkedHashMap<String, Any>?, context: Context?): Response {

		if (!InputConfigurationValidator.validateConfigurationAndInput(input)) {
			return Response(200)
		}

		val body =
				try {
					RequestMapper.createBodyFromRequest(input!!)
				} catch (e: RuntimeException) {
					System.err.println("Error encountered while deserializing request:" + e.message)
					System.err.println(e.stackTrace)
					System.err.println("Here is the body:")
					System.err.println(input!!["body"])
					return Response(200)
				}

		val jsonPayload = ResponseMessageCreator.generateJsonPayload(body)
		val responseSentSuccessfully = MessageSender.sendResponse(callFactory, jsonPayload)
		if (!responseSentSuccessfully) {
			System.err.println("Response was not sent successfully")
		}

		return Response(200)
	}
}

/**
 * Simple container class for the response to send to back to the client.
 */
data class Response(var statusCode: Int? = null)