package nephtys.dualframe.cqrs.client

import angulate2.std._

import scala.scalajs.js

/**
  * Created by nephtys on 12/14/16.
  */
@Component(
  selector = "string-list",
  template =
    """
      |<h3>String List Component</h3>
      |
      | <form class="form-inline">
      |  <div class="form-group">
      |    <label for="newitem">New:</label>
      |    <input  type="text" [(ngModel)]="newStringField" name="newitem" class="form-control" id="newitem">
      |  </div>
      |  <button (click)="addBtnClicked()" [disabled]="newStringField.length <= 2" class="btn btn-success">Add</button>
      |</form>
      |
      | <div class="items">
      |  <div class="item" *ngFor="let item of internalStrings; let i = index">
      |  <button (click)="removeBtnClicked(i)" *ngIf="! internalEditStates[i]"  class="btn btn-danger" ><span class="glyphicon glyphicon-remove"></span></button>
      |  <button (click)="editBtnClicked(i)" *ngIf="! internalEditStates[i]"  class="btn btn-info" ><span class="glyphicon glyphicon-pencil"></span></button>
      |  <div class="form-group">
      |  <input type="text" *ngIf="internalEditStates[i]" class="form-control" [(ngModel)]="internalEditStrings[i]">
      |  </div>
      |  <button (click)="editOkayBtnClicked(i)" *ngIf="internalEditStates[i]"  class="btn btn-success" ><span class="glyphicon glyphicon-check"></span></button>
      |  <button (click)="editRevertBtnClicked(i)" *ngIf="internalEditStates[i]" class="btn btn-danger" ><span class="glyphicon glyphicon-remove-sign"></span></button>
      |  <div *ngIf="! internalEditStates[i]"> {{item}} </div></div>
      |
      |</div>
      |
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
      |  background: #e0ddd5;
      |  color: #171e42;
      |  padding: 10px;
      |	margin-left: 10px;
      |	margin-top: 10px;
      |}
    """.stripMargin)
)
class StringListComponent {

  @Input
  var inputStrings : js.Array[String] = js.Array("Note A",
    "entry with long text and really much to write about that not really says anything useful, so why am I even typing this, this is stupid, so stupid, why am i typing this",
    "Note B", "@2153 €?!", "xxx", "avbc", "Note sfasf")

  var internalStrings : js.Array[String] = js.Array()

  var internalEditStates : js.Array[Boolean] = js.Array()

  var internalEditStrings : js.Array[String] = js.Array()

  var newStringField : String = ""


  def inputChanged() : Unit = {
    println("Input changed")

    newStringField = ""
    internalEditStates = js.Array()
    internalEditStrings = js.Array()
    internalStrings = js.Array()
    (0 until inputStrings.length).foreach(i => {
      internalEditStates(i) = false
      internalEditStrings(i) = inputStrings(i)
      internalStrings(i) = inputStrings(i)
    })
  }

  inputChanged() //todo: call this in OnChanges method


  def emitSeq() : Unit = {
    contentChangedEvent((0 until internalStrings.length).map(i => internalStrings(i)))
  }

  def editBtnClicked(index : Int) : Unit = {
    internalEditStates(index) = true
  }

  def editOkayBtnClicked(index : Int) : Unit = {
    internalEditStates(index) = false
    internalStrings(index) = internalEditStrings(index)
    emitSeq()
  }

  def editRevertBtnClicked(index : Int) : Unit = {
    internalEditStates(index) = false
    internalEditStrings(index) = internalStrings(index)
    emitSeq()
  }



  def addBtnClicked() : Unit = {
    println("add button clicked")
    if(newStringField != null && newStringField.length > 2) {
      internalEditStates.push(false)
      internalStrings.push(newStringField)
      internalEditStrings.push(newStringField)
      newStringField = ""
      emitSeq()
    }
  }

  def removeBtnClicked(index : Int) : Unit = {
    println(s"remove button clicked for index = $index")
    internalEditStates.remove(index)
    internalStrings.remove(index)
    internalEditStrings.remove(index)
    emitSeq()

  }

  def contentChangedEvent(newContent : Seq[String]) : Unit = {
    println(s"Content changed to ${newContent}")
  }

}
