name := "anonymizater"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.12"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  DefaultMavenRepository,
  Resolver.typesafeRepo("releases"),
  Resolver.sbtPluginRepo("releases"),
  Resolver.sonatypeRepo("public")
)

val sparkVersion = "2.4.0.7.1.6.0-297"

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api" % "2.11.2",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.2",

  "org.apache.spark" %% "spark-core" % s"${sparkVersion}",
  "org.apache.spark" %% "spark-sql" % s"${sparkVersion}",

  "com.typesafe" % "config" % "1.3.2",
  "org.rogach" %% "scallop" % "3.5.0"
)

// Fat jar creation
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

assemblyJarName in assembly := s"${name.value}-${version.value}-jar-with-dependencies.jar"
