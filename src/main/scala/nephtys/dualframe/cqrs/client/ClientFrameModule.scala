package nephtys.dualframe.cqrs.client

import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.std.{@@, NgModule}

/**
  * Created by nephtys on 12/6/16.
  */
@NgModule(
  imports = @@[BrowserModule, FormsModule],
  providers = @@[TokenService, HttpService, LocalStorageService, IDBPersistenceService],
  declarations = @@[WorkbenchComponent, ControlComponent,  DottedStringPairComponent, StringListComponent, DotComponent, StringMapComponent, LoginComponent, LoginControlComponent, LoginInfoComponent]
  //bootstrap = @@[WorkbenchComponent]
  ,exports = @@[LoginComponent, DottedStringPairComponent, DotComponent, StringMapComponent, StringListComponent, ControlComponent]
)
class ClientFrameModule {

}
