package models

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

case class SingleItem(item: String, date: String)
object SingleItem {
  def apply(item: String): SingleItem = {
    val now: Date = Calendar.getInstance().getTime
    val format: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    new SingleItem(item, format.format(now))
  }
  def unapply(singleItem: SingleItem): Option[String] = Option(singleItem.item)
}