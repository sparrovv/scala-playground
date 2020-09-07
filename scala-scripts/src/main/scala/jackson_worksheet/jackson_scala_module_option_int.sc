import $file.JsonMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/*
 * Just a demo of this behaviour: https://github.com/FasterXML/jackson-module-scala/wiki/FAQ
 */
case class IntInOption(
    score: Option[Int]
)

case class FixedIntInOption(
    @JsonDeserialize(contentAs = classOf[java.lang.Integer]) score: Option[Int]
)

case class NoOption(
    score: Int
)

val jsonWithIntAsInt =
    """
      |{"score": 1}
      |""".stripMargin.stripLineEnd

val jsonWithIntAsStr =
    """
    |{"score": "1"}
    |""".stripMargin.stripLineEnd

val intInOption = IntInOption(Some(1))

println(s"Deserializing: ${jsonWithIntAsStr}")
val intInOptionDes = JsonMapper.fromJson[IntInOption](jsonWithIntAsStr)
println(s"checking equality for Option: ${intInOptionDes == intInOption}")

println(s"Deserializing: ${jsonWithIntAsInt}")
val intInOptionDes1 = JsonMapper.fromJson[IntInOption](jsonWithIntAsInt)
println(s"checking equality: ${intInOptionDes1 == intInOption}")

println("A fixed version with an annotation:")
val fixedIntInOption = FixedIntInOption(Some(1))
val fixedIntInOptionDes = JsonMapper.fromJson[FixedIntInOption](jsonWithIntAsStr)
println(s"checking equality ${fixedIntInOptionDes == fixedIntInOption}")
