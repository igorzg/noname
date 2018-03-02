package controllers

import helpers.JsonHelper._
import javax.inject.{Inject, Singleton}
import models.dao.CountriesDao
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

/**
  * @author igorzg on 02.03.18.
  * @since 1.0
  */
@Singleton
class CountriesController @Inject()(cc: ControllerComponents, countriesDao: CountriesDao)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action.async { implicit request =>
    countriesDao.all().map { values =>
      Ok(writeJson(values))
    }
  }
}
