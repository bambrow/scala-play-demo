package controllers

import javax.inject.{Inject, Singleton}
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

@Singleton
class ApplicationController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def login = Action { implicit request =>
    Ok(views.html.login("User Login"))
  }

  def doLogin = Action { implicit request =>
    val loginForm = Form(
      tuple(
        "username" -> text(minLength = 6, maxLength = 100),
        "password" -> text(minLength = 8, maxLength = 100)
      )
    )
    loginForm.bindFromRequest().fold(
      errorForm => BadRequest(errorForm.errors.toString()),
      tupleData => {
        val (username, password) = tupleData
        val hashed = md5(password)
        // Ok(username + ":" + hashed)
        Redirect(routes.HomeController.hello(username, hashed))
      }
    )
  }

  private def md5(str: String): String = {
    import java.security.MessageDigest
    import java.math.BigInteger
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(str.getBytes)
    val bigInt = new BigInteger(1, digest)
    bigInt.toString(16)
  }

}
