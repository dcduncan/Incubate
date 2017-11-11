package com.dj.incubate.domain

import com.dj.incubate.domain.MessageReceiveBodyProvider.createBody
import com.dj.incubate.domain.MessageReceiveBodyProvider.createRequestInputFromBody
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

/**
 * Tests for [RequestMapper].
 */
class RequestMapperTest {

	private lateinit var request: LinkedHashMap<String, Any>
	private lateinit var body: Body

	/**
	 * Sets up common variables for tests.
	 */
	@Before
	fun setUp() {
		body = createBody()
		request = createRequestInputFromBody(body)
	}

	@Test
	fun testInputToDomain() {
		val actualBody = RequestMapper.createBodyFromRequest(request = request)
		actualBody `should equal` body
	}
}