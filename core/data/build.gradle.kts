plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = NameSpace.CORE_DATA

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(project(Module.DATA_SOURCE_LOCAL))
    implementation(project(Module.DATA_SOURCE_REMOTE))
    implementation(project(Module.MODEL))

    implementation(libs.koin.android)
}
