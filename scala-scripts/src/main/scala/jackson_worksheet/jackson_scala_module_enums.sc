import $file.JsonMapper
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration


// this TypeReference class is needed for Jackson JSON serialization
class EnumsStatusType extends TypeReference[EnumStatus.type]
object EnumStatus extends Enumeration {
  val Start = Value("start")
  val End = Value("end")
}

case class Bar(
  @JsonDeserialize(contentAs = classOf[java.lang.Integer])
  score: Option[Int],
  @JsonScalaEnumeration(classOf[EnumsStatusType])
  status: EnumStatus.Value
)

val bar = Bar(Some(1), EnumStatus.Start)
println(JsonMapper.JsonMapper.toJson(bar))