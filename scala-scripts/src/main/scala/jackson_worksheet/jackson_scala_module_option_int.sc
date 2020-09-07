import $file.JsonMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/*
 * Just a demo of this behaviour: https://github.com/FasterXML/jackson-module-scala/wiki/FAQ
 */
case class Bar(
    score: Option[Int],
    status: String
)

case class FixedBar(
    @JsonDeserialize(contentAs = classOf[java.lang.Integer]) score: Option[Int],
    status: String
)

val jsonWithIntAsInt =
    """
      |{"score": 1, "status": "start"}
      |""".stripMargin.stripLineEnd

val jsonWithIntAsStr =
    """
    |{"score": "1", "status": "start"}
    |""".stripMargin.stripLineEnd

val bar = Bar(Some(1), "start")

println(s"Deserializing: ${jsonWithIntAsStr}")
val deBar = JsonMapper.fromJson[Bar](jsonWithIntAsStr)
println(s"checking equality: ${deBar == bar}")

println(s"Deserializing: ${jsonWithIntAsInt}")
val deBar1 = JsonMapper.fromJson[Bar](jsonWithIntAsInt)
println(s"checking equality: ${deBar1 == bar}")

println("A version with the right annotation:")
val fixedBar = FixedBar(Some(1), "start")
val deFixedBar = JsonMapper.fromJson[FixedBar](jsonWithIntAsStr)
println(s"checking equality ${deFixedBar == fixedBar}")