package nephtys.dualframe.cqrs.client

import angulate2.std._
import nephtys.dualframe.cqrs.client.httphelper.{HttpResult, HttpResults}
import nephtys.dualframe.cqrs.client.httphelper.HttpResults._
import angulate2.ext._
import org.scalajs.dom.ext.Ajax
import rxscalajs.Observable
import rxscalajs.subjects.BehaviorSubject

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by nephtys on 12/6/16.
  */
@Injectable
class HttpService(tokenService: TokenService) {

  private val _innnerOnline = BehaviorSubject[Boolean](true)
  val online: Observable[Boolean] = _innnerOnline.map(a => a)

  private val ETAG = "ETag"

  type EndpointRoot = String
  def post(url : String, json : String, endpointRoot : Option[EndpointRoot]) : Future[HttpResult] = Ajax.post(endpointRoot.map(_ +"/").getOrElse("") + url, json, timeout = timeoutMs).map(result => {
    val r : HttpResults.ResultCode = HttpResults.fromStatusCode(result.status)
    var e : Option[ETag] = None
    r match {
      case NotFound => {
        println("Http POST resource was not found / returned 404")
        _innnerOnline.next(false)
      }
      case NotAuthenticated => {
        println("ERROR! User is not authenticated!")
        tokenService.unauthenticatedHttpReturn()
        _innnerOnline.next(false)
      }
      case CachedLocally => {
        println("Return of POST was cached locally, no changes")
        _innnerOnline.next(true)
      }
      case FreshSuccess => {
        println(s"Successful HTTP Post return")
        _innnerOnline.next(true)
      }
      case UnknownResultCode => {
        println("received weird result code from http: " +result.status)
      }
    }
    HttpResult(r, result.responseText, e)
  })
  def get(url : String, endpointRoot : Option[EndpointRoot] = None) : Future[HttpResult] = {
    Ajax.get(endpointRoot.map(_ +"/").getOrElse("") + url, timeout = timeoutMs).map(result => {
      val r : HttpResults.ResultCode = HttpResults.fromStatusCode(result.status)
      var e : Option[ETag] = None
      r match {
        case NotFound => {
          println("Http resource was not found / returned 404")
          _innnerOnline.next(false)
        }
        case NotAuthenticated => {
          println("ERROR! User is not authenticated!")
          tokenService.unauthenticatedHttpReturn()
          _innnerOnline.next(false)
        }
        case CachedLocally => {
          println("Return of GET was cached locally, no changes")
          _innnerOnline.next(true)
        }
        case FreshSuccess => {
          val etag : ETag = ETag(result.getResponseHeader(ETAG))
          println(s"Successful HTTP return with etag = $etag")
          e = Some(etag)
          endpointRoot.foreach(ep => setETag(ep, etag))
          _innnerOnline.next(true)
        }
        case UnknownResultCode => {
          println("received weird result code from http: " +result.status)
        }
      }
      HttpResult(r, result.responseText, e)
    })
  }



    val timeoutMs : Int = 5000

      val authenticatedHeaderName : String = "Authorization"
      val modifiedETagHeader : String = "If-None-Match"

      protected def buildHeaders(endpointRoot : Option[EndpointRoot])(implicit tokenService: TokenService) : Map[String, String] = {
        endpointRoot.flatMap(root => getETag(root)).map[Map[String,String]](etag => Map(modifiedETagHeader -> etag.content)).getOrElse(Map.empty[String, String]) ++ Map(authenticatedHeaderName -> tokenService.currentTokenWithBearer) + CSRF.xforwardheader + CSRF.xrequestheader
      }


      def getETag(endpontroot : EndpointRoot) : Option[ETag] = currentETags.get(endpontroot)
      private val currentETags : mutable.Map[EndpointRoot, ETag] = mutable.Map.empty[EndpointRoot, ETag]
      def setETag(endpontroot : EndpointRoot, etag : ETag) : Option[ETag] = currentETags.put(endpontroot, etag)
      def deleteETag(endpointRoot: EndpointRoot) : Option[ETag] = currentETags.remove(endpointRoot)

      def currentHost : String = org.scalajs.dom.window.location.host
      def currentProtocol : String = org.scalajs.dom.window.location.protocol

      protected object CSRF {
        def calculateCurrentOrigin : String = currentProtocol +"//"+currentHost

        protected def XForwardedHostHeader = """X-Forwarded-Host"""

        protected def OriginHeader = """Origin""" //implicitly filled by browser

        protected def XRequestedWithHeader = """X-Requested-With"""

        protected def XRequestedWithValue = """XMLHttpRequest"""

        def xforwardheader : (String, String) = XForwardedHostHeader -> calculateCurrentOrigin
        def xrequestheader  : (String, String) = XRequestedWithHeader -> XRequestedWithValue
      }
}
