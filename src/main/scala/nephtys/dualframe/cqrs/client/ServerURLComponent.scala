package nephtys.dualframe.cqrs.client

import angulate2.std._

/**
  * Created by Christopher on 22.01.2017.
  */
@Component(
  selector = "url-select",
  template =
    """
      | <div>
      | <div *ngIf="!urlChanging">
      | <div>
      | <div (click)="edit()" >{{urlLabel}} (click to edit)</div>
      | </div>
      | </div>
      | <div *ngIf="urlChanging">
      | <label>Server URL, include protocol and port: </label>
      | <div  class="form-group">
      | <input [(ngModel)]="textfield"  class="form-control">
      |</div>
      |
      |  <button (click)="cancel()" class="btn btn-danger" ><span class="glyphicon glyphicon-remove-sign"></span></button>
      |
      |<button (click)="ok()"   class="btn btn-success" ><span class="glyphicon glyphicon-check"></span></button>
      |
      |
      | </div>
      | </div>
      |
      |
    """.stripMargin,
  styles = @@@(
    """
      |label {
      |color: #B4886B;
      |font-weight: bold;
      |/*display: block;*/
      |}
      |
      |
      |
    """.stripMargin
  )
)
class ServerURLComponent(val serverURLService: ServerURLService) {

  var urlChanging : Boolean = false

  var textfield : String = "textfield content"
  var urlLabel : String = "url label" //if empty, set placeholder text

  serverURLService.urlRx.subscribe(t => {
    textfield = t.getOrElse("http://localhost:80")
    if (t.isEmpty) {
      urlLabel = "No Sync-Server set"
    } else {
      urlLabel = "Server URL: "+ t.get
    }
  })

  //label ( / text field switch
  //text field has Cancel or OK Button
  //label has edit button

  def edit() : Unit = {
    urlChanging = true
  }
  def cancel() : Unit = {
    urlChanging = false
    textfield = serverURLService.getUrlOpt.getOrElse("http://localhost:80")
  }
  def ok() : Unit = {
    serverURLService.setUrl(textfield)
    urlChanging = false
  }

}
