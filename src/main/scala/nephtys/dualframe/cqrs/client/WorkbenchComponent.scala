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
      |<string-list></string-list>
      |<meta-control-component></meta-control-component>
      |
      |<login-area></login-area>
      |<dot-control></dot-control>
      |
      |<string-map></string-map>
      |
      |<remark-component></remark-component>
      |</div>
      |
    """.stripMargin
)
class WorkbenchComponent {

  //TODO: tab like pre choice for different protocols
}
