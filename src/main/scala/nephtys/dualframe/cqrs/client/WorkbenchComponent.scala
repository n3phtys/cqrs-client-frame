package nephtys.dualframe.cqrs.client

import angulate2.std.Component

/**
  * Created by nephtys on 12/6/16.
  */
@Component(
  selector = "my-workbench",
  template =
    """<div class="container"><h1>Workbench Angulate2</h1>
      |<login-area></login-area>
      |<dot-control></dot-control>
      |
      |<string-map></string-map>
      |</div>
      |
    """.stripMargin
)
class WorkbenchComponent {

  //TODO: tab like pre choice for different protocols
}
