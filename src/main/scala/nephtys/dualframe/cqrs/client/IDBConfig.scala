package nephtys.dualframe.cqrs.client

import org.nephtys.loom.generic.protocol.InternalStructures.IDable

/**
  * Created by nephtys on 12/16/16.
  */
final case class IDBConfig(dbName : String, aggregateStoreName : String, commandStoreName : String, version : Long)