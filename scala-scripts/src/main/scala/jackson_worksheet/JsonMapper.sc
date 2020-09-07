// val jacksonVersion  = "2.10.1"
// interp.load.ivy( "com.fasterxml.jackson.module"   %% "jackson-module-scala"    % jacksonVersion)

import $ivy.`com.fasterxml.jackson.module::jackson-module-scala:2.10.1`
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}

val mapper = new ObjectMapper() with ScalaObjectMapper
mapper.registerModule(DefaultScalaModule)
mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES)
mapper.setSerializationInclusion(Include.NON_NULL)

def toJson[T](value: T): String =
  mapper.writeValueAsString(value)

def fromJson[T](json: String)(implicit m: Manifest[T]): T =
  mapper.readValue[T](json)