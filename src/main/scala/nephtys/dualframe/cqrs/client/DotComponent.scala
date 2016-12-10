package nephtys.dualframe.cqrs.client

import angulate2.std._

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js

/**
  * Created by nephtys on 12/10/16.
  */
@Component(
  selector = "dot-control",
  template =
    """
      |<div><h2>This is a dot control</h2>
      |<label for="circlecontainer" >{{name}}:</label>
      |<div id="circlecontainer" *ngFor="let instance of values; let i = index">
      |<div id="circle" *ngIf="instance" [style.background]="color" (click)="clickedCircle(i)">{{i+1}}</div>
      |<div id="circle" *ngIf="! instance" (click)="clickedCircle(i)">{{i+1}}</div>
      |</div>
      |</div>""".stripMargin,
  styles = @@@(    """
      |label {
      |font-weight: bold;
      |font-size: 125%;
      |}
      |
      |#circlecontainer {
      |display: inline-block;
      |border: 1px solid black;
      |}
      |#circle {
      |display: inline-block;
      |background : grey;
      |//background-image: -webkit-radial-gradient(45px 45px, circle cover, yellow, orange);
      |color:white;
      |text-align:center;
      | border-style: 2px solid black;
      |margin: 5px 10px 5px 10px;
      |	border-radius: 50%;
      |	width: 25px;
      |	height: 25px;
      |}
    """.stripMargin)
)
class DotComponent{

  @Input
  var name : String = "Value"

  @Input
  var color : String = "blue"

  @Input
  var tooltip : String = "some tooltip"

  @Input
  var max : Int = 5
  @Input
  var min : Int = 0
  @Input
  var value : Int = 2



  var values : js.Array[Boolean] = (1 to max).map(i => {
    if (i > value) false else true
  }).toJSArray


  def clickedCircle(index : Int) : Unit = {
    println(s"clicked circle index = $index")
    (0 until index).foreach(i => values(i) = true )
    (index+1 until values.length).foreach(i => values(i) = false )
    if(value == index+1) {
      //unset
      values(index) = false
      value = index
    } else {
      //set
      values(index) = true
      value = index+1
    }

    println(s"value is now $value")
  }


  /*
  @Output
  val valueSelected = new EventEmitter[Int]()
*/

}
