import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.POSTWORLD

plugins {
    kotlin("jvm") version "1.3.72"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    `maven-publish`
}

group = "me.syari.ss.item"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        url = uri(properties["ssMavenRepoURL"] as String)
    }
}

dependencies {
    implementation("com.destroystokyo.paper:paper-api:1.15.2-R0.1-SNAPSHOT")
    implementation("me.syari.ss.core:SS-Core:2.6")
    implementation("me.syari.ss.battle:SS-Battle:1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

bukkit {
    name = project.name
    version = project.version.toString()
    main = "$group.Main"
    author = "sya_ri"
    depend = listOf("SS-Core", "SS-Battle")
    apiVersion = "1.15"
    load = POSTWORLD
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

val jar by tasks.getting(Jar::class) {
    from(configurations.compile.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
}

val sourceJar by tasks.registering(Jar::class) {
    from(sourceSets.main.get().allJava.srcDirs)
}

publishing {
    repositories {
        maven {
            url = uri(properties["ssMavenRepoUploadURL"] as String)
            credentials {
                username = properties["ssMavenRepoUploadUser"] as String
                password = properties["ssMavenRepoUploadPassword"] as String
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(sourceJar.get()) {
                classifier = "sources"
            }
        }
    }
}