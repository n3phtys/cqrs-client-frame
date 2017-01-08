package nephtys.dualframe.cqrs.client

import angulate2.core.OnChanges.SimpleChanges
import angulate2.std._
import scala.scalajs.js.timers._

import scala.scalajs.js

/**
  * Created by Christopher on 07.01.2017.
  */
@Component(
  selector = "google-plus-login",
  template =
    """<div >
      |
      |<div class="margineddiv" [hidden]="loggedIn"  >
      |<div id="googleLoginButtonId"></div>
      |</div>
      |
      |<div *ngIf="loggedIn">
      |<login-info></login-info>
      |</div>
      |
      |</div>""".stripMargin ,
  styles = @@@(
    """
      |.margineddiv {
      |   margin:10px 10px 10px 10px;
      |}
    """.stripMargin)
)
class GooglePlusLoginComponent(val tokenService: TokenService) extends OnInit{

  var loggedIn : Boolean = false



  def onSignIn(googleUser : nephtys.gapi.auth2.GoogleUser) : Unit = {

    val profile = googleUser.getBasicProfile()
    println("ID: " + profile.getId()) // Do not send to your backend! Use an ID token instead.
    println("Name: " + profile.getName())
    println("Image URL: " + profile.getImageUrl())
    println("Email: " + profile.getEmail())

  }



  private val onSignInFunc : js.Function1[nephtys.gapi.auth2.GoogleUser, Unit] = (googleUser : nephtys.gapi.auth2.GoogleUser) => onSignIn(googleUser)

  private val timeoutMs : Long = 500

  private val gapiRenderOptions = js.Dynamic.literal(
    onSuccess =  onSignInFunc,
    scope = "profile",
    theme = "dark"
  )

  println(s"Created at moment ${System.currentTimeMillis()}")

  override def ngOnInit(): Unit = {
  val timeouthandler = setTimeout(timeoutMs) {
    println(s"This works so far with $timeoutMs at ${System.currentTimeMillis()}")
      nephtys.gapi.auth2.SignIn2.render("googleLoginButtonId", gapiRenderOptions)
  }
  }
}
