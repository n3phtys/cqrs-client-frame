package nephtys.dualframe.cqrs.client

import angulate2.std.{@@, Component}

/**
  * Created by nephtys on 12/6/16.
  */
@Component(
  selector = "login-area",
  template = "<b>I am a login area</b>",
  providers = @@[TokenService]
)
class LoginComponent(val tokenService: TokenService) {

}
