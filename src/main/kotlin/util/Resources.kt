package util

/**
 * Utility methods for loading resources.
 */

/**
 * Loads and returns the contents of the specified [resource].
 */
fun loadResource(resource: String): String {
    return object {}.javaClass.getResource(resource).readText()
}

/**
 * Loads and returns the non-empty lines in the specified [resource].
 */
fun loadResourceLines(resource: String): List<String> {
    return loadResource(resource).lines().filterNot { it.isEmpty() }
}