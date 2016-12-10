package nephtys.dualframe.cqrs.client

import angulate2.platformBrowser.BrowserModule
import angulate2.std.{@@, NgModule}

/**
  * Created by nephtys on 12/6/16.
  */
@NgModule(
  imports = @@[BrowserModule],
  providers = @@[TokenService, HttpService],
  declarations = @@[WorkbenchComponent, DotComponent, LoginComponent, LoginControlComponent, LoginInfoComponent]
  //bootstrap = @@[WorkbenchComponent]
  ,exports = @@[LoginComponent]
)
class ClientFrameModule {

}
