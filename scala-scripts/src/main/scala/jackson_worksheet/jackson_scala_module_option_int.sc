import $file.JsonMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

case class Bar(
    score: Option[Int],
    status: String
)

case class FixedBar(
    @JsonDeserialize(contentAs = classOf[java.lang.Integer]) score: Option[Int],
    status: String
)

val json =
    """
    |{"score": "1", "status": "start"}
    |""".stripMargin.stripLineEnd

val deBar = JsonMapper.JsonMapper.fromJson[Bar](json)
val bar = Bar(Some(1), "start")
println(deBar == bar)
println(deBar.score.get.getClass)

val fixedBar = FixedBar(Some(1), "start")
val deFixedBar = JsonMapper.JsonMapper.fromJson[FixedBar](json)
println(deFixedBar == fixedBar)