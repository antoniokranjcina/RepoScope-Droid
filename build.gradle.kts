import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

private val defaultJvmTarget = JvmTarget.JVM_17
private val defaultJavaVersion = JavaVersion.VERSION_17

subprojects {
    plugins.withType(com.android.build.gradle.BasePlugin::class.java) {
        project.apply(plugin = "kotlin-android")

        extensions.configure<com.android.build.gradle.BaseExtension>("android") {
            compileSdkVersion(SDKVersion.COMPILE_VERSION)
            defaultConfig {
                minSdk = SDKVersion.MIN_VERSION
                targetSdk = SDKVersion.TARGET_VERSION

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = defaultJavaVersion
                targetCompatibility = defaultJavaVersion
            }
            configure<JavaPluginExtension> {
                sourceCompatibility = defaultJavaVersion
                targetCompatibility = defaultJavaVersion
            }
            with(extensions.getByType<KotlinAndroidProjectExtension>()) {
                compilerOptions {
                    jvmTarget.set(defaultJvmTarget)
                }
            }
        }
    }
}