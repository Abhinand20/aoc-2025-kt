plugins {
    kotlin("jvm") version "2.2.21"
    application
}


kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

application {
    mainClass = "Day06Kt"
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
}
