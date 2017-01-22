package nephtys.dualframe.cqrs.client

import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.std.{@@, NgModule}

/**
  * Created by Christopher on 08.01.2017.
  */
@NgModule(
  imports = @@[BrowserModule, FormsModule],
  providers = @@[TokenService, ServerURLService],
  declarations = @@[GooglePlusLoginComponent, ServerURLComponent, LoginComponent, LoginControlComponent, LoginInfoComponent]
  ,exports = @@[LoginComponent, ServerURLComponent, GooglePlusLoginComponent, LoginInfoComponent]
)
class LoginModule {

}
