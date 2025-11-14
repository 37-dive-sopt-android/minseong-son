import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.sopt.dive"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sopt.dive"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL_REQUIRED", properties["base.url.required"].toString())
        buildConfigField("String", "BASE_URL_ADVANCED", properties["base.url.advanced"].toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}



dependencies {
    implementation(libs.bundles.androidx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.kotlinx)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.coil.compose)
    implementation(libs.bundles.room)
    implementation(libs.bundles.paging)
    ksp(libs.room.compiler)
    debugImplementation(libs.bundles.debug)
    testImplementation(libs.bundles.unit.test)
    androidTestImplementation(libs.bundles.instrumented.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
}

configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}
