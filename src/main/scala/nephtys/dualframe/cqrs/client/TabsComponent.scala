package nephtys.dualframe.cqrs.client

import angulate2.core.{AfterContentInit, EventEmitter}
import angulate2.std.{@@@, Component, Output}

import scala.scalajs.js

/**
  * Created by Christopher on 05.01.2017.
  */
@Component(
  selector = "tab-pane",
  template =
    """ <ul>
      |  <li *ngFor="let tab of tabs">
      |    <a href="#" (click)="selectTab(tab)">{{tab.tabTitle}}</a>
      |  </li>
      |</ul>
      |<ng-content></ng-content>
    """.stripMargin,
  styles = @@@(
    """
      |
    """.stripMargin)
)
class TabsComponent  {

  var tabs : js.Array[TabComponent] = js.Array()

  @Output
  var selected = new EventEmitter[TabComponent]()



  def selectTab(tabComponent: TabComponent) : Unit = {
    this.tabs.foreach(_.selected = false)
    tabComponent.selected = true
    this.selected.emit(tabComponent)
  }

  def addTab(tabComponent: TabComponent) : Unit = {
    if(this.tabs.length > 0) {
      tabComponent.selected = true
    }
    val t = this.tabs.push(tabComponent)
  }

}
