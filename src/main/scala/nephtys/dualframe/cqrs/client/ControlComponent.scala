package nephtys.dualframe.cqrs.client

import angulate2.core.OnChanges.SimpleChanges
import angulate2.core.{EventEmitter}
import angulate2.std._
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, Owned}

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import org.scalajs.dom.window._

import scala.scalajs.js

/**
  * Created by nephtys on 12/14/16.
  */
@Component(
  selector = "meta-control-component",
  template =
    """
      | <div>
      | <div *ngIf="useTitle">
      | <h3 >Access Settings</h3>
      |</div>
      | <button (click)="togglePublicBtn()" class="btn btn-warning">Currently {{publicStr}}, set to {{otherPublicStr}}</button>
      |
      |
      |
      | <button (click)="deleteBtnClicked()" class="btn btn-danger" >Delete permanently</button>
      |
      |<form class="form-inline">
      |  <div class="form-group">
      |    <label for="email">Owner (only one with write access):</label>
      |    <input type="email" [(ngModel)]="ownerStr" name="owner" class="form-control" id="email">
      |  </div>
      |  <button (click)="changeOwnerClicked()" class="btn btn-success">Change Owner</button>
      |</form>
      |
      |
      | <div>
      | <h4>Readonly Access</h4>
      | <form class="form-inline">
      |  <div class="form-group">
      |    <label for="email">New Reader's Email Address:</label>
      |    <input  type="email" [(ngModel)]="newReaderStr" name="newreader" class="form-control" id="email">
      |  </div>
      |  <button (click)="addReaderBtn()" class="btn btn-success">Add</button>
      |</form>
      |
      |
      |<label>Emails with explicit readonly access:</label>
      |<ul>
      |<li *ngFor="let r of readers; let i = index">
      |<button (click)="removeReaderBtn(i)" class="btn btn-danger" ><span class="glyphicon glyphicon-remove"></span></button>
      |{{r}}
      |</li>
      |</ul>
      |
      |<div>
      |
      |</div>
    """.stripMargin
)
class ControlComponent extends OnChanges{

  @Input
  var input : Owned = _

  @Input
  var useTitle : Boolean = true

  var readers : js.Array[String] = js.Array("somedude@company.com", "other@home.org")
  var owner : String = "bob@home.com"
  var public : Boolean = false
  var otherPublicStr : String = ""
  var publicStr : String = ""

  var newReaderStr : String = ""
  var ownerStr : String = "bob@home.com"



  def togglePublicBtn() : Unit = {
    println("public button toggled")
    public = ! public
    publicStateEvent(public)
    setPublicStrings()
  }

  def addReaderBtn() : Unit = {
    val str = newReaderStr
    newReaderStr = ""
    println(s"received string $str")
    if (isEmail(str)) {
      readers.push(str)
      readersChangedEvent((0 until readers.length).map(i => Email(readers(i))).toSet)
    }
  }

  def removeReaderBtn(index : Int) : Unit = {
    if (confirm(s"Do you want to remove ${readers(index)} from list of people with read access?")) {
      println(s"removing reader with index = $index")
      val d = readers.remove(index)
      readersChangedEvent((0 until readers.length).map(i => Email(readers(i))).toSet)
    }
  }

  def deleteBtnClicked() : Unit = {
    if (confirm("Do you really want to permanently and irreversible delete this entire aggregate?")) {
      println("Deleting instance!")
      deleteEventTriggered()
    }
  }

  def changeOwnerClicked() : Unit = {
    if (isEmail(ownerStr)) {
      println("valid email!")
      owner = ownerStr
      if(org.scalajs.dom.window.confirm(s"Are you really sure you want to move ownership and control over this character to $ownerStr ? If the address is not correct this character will be permanently corrupted")) {
        ownerChangedEvent(Email(ownerStr))
      }
    } else {
      alert("The value you inputed was not a valid email!")
      ownerStr = owner
    }
  }


  def isEmail(str : String) : Boolean = {
    """(?=[^\s]+)(?=(\w+)@([\w\.]+))""".r.findFirstIn(str).isDefined
  }

  def setPublicStrings() : Unit = {
    val s = Seq("private", "public")
    val cur = if(public) 1 else 0
    val nex = if(public) 0 else 1
    publicStr = s(cur)
    otherPublicStr = s(nex)
  }

  def inputChanged() : Unit = {
    println("input changed on meta-control-component")

    newReaderStr = ""

    if(input != null) {
      public = input.public
      owner = input.owner.email
      ownerStr = owner
      readers = input.readers.map(_.email).toJSArray
    }


    setPublicStrings()
  }

  inputChanged()



  def deleteEventTriggered() : Unit = {
    println("Triggering deletion event")
    deleteEvent.emit(true)
  }

  def publicStateEvent(public : Boolean) : Unit = {
    println(s"public-state changed to $public")
    publicStateChanged.emit(public)
  }

  def readersChangedEvent(set : Set[Email]) : Unit = {
    println(s"readers changed to $set")
    readersChanged.emit(set)
  }

  def ownerChangedEvent(newOwner : Email) : Unit = {
    println(s"owner changed to $newOwner")
    ownerChanged.emit(newOwner)
  }

  @Output
  val ownerChanged = new EventEmitter[Email]()

  @Output
  val deleteEvent = new EventEmitter[Boolean]()

  @Output
  val readersChanged = new EventEmitter[Set[Email]]()

  @Output
  val publicStateChanged = new EventEmitter[Boolean]()

  override def ngOnChanges(changes: SimpleChanges): Unit = inputChanged()
}
