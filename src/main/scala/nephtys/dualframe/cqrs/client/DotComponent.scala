package nephtys.dualframe.cqrs.client

import angulate2.core.{EventEmitter}
import angulate2.core.OnChanges.SimpleChanges
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
      |<div class="fullcontainer">
      |<label for="circlecontainer" >{{name}}:</label>
      |<div id="circlecontainer" >
      |<div  *ngFor="let instance of values; let i = index">
      |<div id="circle" *ngIf="instance" [style.background]="color" (click)="clickedCircle(i)">{{i+1}}</div>
      |<div id="circle" *ngIf="! instance" (click)="clickedCircle(i)">{{i+1}}</div>
      |</div>
      |</div>
      |</div>""".stripMargin,
  styles = @@@(    """
      |label {
      |display: inline-block;
      |width : 90px;
      |font-weight: bold;
      |}
      |
      |.fullcontainer {
      | white-space: nowrap;
      |}
      |
      |#circlecontainer {
      |display: flex;
      |
      |width 150px;
      |border: 1px solid black;
      |}
      |#circle {
      |/*display: inline-block;*/
      |background : grey;
      |//background-image: -webkit-radial-gradient(45px 45px, circle cover, yellow, orange);
      |color:white;
      |text-align:center;
      | border-style: 2px solid black;
      |margin: 5px 5px 5px 5px;
      |	border-radius: 50%;
      |	width: 25px;
      |	height: 25px;
      |}
    """.stripMargin)
)
class DotComponent extends OnChanges {

  @Input
  var name : String = "Value"

  @Input
  var color : String = "blue"

  @Input
  var tooltip : String = "some tooltip"

  @Input
  var allowInconsistentState : Boolean = false

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
    if (index + 1 >= min && index < max && (value != index +1 || value > min)) {
      if(allowInconsistentState) {
        (0 until index).foreach(i => values(i) = true)
        (index + 1 until values.length).foreach(i => values(i) = false)
      }
      if (value == index + 1 && index+1 > min) {
        //unset
        if(allowInconsistentState) {
          values(index) = false
        }
        value = Math.max(min, index)
      } else {
        //set
        if(allowInconsistentState) {
          values(index) = true
        }
        value = Math.max(min, index + 1)
      }
      valueSelected.emit(value)
      println(s"value is now $value")
    }
  }



  @Output
  val valueSelected = new EventEmitter[Int]()

  override def ngOnChanges(changes: SimpleChanges): Unit = {
    values = (1 to max).map(i => {
      if (i > value) false else true
    }).toJSArray
  }
}
