package nephtys.dualframe.cqrs.client

import angulate2.std.{@@, Component}

/**
  * Created by nephtys on 12/6/16.
  */
@Component(
  selector = "login-control",
  template =
    """<div >
       <a *ngIf="!loggedIn" href="/login/google">
       <div><button type="button" class="btn btn-primary"
      >Login with Google</button>
    </div>
    </a>
     <div *ngIf="loggedIn"><button type="button" class="btn btn-danger"
       (click)="logoutClicked()">Logout</button></div>
     </div>
    """
)
class LoginControlComponent(val tokenService: TokenService) {

  var loggedIn = false

  tokenService.hasToken.subscribe(b => loggedIn = b)

  def logoutClicked() : Unit = {
    tokenService.clearToken()
    println("Clicked Logout Button!")
  }

}
