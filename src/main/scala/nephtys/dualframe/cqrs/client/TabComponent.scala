package nephtys.dualframe.cqrs.client

import angulate2.std.{@@@, Component, Input, OnInit}

import scala.scalajs.js.annotation.JSExport

/**
  * Created by Christopher on 05.01.2017.
  */
@Component(
  selector = "tab-control",
  template =
    """ <div [hidden]="!active" class="pane">
      |  <ng-content></ng-content>
      |</div>
    """.stripMargin,
  styles = @@@(
    """
      |.pane{
      |      padding: 1em;
      |    }
    """.stripMargin)
)
class TabComponent(tabsComponent: TabsComponent) extends OnInit {

  @Input
  var title : String = "title"

  @Input
  var active : Boolean = true

  override def ngOnInit(): Unit = {
    this.tabsComponent.addTab(this)
  }
}
