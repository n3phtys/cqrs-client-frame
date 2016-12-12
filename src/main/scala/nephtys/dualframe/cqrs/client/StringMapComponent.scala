package nephtys.dualframe.cqrs.client

import angulate2.std._

import scala.scalajs.js.Array
import scala.scalajs.js.JSConverters._

/**
  * Created by nephtys on 12/12/16.
  */
@Component(
selector = "string-map",
template =
""" <div>
  |StringMap component:
  |<form class="form-inline"  >
  |  <div class="form-group" *ngFor="let header of headers; let i = index" >
  |    <label for="header">{{header}}</label>
  |    <input type="text" class="form-control" [name]="header" id="header" [ngModel]="values[i]" (keypress)="onTextChange(i)"/>
  |  </div>
  |</form>
  |
  |</div>
""".stripMargin,
  styles = @@@(
    """
      |.form-inline > * {
      |   margin:15px 15px;
      |}
    """.stripMargin)
)
class StringMapComponent {

  var static = "This is a static value" //[ngModel]="static"

  @Input
  var input : Map[String, String] = Map("key A" -> "value a", "key B" -> "value b")

  var headers: Array[String] = input.keys.toJSArray
  var values: Array[String] = headers.map(h => input(h))


  def onTextChange(index : Int) : Unit = {
    println(s"onTextChange index = $index")
      if (!input(headers(index)).equals(values(index))) {
        mapChanged(headers.zip(values).toMap[String, String])
      } else {
        println(s"nothing changed at index $index")
      }
  }


    def mapChanged(map : Map[String, String]) : Unit = {
      println(s"Map Changed to $map")
      //TODO: use output
    }


}
