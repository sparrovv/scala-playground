import $file.JsonMapper
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty}

case class Bar(score: Option[Int], status: String ) {
  @JsonProperty("this_is_working")
  def isThisWorking:Boolean = true

  def isThisYetWorking:Boolean = true

  @JsonIgnore
  def hey:String = "foo"
}

val bar = Bar(Some(1), "start")
println(JsonMapper.toJson(bar))