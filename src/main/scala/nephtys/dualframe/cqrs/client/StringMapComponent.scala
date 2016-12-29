package nephtys.dualframe.cqrs.client

import angulate2.core.{EventEmitter, OnChangesJS}
import angulate2.core.OnChanges.SimpleChanges
import angulate2.std._

import scala.scalajs.js
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
  |    <input type="text" class="form-control" [name]="header" id="header" [(ngModel)]="values[i]" (keyup)="onTextChange(i)"/>
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
class StringMapComponent extends OnChangesJS {


  @Output
  val mapChange = new EventEmitter[Seq[(String, String)]]()

  @Input
  var input : Seq[(String, String)] = Map("key A" -> "value a", "key B" -> "value b").toSeq

  var cached : Seq[(String, String)] = input

  var headers: Array[String] = input.map(_._1).toJSArray
  var values: Array[String] = input.map(_._2).toJSArray


  def onTextChange(index : Int) : Unit = {
      if (cached.toMap[String,String].apply(headers(index)) != values(index)) {
        mapChanged(headers.zip(values))
      } else {
        println(s"nothing changed at index $index (why was this event even triggered???)")
      }
  }


    def mapChanged(map : Seq[(String, String)]) : Unit = {
      cached = map
      println(s"Map Changed to $map")
      mapChange.emit(map)
    }

  override def ngOnChanges(changes: SimpleChanges): Unit = {
    cached = input
    headers = input.map(_._1).toJSArray
    values = input.map(_._2).toJSArray
  }
}
