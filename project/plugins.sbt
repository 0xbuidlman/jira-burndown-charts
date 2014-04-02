// Comment to get more information during initialization
logLevel := Level.Warn

resolvers += Resolver.sonatypeRepo("public")

resolvers += "Typesafe job" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "scct-github-job" at "http://mtkopone.github.com/scct/maven-repo"

resolvers += "spray repo" at "http://repo.spray.io"

// Reloads process FAST
addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.1")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

// Code coverage tool -- https://github.com/sqality/scct
addSbtPlugin("com.sqality.scct" % "sbt-scct" % "0.3")

// Enforcing formatting
addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.2.1")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.10.2")

// scalaxb - like jaxb but for scala (xml parser)
addSbtPlugin("org.scalaxb" % "sbt-scalaxb" % "1.1.2")

