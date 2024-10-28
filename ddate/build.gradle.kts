plugins {
   java
   application
}

apply(from = rootProject.file("buildSrc/shared.gradle.kts"))

sourceSets {
    main {
        java { setSrcDirs(listOf("src")) }
    }
}

dependencies {
    implementation(project(":discordian"))
    implementation("org.rwtodd:org.rwtodd.args:2.0.1")
}

application {
    mainModule = "ddate"
    mainClass = "ddate.Cmd"
}

tasks.register("runcmd") {
    dependsOn("assemble")
    doLast {
        var cp = sourceSets["main"].runtimeClasspath.filter { it.name.endsWith(".jar") }.getFiles().toList().map { it.getAbsolutePath() }
        cp += File(projectDir,"build/libs/ddate-1.0.0.jar").getAbsolutePath()
        var joinChar = ":"
        if ( (System.getProperties().get("os.name") as String).lowercase().contains("windows")) {
            joinChar = ";"
        }
        println("java -p '${cp.joinToString(joinChar)}' -m ddate")
    }
}

