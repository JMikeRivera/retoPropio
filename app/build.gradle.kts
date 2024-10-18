import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.claymation.retopropio"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.claymation.retopropio"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        // Lee el API_URL desde local.properties
        val localProperties = Properties()
        localProperties.load(rootProject.file("local.properties").inputStream())
        val apiKey = localProperties.getProperty("api_key")

        // Asegúrate de que no esté vacío
        if (apiKey != null) {
            buildConfigField("String", "api_key", "\"$apiKey\"")
        } else {
            throw GradleException("api_key no está definido en local.properties")
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/LICENSE.md"
        }
    }
}

dependencies {
    androidTestImplementation(libs.androidx.ui.test.junit4.v151)
    debugImplementation (libs.androidx.ui.tooling.v151)
    debugImplementation (libs.androidx.ui.test.manifest.v151)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation (libs.ui.test.junit4)
    debugImplementation (libs.ui.tooling)
    debugImplementation (libs.ui.test.manifest)
    testImplementation (libs.junit)
    testImplementation (libs.androidx.core.testing.v210)
    testImplementation (libs.kotlinx.coroutines.test.v164)
    testImplementation (libs.mockito.core.v480)
    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.robolectric.v49)
    testImplementation (libs.mockk)
    testImplementation (libs.mockk.android)
    implementation(libs.generativeai)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.core.ktx)
    implementation(libs.okhttp)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing) // Para LiveData y ViewModel
    testImplementation(libs.kotlinx.coroutines.test) // Para probar corutinas
    implementation(libs.coil.compose)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.android.mail)
    implementation(libs.android.activation)
    implementation(libs.exoplayer)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.datastore.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}