package nephtys.dualframe.cqrs.client

import angulate2.std.Injectable
import rxscalajs.subjects.BehaviorSubject

/**
  * Created by Christopher on 22.01.2017.
  */
@Injectable
class ServerURLService(localStorageService: LocalStorageService) {

  println("ServerURLService initialized")

  private val localStorageKey : String = "TargetSyncServerURL"
  private var Url : Option[String] = localStorageService.get(localStorageKey).filter(_.nonEmpty)
  val urlRx : BehaviorSubject[Option[String]] = BehaviorSubject[Option[String]](Url)
  def getUrl : String = Url.getOrElse("")
  def getUrlOpt: Option[String] = Url
  def isSetUrl : Boolean = Url.nonEmpty && Url.get.nonEmpty
  def setUrl(s : String) : Unit = {
    if(s.isEmpty) {
      localStorageService.remove(localStorageKey)
      Url = None
      urlRx.next(Url)
    } else {
      localStorageService.set(localStorageKey, s)
      Url = Some(s)
      urlRx.next(Url)
    }
  }
}
