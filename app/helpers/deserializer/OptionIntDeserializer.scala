package helpers.deserializer

import java.io.IOException

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer, JsonNode}

/**
  * @author igorzg on 01.02.18.
  * @since 1.0
  */
class OptionIntDeserializer extends JsonDeserializer[Option[Int]] {

  @throws[IOException]
  override def deserialize(parser: JsonParser,
                           context: DeserializationContext): Option[Int] = {
    val node:JsonNode = parser.getCodec.readTree(parser)
    Option(node.asInt)
  }
}
