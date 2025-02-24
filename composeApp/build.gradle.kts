import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import kotlin.io.path.div

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation (libs.androidx.media3.exoplayer)
            implementation (libs.androidx.media3.exoplayer.dash)
            implementation (libs.androidx.media3.ui)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

//            implementation(libs.compose.multiplatform.media.player)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.vlcj)
            implementation(libs.jna)
        }
    }
}

android {
    namespace = "info.test"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "info.test"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "info.test.MainKt"

        buildTypes.release.proguard {
            obfuscate.set(true)
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Music Player"
            packageVersion = "1.0.0"

//            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))

//            appResourcesRootDir = (rootDir.toPath() / "vlc").toFile()
            appResourcesRootDir.set((rootDir.toPath() / "resources").toFile())
//            fromFiles("src/desktopMain/resources/vlc/libvlc.dll", "src/desktopMain/resources/vlc/libvlccore.dll")

//            windows {
//                when (val arch = System.getProperty("os.arch")) {
//                    "amd64" -> {
//                        appResourcesRootDir.set(projectDir.resolve("vlc/windows/x64"))
//                    }
//                    "x86" -> {
//                        appResourcesRootDir.set(projectDir.resolve("vlc/windows/x86"))
//                    }
//                    else -> {
//                        throw IllegalStateException("Unsupported Windows architecture: $arch")
//                    }
//                }
//            }
//
//            linux {
//                appResourcesRootDir.set(projectDir.resolve("vlc/linux/x64"))
//            }
//
//            macOS {
//                appResourcesRootDir.set(projectDir.resolve("vlc/macos/x64"))
//            }
        }
    }
}

//tasks.register<Copy>("copyVlcLibs") {
//    from("src/desktopMain/resources/vlc")
//    into("$buildDir/libs/vlc")
//}
//
//tasks.named<JavaExec>("runJvm") {
//    dependsOn("copyVlcLibs")
//}

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from("src/desktopMain/resources/vlc/") {
        into("vlc/")
    }
}

tasks.withType<JavaExec> {
    doFirst {
        copy {
            from("src/desktopMain/resources/vlc")
            into("${buildDir}/vlc")
        }
    }
    systemProperty("jna.library.path", "${buildDir}/vlc")
}