import nu.studer.gradle.jooq.JooqExtension
import nu.studer.gradle.jooq.JooqPlugin
import nu.studer.gradle.jooq.JooqTask
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.GeneratedAnnotationType.JAVAX_ANNOTATION_GENERATED
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Logging.WARN
import org.jooq.meta.jaxb.Target

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("nu.studer:gradle-jooq-plugin:4.2")
    }
}

apply<JooqPlugin>()

val jooqRuntime by configurations

dependencies {
    jooqRuntime("org.postgresql:postgresql:42.2.12")
}

val dbHost = "130.193.49.140"
val dbPort = 5432
val dbName = "alfa_battle"
val dbSchema = "public"
val dbUsername = System.getProperty("app.datasource.username") ?: "alfa_battle"
val dbPassword = System.getProperty("app.datasource.password") ?: "qwe123"
val datasourceUrl = System.getProperty("app.datasource.jdbcurl")
    ?: "jdbc:postgresql://$dbHost:$dbPort/$dbName?currentSchema=$dbSchema"

val jooqGeneratedPackage = "ru.alfabattle.task3.models"
val jooqGeneratedTarget = "$buildDir/generated/source/jooq/main"
the<SourceSetContainer>()["main"].java.srcDir(jooqGeneratedTarget)

configure<JooqExtension> {
    version = "3.13.2"
}

val jooqGenerate by tasks.registering(JooqTask::class) {
    group = "Database"
    description = "Generates jOOQ sources"

    jooqClasspath = jooqRuntime
    configuration = Configuration().apply {
        logging = WARN
        jdbc = Jdbc().apply {
            url = datasourceUrl
            user = dbUsername
            password = dbPassword
        }
        generator = Generator().apply {
            database = Database().apply {
                inputSchema = dbSchema
                excludes = "databasechangelog|databasechangeloglock|flyway_schema_history"
                isIncludeExcludeColumns = true
            }
            generate = Generate().apply {
                generatedAnnotationType = JAVAX_ANNOTATION_GENERATED
                isDeprecated = false
                isGlobalRoutineReferences = false
                isGlobalSequenceReferences = false
                isGlobalTableReferences = false
                isGlobalUDTReferences = false
                isUdts = true
                isJavaTimeTypes = true
                isRecords = true
                isValidationAnnotations = true
            }
            target = Target().apply {
                directory = jooqGeneratedTarget
                packageName = jooqGeneratedPackage
            }
        }
    }
}
