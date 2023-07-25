
plugins {
    id("java")
}

group = "dev.vacariu.MCTycoon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://redempt.dev")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("com.github.Redempt:RedLib:6.5.8")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    tasks.withType<Jar> {
        outputs.dir(file("/home/andreivacariu/Downloads/test/plugins"))
        destinationDirectory.set(file("/home/andreivacariu/Downloads/test/plugins"))
    }
}
