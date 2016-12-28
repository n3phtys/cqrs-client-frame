package nephtys.dualframe.cqrs.client

import angulate2.core.OnChanges.SimpleChanges
import angulate2.core.{EventEmitter, OnChangesJS}
import angulate2.std._

import scala.scalajs.js

/**
  * Created by nephtys on 12/15/16.
  */
@Component(
  selector = "dotted-string-list",
  template =
    """<h2>{{title}}</h2>
      |<div class="form-inline">
      |<div class="form-group form-inline">
      |<label for="textinput">New
      |
      |<select [(ngModel)]="selectedValue" class="form-control" >
      |  <option *ngFor="let c of selectableValues" [ngValue]="c">{{c}}</option>
      |</select>
      |<input class="form-control" type="text" id="textinput" [(ngModel)]="writtenValue">
      |
      |<button type="submit" class="btn btn-success" (click)="add()" [disabled]="! writtenValue"
      |      ><span class="glyphicon glyphicon-plus"></span></button>
      |      </label>
      |</div>
      | </div>
      |<div class="items">
      |<div class="item" *ngFor="let entry of internalValues; let i = index">
      |<button type="button" *ngIf="canRemove" (click)="remove(i)" class="btn btn-danger"
      |      ><span class="glyphicon glyphicon-remove-sign"></span></button>
      |      {{entry.category}}:
      |
      |<dot-control [name]="entry.title" [value]="entry.rating" (valueSelected)="changeRating(i, $event)" ></dot-control>
      |
      | </div>
      |</div>
    """.stripMargin,
  styles = @@@(
    """
      |.items {
      |	display: flex;
      |	flex-wrap: wrap;
      |	margin-left: -10px;
      |	margin-top: -10px;
      |}
      |.items .item {
      |	flex: 1 0 200px;
      |  box-sizing: border-box;
      |  background: silver;
      |  color: #171e42;
      |  padding: 10px;
      |	margin-left: 10px;
      |	margin-top: 10px;
      |}
    """.stripMargin)
)
class DottedStringPairComponent extends OnChangesJS{

  //does not enable edit, just add, remove, and dot changes


  @Input
  var title : String = "This is the Dotted StringPair title"

  @Input
  var selectableValues : js.Array[String] = js.Array("Cat A", "Cat B", "Cat C")

  //@Input
  //var allowOwnCategories : Boolean = false

  @Input
  var input : js.Array[DottedStringPair] = js.Array(
    DottedStringPair("Cat A", "Text A", 3),
    DottedStringPair("Cat B", "Text B", 4)
  )

  @Input
  var canRemove : Boolean = true


  var internalValues : js.Array[DottedStringPair] = js.Array()

  var selectedValue : String = "Cat A"
  var writtenValue : String = ""



  def inputChanged() : Unit = {
    internalValues = js.Array()
    input.foreach(d => internalValues.push(d))
    writtenValue = ""
    selectedValue = selectableValues.headOption.getOrElse("")
  }

  inputChanged()

  def outputEvent(newContent : IndexedSeq[DottedStringPair]) : Unit = {
    seqChange.emit(newContent)
  }

  def changeRating(index : Int, newRating : Int) : Unit = {
    println(s"rating of index $index changed to value $newRating")
    val old = internalValues(index)
    internalValues(index) = DottedStringPair(title = old.title, category = old.category, rating = newRating)
    outputEvent(internalValues.toIndexedSeq)
  }

  def add() : Unit  = {
    println(s"writtenValue = $writtenValue")
    println(s"selectedValue = $selectedValue")

    if (writtenValue.nonEmpty ) {
      internalValues.push(DottedStringPair(selectedValue, writtenValue, 0))
      writtenValue = ""
      outputEvent(internalValues.toIndexedSeq)
    }
  }

  def remove(index : Int) : Unit = {
    if (org.scalajs.dom.window.confirm("Do you really want to remove this item?")) {
      val r = internalValues.splice(index, 1)
      println(s"removed element $r")
      outputEvent(internalValues.toIndexedSeq)
    }
  }

  @Output
  val seqChange = new EventEmitter[IndexedSeq[DottedStringPair]]()

  override def ngOnChanges(changes: SimpleChanges): Unit = inputChanged()
}
