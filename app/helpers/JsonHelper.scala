package helpers

import java.io.{OutputStream, Reader, Writer}

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.json4s.{Formats, JsonInput}
import org.json4s.jackson.Serialization
import org.json4s.jackson.JsonMethods
import play.Logger

import scala.reflect.Manifest

/**
  * @author igorzg on 20.01.18.
  * @since 1.0
  */
object JsonHelper extends JsonMethods {

  override def mapper: ObjectMapper =
    super.mapper.registerModule(new DefaultScalaModule)

  @throws(classOf[Exception])
  def writeJson[T](body: T): String = {
    try {
      mapper.writeValueAsString(body)
    } catch {
      case e: Exception =>
        Logger.error("JacksonJsonSerialization", e)
        throw e;
    }
  }

  def parseOpt[T](body: String)(
    implicit typeReference: TypeReference[T]): Option[T] = {
    try {
      Option(mapper.readerFor(typeReference).readValue(body))
    } catch {
      case e: Exception =>
        Logger.error("JacksonJsonParse", e)
        None
    }
  }

  def parseOptList[T](body: String)(
    implicit typeReference: TypeReference[List[T]]): Option[List[T]] = {
    var data: List[T] = List()
    try {
      data = mapper.readerFor(typeReference).readValue(body)
    } catch {
      case e: Exception => Logger.error("JacksonJsonParse", e)
    }
    Option(data)
  }

  /**
    * Copy from serialization
    */
  def write[A <: AnyRef](a: A)(implicit formats: Formats): String =
    Serialization.write(a)(formats)

  def write[A <: AnyRef, W <: Writer](a: A, out: W)(
    implicit formats: Formats): W = Serialization.write(a, out)(formats)

  def write[A <: AnyRef](a: A, out: OutputStream)(
    implicit formats: Formats): Unit = Serialization.write(a, out)(formats)

  def writePretty[A <: AnyRef](a: A)(implicit formats: Formats): String =
    Serialization.writePretty(a)(formats)

  def writePretty[A <: AnyRef, W <: Writer](a: A, out: W)(
    implicit formats: Formats): W = Serialization.writePretty(a, out)(formats)

  def read[A](json: JsonInput)(implicit formats: Formats, mf: Manifest[A]): A =
    Serialization.read(json)(formats, mf)

  def read[A](in: Reader)(implicit formats: Formats, mf: Manifest[A]): A =
    Serialization.read(in)(formats, mf)
}
