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
    """
      |<div class="bordered">
      |<ul >
      |  <li *ngFor="let tab of tabs">
      |    <a href="#" (click)="selectTab(tab)">{{tab.title}}</a>
      |  </li>
      |</ul>
      |<ng-content></ng-content>
      |</div>
    """.stripMargin,
  styles = @@@(
    """
      |
      |.bordered {
      |    border-left: 6px solid red;
      |    background-color: lightgrey;
      |}
      |
      |ul {
      |    list-style-type: none;
      |    margin: 0;
      |    padding: 0;
      |    overflow: hidden;
      |    background-color: #333;
      |}
      |li {
      |float: left;
      |}
      |li a {
      |display : block;
      |color : white;
      |text-align : center;
      |padding : 14px 16px;
      |text-decoration: none;
      |}
      |
      |li a:hover {
      |background-color: #111;
      |}
      |
    """.stripMargin)
)
class TabsComponent  {

  var tabs : js.Array[TabComponent] = js.Array()

  @Output
  var selected = new EventEmitter[TabComponent]()



  def selectTab(tabComponent: TabComponent) : Unit = {
    this.tabs.foreach(_.active = false)
    tabComponent.active = true
    this.selected.emit(tabComponent)
  }

  def addTab(tabComponent: TabComponent) : Unit = {
    this.tabs.foreach(_.active = false)
    tabComponent.active = true
    val t = this.tabs.push(tabComponent)
  }

}
