package nephtys.dualframe.cqrs.client

import angulate2.std.{@@@, Component, Input, OnInit}

import scala.scalajs.js.annotation.JSExport

/**
  * Created by Christopher on 05.01.2017.
  */
@Component(
  selector = "tab-control",
  template =
    """ <div [hidden]="!selected">
      |  <ng-content></ng-content>
      |</div>
    """.stripMargin,
  styles = @@@(
    """
      |
    """.stripMargin)
)
class TabComponent(tabsComponent: TabsComponent) extends OnInit {

  @Input
  var title : String = "title"

  @Input
  var selected : Boolean = true

  override def ngOnInit(): Unit = {
    this.tabsComponent.addTab(this)
  }
}
