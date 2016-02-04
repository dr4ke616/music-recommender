name := """music-recommender"""
scalaVersion := "2.11.7"
version := "0.1.0-SNAPSHOT"

mainClass in Compile := Some("MusicRecommenderApp")

val sparkVersion = "1.6.0"
libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.0",
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided"
)

val meta = """META.INF(.)*""".r
assemblyMergeStrategy in assembly := {
  case meta(_) => MergeStrategy.discard
  case _ => MergeStrategy.first
}
