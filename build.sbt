name        := "kitlangton.com"
description := "Kit's website"
version     := "0.0.1"

val zioVersion       = "2.0.0"
val animusVersion    = "0.2.0"
val boopickleVersion = "1.4.0"
val laminarVersion   = "0.14.0"
val laminextVersion  = "0.13.6"
val quillZioVersion  = "3.10.0"
val scalaMetaVersion = "4.5.9"

Global / onChangedBuildSource := ReloadOnSourceChanges

val sharedSettings = Seq(
  addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.2" cross CrossVersion.full),
  addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
  libraryDependencies ++= Seq(
    "io.suzaku" %%% "boopickle" % boopickleVersion
  ),
  scalacOptions ++= Seq("-Ymacro-annotations", "-deprecation", "-feature"),
  // remove fatal warnings if not in CI
  scalacOptions ++= (if (sys.env.contains("CI")) Seq("-Xfatal-warnings") else Seq()),
  scalaVersion   := "2.13.8",
  testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
)

lazy val root = project
  .in(file("."))
  .settings(
    publish / skip := true
  )
  .aggregate(frontend, backend, shared)

lazy val frontend = project
  .in(file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
//    scalaJSLinkerConfig ~= { _.withSourceMap(false) },
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "io.github.kitlangton" %%% "animus"          % animusVersion,
      "com.raquo"            %%% "laminar"         % laminarVersion,
      "io.github.cquiroz"    %%% "scala-java-time" % "2.4.0",
      "io.laminext"          %%% "websocket"       % laminextVersion,
      "com.raquo"            %%% "waypoint"        % "0.5.0",
      "org.scalameta"        %%% "scalameta"       % scalaMetaVersion
    )
  )
  .settings(sharedSettings)
  .dependsOn(shared)

lazy val backend =
  project
    .in(file("backend"))
    .settings(
      sharedSettings,
      libraryDependencies ++= Seq(
        "dev.zio"        %% "zio"                         % zioVersion,
        "dev.zio"        %% "zio-test"                    % zioVersion % Test,
        "dev.zio"        %% "zio-test-sbt"                % zioVersion % Test,
        "dev.zio"        %% "zio-json"                    % "0.3.0-RC10",
        "com.google.apis" % "google-api-services-youtube" % "v3-rev20220612-1.32.1"
      )
    )
    .dependsOn(shared)

lazy val shared =
  project
    .in(file("shared"))
    .enablePlugins(ScalaJSPlugin)
    .in(file("shared"))
    .settings(sharedSettings)
    .settings(
      scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) }
    )
