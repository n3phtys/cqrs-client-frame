package nephtys.dualframe.cqrs.client

import java.util.concurrent.TimeUnit

import angulate2.std.Injectable
import nephtys.dualframe.cqrs.client.oidchelper.OIDC
import nephtys.dualframe.cqrs.client.oidchelper.OIDC.IdentityToken
import org.nephtys.loom.generic.protocol.InternalStructures.Email
import rxscalajs.Observable
import rxscalajs.subjects.BehaviorSubject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.FiniteDuration

/**
  * Created by nephtys on 12/6/16.
  */
@Injectable
class TokenService {
  def currentTokenWithBearer: String = tokenWithBearer

  var manualSetEmail : Option[String] = None

  private val localStorageManualEmailKey = """MANUALLY_ENTERED_EMAIL"""
  private val localStorageTokenKey = """GOOGLE_IDENTITY_TOKEN"""
  private val repeatScanInterval : FiniteDuration = FiniteDuration(2, TimeUnit.MINUTES)

  private val timer = Observable.interval(repeatScanInterval)

  timer.subscribe(i => {
    println(s"tokenservice timer ticked $i")
    _innerCurrentToken.next(scanLocalStorage)

    manualSetEmail.foreach(s => if(isEmail(s)) {
      org.scalajs.dom.window.localStorage.setItem(localStorageManualEmailKey, s)
    })
  })

  def isEmail(str : String) : Boolean = {
    """(?=[^\s]+)(?=(\w+)@([\w\.]+))""".r.findFirstIn(str).isDefined
  }

  private def scanLocalStorage : String = {

    manualSetEmail = Some(org.scalajs.dom.window.localStorage.getItem(localStorageManualEmailKey)).filter(a => a != null && a.nonEmpty && a.contains('@'))

    val extr = org.scalajs.dom.window.localStorage.getItem(localStorageTokenKey)
    if (extr != null) {
      extr
    } else {
      ""
    }
  }

  def setToken(newtoken : String)  : Unit = {
    if(newtoken.isEmpty) {
      org.scalajs.dom.window.localStorage.removeItem(localStorageTokenKey)
    } else {
      org.scalajs.dom.window.localStorage.setItem(localStorageTokenKey, newtoken)
    }
  }

  def clearToken() : Unit = setToken("")

  def unauthenticatedHttpReturn() : Unit = ???

  private val _innerCurrentToken : BehaviorSubject[String] = BehaviorSubject(scanLocalStorage)

  val currentToken : Observable[String] = _innerCurrentToken.distinct

  val currentIdentityToken : Observable[Option[IdentityToken]] = currentToken.map(s => OIDC.extractToken(s).toOption)

  val currentEmail : Observable[String] = currentIdentityToken.map(i => i.map(_.email).getOrElse(">no email set<"))

  val currentTokenWithBearerInFront: Observable[String] = currentToken.map(s => "Bearer " + s)

  private var internalEmailStorage : Option[Email] = None
  currentEmail.subscribe(s => if(s.nonEmpty) {
    internalEmailStorage = Some(Email(s))
  })
  def getCurrentEmail : Email = {
    internalEmailStorage.getOrElse({
      if (manualSetEmail.isDefined) {
        Email(manualSetEmail.get)
      } else {
        val s = org.scalajs.dom.window.prompt("You do not have an OpenID Connect Token stored. Please manually enter your email address to let us determine which characters are yours.")
        manualSetEmail = Some(s)
        Email(s)
      }
    })
  }


  private var tokenWithBearer : String = ""
  currentTokenWithBearerInFront.subscribe(s => tokenWithBearer = s)

  val hasToken: Observable[Boolean] = currentIdentityToken.map(s => s.isDefined)

  val expirationTimestampMs : Observable[Long] = currentIdentityToken.map(i => i.map(_.exp_long).getOrElse(0L) * 1000L)

  println("created token service")

  currentTokenWithBearerInFront.subscribe(i => println(i))
}
