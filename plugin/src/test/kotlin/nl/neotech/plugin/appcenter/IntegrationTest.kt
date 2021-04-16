package nl.neotech.plugin.appcenter

import com.google.common.truth.Truth.assertThat
import nl.neotech.plugin.appcenter.util.SystemOutputWriter
import nl.neotech.plugin.appcenter.util.createLocalPropertiesFile
import org.gradle.testkit.runner.GradleRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File

@RunWith(Parameterized::class)
class IntegrationTest(
    // Used by Junit as the test name, see @Parameters
    @Suppress("unused") private val name: String,
    private val projectRoot: File,
    private val gradleVersion: String
) {

    @Test
    fun execute() {
        createLocalPropertiesFile(projectRoot)

        val runner = GradleRunner.create()
            .withProjectDir(projectRoot)
            .withGradleVersion(gradleVersion)
            //.withDebug(true)
            //.withEnvironment(mapOf(
            //    "APPCENTER_API_TOKEN" to System.getProperty("APPCENTER_API_TOKEN")
            //))
            .withPluginClasspath()
            .forwardStdOutput(SystemOutputWriter.out())
            .forwardStdError(SystemOutputWriter.err())
            .withArguments(
                "clean",
                "app:appCenterAssembleAndUploadNonStandardBuildType",
                "-DAPPCENTER_API_TOKEN=${System.getProperty("APPCENTER_API_TOKEN")}",
                "--stacktrace"
            )

        val result = runner.build()

        assertThat(result.output).contains("BUILD SUCCESSFUL")
    }

    companion object {

        @Suppress("unused") // This method is used by the JVM (Parameterized JUnit Runner)
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun parameters(): List<Array<Any>> {

            val testFixtures = File("src/test/test-fixtures").listFiles()?.filter { it.isDirectory }
                ?: error("Could not list test fixture directories")
            val gradleVersions = arrayOf("6.5.1", "6.6.1", "6.7.1", "6.8.3")
            return testFixtures.flatMap { file ->
                gradleVersions.map { gradleVersion ->
                    arrayOf("${file.name}-$gradleVersion", file, gradleVersion)
                }
            }
        }
    }
}