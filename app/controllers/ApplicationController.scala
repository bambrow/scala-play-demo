package controllers

import javax.inject.Inject
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

class ApplicationController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def login = Action { implicit request =>
    Ok(views.html.login("User Login"))
  }

  def doLogin = Action { implicit request =>
    val loginForm = Form(
      tuple(
        "username" -> text,
        "password" -> text(minLength = 8)
      )
    )
    loginForm.bindFromRequest().fold(
      errorForm => Ok(errorForm.errors.toString()),
      tupleData => {
        val (username, password) = tupleData
        Ok(username + ":" + password)
      }
    )
  }

}
