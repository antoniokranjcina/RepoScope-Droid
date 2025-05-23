plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = NameSpace.CORE_DATA_SOURCE_REMOTE

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField(type = "String", name = ApiData.URL_NAME, value = ApiData.URL)
        }
        release {
            buildConfigField(type = "String", name = ApiData.URL_NAME, value = ApiData.URL)
        }
    }
}

kotlin {
    explicitApi()
}

dependencies {
    // Network
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)

    // DI - Koin
    implementation(libs.koin.android)

    // Tests
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
}
