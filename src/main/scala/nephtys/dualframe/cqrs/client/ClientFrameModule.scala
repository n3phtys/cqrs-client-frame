package nephtys.dualframe.cqrs.client

import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.std.{@@, NgModule}

/**
  * Created by nephtys on 12/6/16.
  */
@NgModule(
  imports = @@[BrowserModule, FormsModule, LoginModule],
  providers = @@[HttpService, LocalStorageService, IDBPersistenceService],
  declarations = @@[WorkbenchComponent, CollapsedWellComponent, TabComponent, TabsComponent, ControlComponent,  RemarkComponent, DottedStringPairComponent, StringListComponent, DotComponent, StringMapComponent]
  //bootstrap = @@[WorkbenchComponent]
  ,exports = @@[RemarkComponent, CollapsedWellComponent, DottedStringPairComponent, DotComponent, StringMapComponent, StringListComponent, ControlComponent]
)
class ClientFrameModule {

}
