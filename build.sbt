name := "Pst Reader"

version := "0.0"

mainClass := Some("org.opf.pst.PSTMain")

libraryDependencies ++= Seq(
	"org.apache.tika" % "tika-core" % "1.3",
	"org.apache.tika" % "tika-parsers" % "1.3",
	"org.apache.tika" % "tika-xmp" % "1.3"
)
