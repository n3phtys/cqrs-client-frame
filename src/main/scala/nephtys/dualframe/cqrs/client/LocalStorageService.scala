package nephtys.dualframe.cqrs.client

import angulate2.std._

/**
  * Created by nephtys on 12/16/16.
  */
@Injectable
class LocalStorageService {

  def get(key : String) : Option[String] = ???
  def remove(key : String) : Unit = ???
  def set(key : String, value : String) : Unit = ???

}
