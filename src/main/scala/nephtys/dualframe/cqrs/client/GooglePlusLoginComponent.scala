package nephtys.dualframe.cqrs.client

import angulate2.std.Component

import scala.scalajs.js

/**
  * Created by Christopher on 07.01.2017.
  */
@Component(
  selector = "google-plus-login",
  template =
    """<div class="well well-sm">
      |<div >
      |<div  *ngIf="!loggedIn" class="g-signin2" (data-onsuccess)="onSignIn"></div>
      |<div  *ngIf="loggedIn" class="g-signout2" (data-onsuccess)="logoutClicked()"></div>
      |
      |<login-info>
      |
      |</login-info>
      |</div>""".stripMargin
)
class GooglePlusLoginComponent(val tokenService: TokenService) {

  var loggedIn = false

  tokenService.hasToken.subscribe(b => loggedIn = b)


  def onSignIn(googleUser : js.Object) : Unit = {
    //javascript code:
    /*
    var profile = googleUser.getBasicProfile();
    println('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    println('Name: ' + profile.getName());
    println('Image URL: ' + profile.getImageUrl());
    println('Email: ' + profile.getEmail());

    */
  }

  def logoutClicked() : Unit = {

  }

}
