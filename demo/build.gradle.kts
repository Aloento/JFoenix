plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

application {
    mainClass.set("demos.MainDemo")
    mainModule.set("demo.main")
    applicationDefaultJvmArgs = listOf(
        "--add-opens", "java.base/java.lang.reflect=com.jfoenix",
        "--add-exports", "javafx.base/com.sun.javafx.binding=com.jfoenix",
        "--add-opens", "javafx.graphics/javafx.scene=com.jfoenix",
        "--add-opens", "javafx.graphics/javafx.scene.text=com.jfoenix",
        "--add-exports", "javafx.base/com.sun.javafx.event=com.jfoenix",
        "--add-exports", "javafx.graphics/com.sun.javafx.scene=com.jfoenix",
        "--add-exports", "javafx.graphics/com.sun.javafx.stage=com.jfoenix",
        "--add-exports", "javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix"
    )
}

repositories {
    mavenCentral()
}

dependencies {
    // NOTE: the latest version _is_ 8.0.1!
    // 8.0.7 was published by mistake
    implementation("io.datafx:datafx:8.0.1")
    implementation("io.datafx:flow:8.0.1")
    // FontAwesome
    // Versions higher than 2.x are for Java 11
    implementation("org.kordamp.ikonli:ikonli-javafx:2.4.0")
    implementation("org.kordamp.ikonli:ikonli-fontawesome5-pack:2.4.0")
    implementation("jakarta.annotation:jakarta.annotation-api:1.3.5")
    implementation(project(":jfoenix"))
}

javafx {
    version = "19"
    modules = listOf("javafx.controls", "javafx.fxml")
}
