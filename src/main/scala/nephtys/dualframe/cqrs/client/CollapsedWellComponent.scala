package nephtys.dualframe.cqrs.client

import angulate2.std._

import scala.util.Random

/**
  * Created by Christopher on 08.01.2017.
  */
@Component(
  selector = "collapsed-well",
  template = (
    """
      |<div class="panel-group">
      |<div class="panel panel-default">
      |  <div class="panel-heading">
      |  <a data-toggle="collapse"  href="#collapse-{{id}}">
      |  <h4 >
      |{{title}}
      |</h4></a>
      |</div>
      |  <div id="collapse-{{id}}" class="panel-body panel-collapse collapse">
      |  <ng-content></ng-content>
      |  </div>
      |</div>
      |  </div>
      |
      |
      |""").stripMargin
)
class CollapsedWellComponent {

  @Input
  var title : String = "some title"


  var id : String = Random.alphanumeric.take(5).mkString

  //TODO: utilize this:
  //@Input
  //var open : Boolean = false
}
