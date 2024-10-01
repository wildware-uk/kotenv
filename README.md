# kotenv

A lightweight Kotlin library for reading environment variables and reading dotenv files with interpolation support.

## Supported Targets

- jvm
- linuxX64
- mingwX64

## Usage

```kotlin
getEnv("PATH")
getEnvOrNull("PATH")
```

getEnv("YOUR_ENV_VARIABLE_NAME")
getEnvOrNull("YOUR_ENV_VARIABLE_NAME")

## Adding and Using Dotenv Files

1. First, create a `.env` file in the root of your project directory. Here's an example:

    ```plaintext
    DATABASE_URL=jdbc:mysql://localhost:3306/mydatabase
    API_KEY=123456789
    ```

2. Then, use the `useDotEnv()` function in your code to load the variables from the `.env` file:

    ```kotlin
    // Load environment variables from the .env file
    useDotEnv()

    // Now you can access them using getEnv or getEnvOrNull
    val dbUrl = getEnv("DATABASE_URL")
    val apiKey = getEnvOrNull("API_KEY")
    ```

## Interpolation Support

Environment variables support interpolation, allowing you to reference other variables within your `.env` file:

    ```plaintext
    HOST=localhost
    PORT=3306
    DATABASE_URL=jdbc:mysql://$HOST:$PORT/mydatabase
    ```

When using `getEnv`, the library will automatically interpolate these variables.

    ```kotlin
    // After loading the .env file, variables will be interpolated
    useDotEnv()
    val dbUrl = getEnv("DATABASE_URL") // jdbc:mysql://localhost:3306/mydatabase
    ```

## Error Handling

- `getEnv(variableName: String)` will throw an `Exception` if the variable does not exist.
- `getEnvOrNull(variableName: String)` will return `null` if the variable does not exist, allowing you to handle missing
  variables gracefully.

## Example

    ```kotlin
    import kotenv.useDotEnv
    import kotenv.getEnv
    import kotenv.getEnvOrNull

    fun main() {
        // Load environment variables
        useDotEnv()

        // Accessing an existing environment variable
        val path = getEnv("PATH")
        println("PATH: $path")

        // Accessing a non-existing environment variable using getEnvOrNull
        val nonExistentVar = getEnvOrNull("NON_EXISTENT_VAR")
        if (nonExistentVar == null) {
            println("NON_EXISTENT_VAR not found")
        }
    }
    ```

By following the above instructions, you can manage environment variables efficiently in your Kotlin applications using
the `kotenv` library.