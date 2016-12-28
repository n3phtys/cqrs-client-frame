package nephtys.dualframe.cqrs.client

import angulate2.std._

/**
  * Created by nephtys on 12/16/16.
  */
@Injectable
class LocalStorageService {

  private def localStorage = org.scalajs.dom.window.localStorage

  def get(key : String) : Option[String] = {
    val s : String = localStorage.getItem(key)
    if (s != null && s.nonEmpty) {
      Some(s)
    } else {
      None
    }
  }
  def remove(key : String) : Unit = localStorage.removeItem(key)
  def set(key : String, value : String) : Unit = localStorage.setItem(key, value)

}
