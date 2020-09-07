import com.fasterxml.jackson.core.{JsonGenerator, JsonParser}
import com.fasterxml.jackson.databind._
import com.fasterxml.jackson.databind.annotation.{JsonDeserialize, JsonSerialize}
import $file.JsonMapper

@JsonSerialize(using = classOf[StatusSerializer])
@JsonDeserialize(using = classOf[StatusDeserializer])
sealed trait Status{
  def name:String
}
object Start extends Status {
  override val name = "start"
}
object End extends Status {
  override val name = "end"
}

class StatusSerializer extends JsonSerializer[Status] {
  override def serialize( w: Status, json: JsonGenerator, provider: SerializerProvider ): Unit = {
    json.writeString(w.name)
  }
}

class StatusDeserializer extends JsonDeserializer[Status] {
  override def deserialize(p: JsonParser, ctxt: DeserializationContext): Status = {
    import com.fasterxml.jackson.databind.JsonNode
    val node: JsonNode = p.getCodec.readTree(p)
    val s: String = node.asText()
    s match {
      case Start.name => Start
      case End.name => End
      case _ => throw new IllegalArgumentException(
        s"[$s] is not a valid value for Status"
      )
    }
  }
}

case class Foo(score: Option[Int], status: Status)
val foo = Foo(Some(1), Start)

println(JsonMapper.JsonMapper.toJson(foo))
