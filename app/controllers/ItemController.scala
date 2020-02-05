package controllers

import javax.inject.{Inject, Singleton}
import models.SingleItem
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

import scala.collection.mutable.ListBuffer

@Singleton
class ItemController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val itemList: ListBuffer[SingleItem] = ListBuffer.empty

  def items = Action { implicit request =>
    Ok(views.html.items(itemList.toSeq))
  }

  def addItem = Action { implicit request =>
    val addForm = Form(
      mapping(
        "item" -> nonEmptyText
      )(SingleItem.apply)(SingleItem.unapply)
    )
    addForm.bindFromRequest().fold(
      errorForm => BadRequest(errorForm.errors.toString()),
      singleItem => {
        itemList.append(singleItem)
        Redirect(routes.ItemController.items())
      }
    )
  }

  def deleteItem(id: Int) = Action { implicit request =>
    itemList.remove(id)
    Redirect(routes.ItemController.items())
  }

}
