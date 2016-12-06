package nephtys.dualframe.cqrs.client.httphelper

/**
  * Created by nephtys on 12/4/16.
  */
object HttpResults {

  case class ETag(content : String) extends AnyVal

  sealed trait ResultCode
  case object CachedLocally extends ResultCode
  case object NotFound extends ResultCode
  case object FreshSuccess extends ResultCode
  case object NotAuthenticated extends ResultCode
  case object UnknownResultCode extends ResultCode

  def fromStatusCode(statusCode : Int) : ResultCode = statusCode match {
    case 404 => NotFound
    case 401 => NotAuthenticated
    case 304 => CachedLocally
    case 200 => FreshSuccess
    case _ => UnknownResultCode
  }
}
