name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final", // replace by your jpa implementation
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
  "org.mockito" % "mockito-core" % "1.9.5"

)

libraryDependencies += "org.springframework" % "spring-context" % "4.3.0.RELEASE"

libraryDependencies += "eu.imind.play" %% "play-cxf_play24" % "1.2.1"

libraryDependencies += "org.apache.cxf" % "cxf-rt-bindings-soap" % "3.1.2"

libraryDependencies += "org.apache.cxf" % "cxf-rt-frontend-jaxws" % "3.1.2"


fork in run := true
