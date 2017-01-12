package nephtys.dualframe.cqrs.client

import angulate2.std.Component

/**
  * Created by nephtys on 12/6/16.
  */
@Component(
  selector = "my-workbench",
  template =
    """<div>
      |<h1>Workbench Angulate2</h1>
      |
      |<google-plus-login></google-plus-login>
      |</div>
      |""".stripMargin
)
class WorkbenchComponent {


  private val temple = """
                         |<!-- tab-pane >
                         |  <tab-control [title]="'Title 1'">
                         |    Content 1
                         |  </tab-control>
                         |  <tab-control title="Title 2">
                         |    Content 2
                         |  </tab-control>
                         |  <tab-control [title]="'Title 3'">
                         |    Content 3
                         |  </tab-control>
                         |</tab-pane>
                         |
                         |<dotted-string-list (seqChange)="printDotChange($event)" title="Dotted StringPair"></dotted-string-list>
                         |
                         |
                         |
                         |<string-list (seqChange)="printChange($event)"></string-list>
                         |<meta-control-component></meta-control-component>
                         |
                         |<login-area></login-area>
                         |<dot-control (valueSelected)="printInt($event)"></dot-control>
                         |
                         |<string-map (mapChange)="printKeyMap($event)"></string-map>
                         |
                         |
                         |<remark-component></remark-component -->
                         |
                         |</div>
                         |
                         |
                         |
    """.stripMargin


  def printInt(i : Int) : Unit = {
    println(s"EventEmitter: Dot Control changed to $i")
  }

  def printChange(i : Seq[String]) : Unit = {
    println(s"EventEmitter: String List changed to $i")
  }

  def printDotChange(i : Seq[DottedStringPair]) : Unit = {
    println(s"EventEmitter: Dotted List changed to $i")
  }

  def printKeyMap(map : Map[String, String]) : Unit = {
    println(s"EventEmitter: stringmap changed to $map")
  }
}
