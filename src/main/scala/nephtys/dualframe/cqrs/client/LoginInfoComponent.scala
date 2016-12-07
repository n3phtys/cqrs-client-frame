package nephtys.dualframe.cqrs.client

import angulate2.std.{@@, Component}
import rxjs.Observable

import scala.scalajs.js.Date

/**
  * Created by nephtys on 12/6/16.
  */
@Component(
  selector = "login-info",
  template =
    """<div *ngIf="shown">
       <b>Here are informations about your currently used OAuth2 Token:</b>
      <ul>
       <li>Logged in as {{email}}</li>
       <li>Token is running out at {{date}}</li>
      </ul>
      </div>
    """,
  providers = @@[TokenService]
)
class LoginInfoComponent(val tokenService: TokenService) {
  var date : String = "N/A"
  var email : String = "N/A"
  var shown : Boolean = false

  tokenService.expirationTimestampMs.map(l => new Date(l).toISOString()).subscribe(s => date = s)

  tokenService.currentEmail.subscribe(e => email = e)

  tokenService.hasToken.subscribe(b => shown = b)
}
