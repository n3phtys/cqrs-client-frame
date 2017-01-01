package nephtys.dualframe.cqrs.client

/**
  * Created by nephtys on 12/31/16.
  */

object StringListDif {
sealed trait StringListDif {

}

final case class StringListDelete(index : Int) extends StringListDif
final case class StringListAdd(value : String) extends StringListDif
final case class StringListEdit(index : Int, value : String) extends StringListDif
}