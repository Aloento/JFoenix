plugins {
    `java-library`
    `maven-publish`
    signing
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "org.rationalityfrontline.workaround"
version = "19.0.1"
val NAME = project.name
val DESC = "JavaFX Material Design Library"
val GITHUB_REPO = "RationalityFrontline/JFoenix"

repositories {
    mavenCentral()
}

javafx {
    version = "19"
    modules = listOf("javafx.controls", "javafx.fxml")
    configuration = "compileOnly"
}

tasks {
    withType(JavaCompile::class.java) {
        options.release.set(11)
    }
    compileJava {
        options.compilerArgs = listOf(
            "--add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix",
            "--add-exports=javafx.base/com.sun.javafx.binding=com.jfoenix",
            "--add-exports=javafx.base/com.sun.javafx.event=com.jfoenix",
            "--add-exports=javafx.graphics/com.sun.javafx.stage=com.jfoenix",
            "--add-exports=javafx.graphics/com.sun.javafx.scene=com.jfoenix",
            "--add-exports=javafx.graphics/com.sun.javafx.geom=com.jfoenix",
            "--add-exports=javafx.graphics/com.sun.javafx.scene.text=com.jfoenix",
            "--add-exports=javafx.controls/com.sun.javafx.scene.control.inputmap=com.jfoenix",
            "--add-exports=javafx.graphics/com.sun.javafx.scene.traversal=com.jfoenix",
            "--add-exports=javafx.controls/com.sun.javafx.scene.control=com.jfoenix",
            "--add-exports=javafx.graphics/com.sun.javafx.util=com.jfoenix",
        )
    }
    javadoc {
        options {
            this as StandardJavadocDocletOptions
            addStringOption("Xdoclint:none", "-quiet")
            addMultilineStringsOption("-add-exports").setValue(listOf(
                "javafx.base/com.sun.javafx.event=com.jfoenix",
                "javafx.base/com.sun.javafx.binding=com.jfoenix",
                "javafx.graphics/com.sun.javafx.scene=com.jfoenix",
                "javafx.graphics/com.sun.javafx.scene.text=com.jfoenix",
                "javafx.graphics/com.sun.javafx.stage=com.jfoenix",
                "javafx.graphics/com.sun.javafx.geom=com.jfoenix",
                "javafx.graphics/com.sun.javafx.util=com.jfoenix",
                "javafx.graphics/com.sun.javafx.scene.traversal=com.jfoenix",
                "javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix",
                "javafx.controls/com.sun.javafx.scene.control.inputmap=com.jfoenix",
                "javafx.controls/com.sun.javafx.scene.control=com.jfoenix",
            ))
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set(NAME)
                description.set(DESC)
                packaging = "jar"
                url.set("https://github.com/$GITHUB_REPO")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        name.set("RationalityFrontline")
                        email.set("rationalityfrontline@gmail.com")
                        organization.set("RationalityFrontline")
                        organizationUrl.set("https://github.com/RationalityFrontline")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/$GITHUB_REPO.git")
                    developerConnection.set("scm:git:ssh://github.com:$GITHUB_REPO.git")
                    url.set("https://github.com/$GITHUB_REPO/tree/master")
                }
            }
        }
    }
    repositories {
        fun env(propertyName: String): String {
            return if (project.hasProperty(propertyName)) {
                project.property(propertyName) as String
            } else "Unknown"
        }
        maven {
            val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = env("ossrhUsername")
                password = env("ossrhPassword")
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}