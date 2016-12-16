package nephtys.dualframe.cqrs.client

import java.util.UUID

import angulate2.std._
import org.nephtys.loom.generic.protocol.InternalStructures.IDable

import scala.concurrent.Future

/**
  * Created by nephtys on 12/16/16.
  */
@Injectable
class IDBPersistenceService {

  def getAsync(key : UUID)(implicit config : IDBConfig) : Future[String] = ???

  def getAllAsync()(implicit config : IDBConfig) : Future[Seq[String]] = ???

  def getSetAsync(keys : Seq[UUID]) : Future[Seq[String]] = ???

  def removeAsync(key : UUID)(implicit config : IDBConfig) : Future[Unit] = ???

  def removeSetAsync(keys : Seq[UUID])(implicit config: IDBConfig) : Future[Unit] = ???

  def setAsync(key : UUID, value : String)(implicit config : IDBConfig) : Future[Unit] = ???

  def setSetAsync(keyValueMap : Map[UUID, String])(implicit config : IDBConfig) : Future[Unit] = ???

}
