package nephtys.dualframe.cqrs.client

import angulate2.std.Component

/**
  * Created by nephtys on 12/6/16.
  */
@Component(
  selector = "my-workbench",
  template =
    """<div class="container"><h1>Workbench Angulate2</h1>
      |
      |
      |<dotted-string-list></dotted-string-list>
      |<string-list (seqChange)="printChange($event)"></string-list>
      |<meta-control-component></meta-control-component>
      |
      |<login-area></login-area>
      |<dot-control (valueSelected)="printInt($event)"></dot-control>
      |
      |<string-map (mapChange)="printKeyMap($event)"></string-map>
      |
      |<remark-component></remark-component>
      |</div>
      |
    """.stripMargin
)
class WorkbenchComponent {

  //TODO: tab like pre choice for different protocols


  def printInt(i : Int) : Unit = {
    println(s"EventEmitter: Dot Control changed to $i")
  }

  def printChange(i : Seq[String]) : Unit = {
    println(s"EventEmitter: String List changed to $i")
  }

  def printKeyMap(map : Map[String, String]) : Unit = {
    println(s"EventEmitter: stringmap changed to $map")
  }
}
