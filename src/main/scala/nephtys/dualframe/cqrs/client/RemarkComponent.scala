package nephtys.dualframe.cqrs.client

import angulate2.std._

import scala.scalajs.js
import scala.scalajs.js
import scala.scalajs.js.JSConverters._

/**
  * Created by nephtys on 12/17/16.
  */
@Component(
selector = "remark-component",
template =
  """
    |<div>
    |<div class="container" *ngIf="nonEmptyCount > 0 && visible">
    |<ul>
    |<li class="highprio" *ngFor="let s of highPrio">{{s}}</li>
    |</ul>
    |<hr *ngIf="nonEmptyCount === 2">
    |<ul>
    |<li class="lowprio" *ngFor="let s of lowPrio">{{s}}</li>
    |</ul>
    |
    |</div>
    |
    |
    |<div class="buttonDiv">
    |<button (click)="btnClick()" type="button" class="btn btn-info"><span class="glyphicon glyphicon-info-sign"></span></button>
    |</div>
    |</div>
    | """.stripMargin,
styles = @@@(
"""
  |.container {
  |    border: 2px solid red;
  |    border-radius: 10px;
  |}
  |.buttonDiv {
  | margin: 0 auto;
  |}
  |@media only screen and (min-width: 768px) {
  |.container {
  |   border: 3px solid blue;
  |    border-radius: 15px;
  |   width: 10vw;
  |   position: fixed;
  |   right: 1px;
  |   top: 20%;
  |}
  |.buttonDiv {
  |display: block;
  | position: fixed;
  | top: 5%;
  | right: 15px;
  |}
  |}
  |.highprio {
  |font-weight: bold;
  |}
  |.lowprio {
  |
  |}
  |.form-inline > * {
  |   margin:15px 15px;
  |}
""".stripMargin)

)
class RemarkComponent {

  @Input
  var remarksHighPrio : Seq[String] = Seq("You have to do A", "You have to do B before closing chargen")

  var highPrio : js.Array[String] = js.Array()

  @Input
  var remarksLowPrio : Seq[String] = Seq("You have 0 / 0 Experience Points left")

  var lowPrio : js.Array[String] = js.Array()

  var nonEmptyCount : Int = 0

  var visible : Boolean = true

  def btnClick() : Unit = {
    println("button clicked!")
    visible = !visible
  }

  def inputChanged() : Unit = {
    nonEmptyCount = (if(remarksHighPrio.nonEmpty) 1 else 0) + (if(remarksLowPrio.nonEmpty) 1 else 0)
    highPrio = remarksHighPrio.toJSArray
    lowPrio = remarksLowPrio.toJSArray
  }

  inputChanged()
}
