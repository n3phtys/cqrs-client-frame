package nephtys.dualframe.cqrs.client

import angulate2.platformBrowser.BrowserModule
import angulate2.std.{@@, NgModule}

/**
  * Created by nephtys on 12/8/16.
  */
@NgModule(
  imports = @@[BrowserModule, ClientFrameModule, LoginModule],
  //providers = @@[TokenService, HttpService],
  //declarations = @@[WorkbenchComponent,LoginComponent, LoginControlComponent, LoginInfoComponent],
  bootstrap = @@[WorkbenchComponent]
)
class AppModule {

}
