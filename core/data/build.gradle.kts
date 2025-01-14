import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization")
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.kover)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "core_data"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:domain"))

                implementation(libs.kotlinx.serialization.json)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)

                implementation(libs.ktorfit.lib)
                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.koin.core)

                implementation(libs.paging.common)
                implementation(libs.sqldelight.paging3)
                implementation(libs.sqldelight.coroutines)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.sqldelight.android.driver)
                implementation(libs.sqldelight.sqlite.driver)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqldelight.native.driver)
                implementation(libs.stately.isolate)
                implementation(libs.stately.iso.collections)
            }
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.mockk.common)
            implementation(libs.mockk)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
            implementation(libs.paging.testing)
            implementation(libs.turbine)
        }
    }
}

android {
    namespace = "com.linh.core.data"

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    dependencies {
        debugImplementation(libs.chucker.library)
        releaseImplementation(libs.chucker.library.no.op)
    }
    testOptions {
        unitTests.isReturnDefaultValues = true // Set this to true to fix issue Method isLoggable in android.util.Log not mocked for Paging tests
    }
}

sqldelight {
    databases {
        create("DemoProjectDatabase") {
            packageName.set("com.linh.demoproject")
        }
    }
}