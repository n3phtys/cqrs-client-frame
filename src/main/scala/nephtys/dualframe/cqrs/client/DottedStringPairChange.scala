package nephtys.dualframe.cqrs.client

/**
  * Created by nephtys on 1/1/17.
  */
object DottedStringPairChange {

  sealed trait DottedStringPairChange

  final case class Insert(value : DottedStringPair) extends DottedStringPairChange
  final case class Remove(index : Int) extends DottedStringPairChange
  final case class Edit(index : Int, value : DottedStringPair) extends DottedStringPairChange
}
