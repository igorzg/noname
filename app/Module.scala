import com.google.inject.AbstractModule
import play.api.{Environment, Configuration}
import helpers.JsonHelper.mapper
import com.fasterxml.jackson.databind.SerializationFeature

/**
  * @author igorzg on 20.01.18.
  * @since 1.0
  */
class Module(environment: Environment, configuration: Configuration)
    extends AbstractModule {

  override def configure() = {
    if (environment.mode.equals(play.api.Mode.Dev)) {
      mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }
  }

}
