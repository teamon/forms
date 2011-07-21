import sbt._

class Forms(info: ProjectInfo) extends DefaultProject(info){
    val scalatest = "org.scalatest" %% "scalatest" % "1.6.1.RC1"
}
