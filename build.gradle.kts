buildscript {
    val agp_version by extra("7.3.1")
    val agp_version1 by extra("8.0.0")
    val agp_version2 by extra("7.1.3")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false

    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("androidx.navigation.safeargs") version "2.4.2" apply false
}