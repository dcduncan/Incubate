package com.dj.incubate.configuration

/**
 * Deals with environment variables.
 */
object Environment {

	/**
	 * Retrieves all unassigned [EnvironmentVariable]s.
	 */
	fun getUnassignedEnvironmentVariables(): List<EnvironmentVariable> {
		return EnvironmentVariable
				.values()
				.filter { it.getValue() == null }
				.toList()
	}

	/**
	 * Environment variables for the application.
	 */
	enum class EnvironmentVariable {
		APP_SECRET,
		VERIFY_TOKEN,
		PAGE_ACCESS_TOKEN
		;

		/**
		 * Retrieves the value of the environment variable.
		 */
		fun getValue(): String? = System.getenv(name)
	}
}