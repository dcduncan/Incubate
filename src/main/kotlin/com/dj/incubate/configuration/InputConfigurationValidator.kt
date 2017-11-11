package com.dj.incubate.configuration

/**
 * Validates configurations and input.
 */
object InputConfigurationValidator {

	/**
	 * Validates configurations and input.
	 */
	fun validateConfigurationAndInput(input: LinkedHashMap<String, Any>?): Boolean {
		var valid = true
		if (input == null) {
			System.err.println("Input is null")
			valid = false
		}

		val unassignedEnvironmentVariables = Environment.getUnassignedEnvironmentVariables()
		if (unassignedEnvironmentVariables.isNotEmpty()) {
			unassignedEnvironmentVariables.forEach { System.err.println("Environment variable $it is not set") }
			valid = false
		}

		return valid
	}
}