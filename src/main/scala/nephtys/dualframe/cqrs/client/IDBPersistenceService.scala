package nephtys.dualframe.cqrs.client

import java.util.UUID

import angulate2.std._
import org.scalajs.dom
import org.scalajs.dom.raw.{Event, IDBCursor, IDBDatabase}
import upickle.default._

import scala.collection.mutable
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js._
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by nephtys on 12/16/16.
  */
@Injectable
class IDBPersistenceService {

  println("IDBPersistenceService instantiated")

  def getAsync(key : UUID, commandStore : Boolean)(implicit config : IDBConfig) : Future[String] = {
    val promise : scala.concurrent.Promise[String] = scala.concurrent.Promise[String]()

    def objectStoreName : String = if (commandStore) config.commandStoreName else config.aggregateStoreName

    def db = dom.window.indexedDB

    val open = db.open(config.dbName, config.version.toInt)

    // Create the schema
    open.addEventListener[Event]("upgradeneeded", {
      (event: Event) => {
        var db2 = open.result.asInstanceOf[IDBDatabase]
        var storeA = db2.createObjectStore(config.aggregateStoreName)
        var storeB = db2.createObjectStore(config.commandStoreName)
      }
    }, true)

    open.addEventListener[Event]("success", {
      (event: Event) => {
        // Start a new transaction
        val db = open.result.asInstanceOf[IDBDatabase]

        val tx = db.transaction(objectStoreName, "readonly")

        val store = tx.objectStore(objectStoreName)

        val getRequest = store.get(write(key))

        var t : Try[String] = Failure(new Exception("not set"))

        getRequest.addEventListener[Event]("error", {
          (event: Event) => {
              println(s"error reading $key from database")
          }
        })

        getRequest.addEventListener[Event]("success", {
          (event: Event) => {
            t = Try(getRequest.result.asInstanceOf[String])
          }
        })

        tx.addEventListener[Event]("error", {
          (event: Event) => {
            db.close()
            promise.failure(new Exception(event.toString))
          }
        })

        tx.addEventListener[Event]("complete", {
          (event: Event) => {
            db.close()
            promise.complete(t)

          }
        })
      }

    }, false)

    promise.future
  }

  def getAllAsync(commandStore : Boolean)(implicit config : IDBConfig) : Future[Seq[String]] = {
    val promise : scala.concurrent.Promise[Seq[String]] = scala.concurrent.Promise[Seq[String]]()

    def objectStoreName : String = if (commandStore) config.commandStoreName else config.aggregateStoreName

    def db = dom.window.indexedDB

    val open = db.open(config.dbName, config.version.toInt)

    // Create the schema
    open.addEventListener[Event]("upgradeneeded", {
      (event: Event) => {
        var db2 = open.result.asInstanceOf[IDBDatabase]
        var storeA = db2.createObjectStore(config.aggregateStoreName)
        var storeB = db2.createObjectStore(config.commandStoreName)
      }
    }, true)

    open.addEventListener[Event]("success", {
      (event: Event) => {
        // Start a new transaction
        val db = open.result.asInstanceOf[IDBDatabase]

        val tx = db.transaction(objectStoreName, "readonly")

        val store = tx.objectStore(objectStoreName)

        val cursorRequest = store.openCursor()

        val keysBuffer = mutable.Buffer.empty[String]
        val valueBuffer = mutable.Buffer.empty[String]

        cursorRequest.addEventListener[Event]("error", {
          (event: Event) => {
            println(s"error reading from database: ${event.toString}")
          }
        })

        cursorRequest.addEventListener[Event]("success", {
          (event: Event) => {
            val cursor = cursorRequest.result.asInstanceOf[IDBCursor]
            if (cursor != null) {
              keysBuffer.+=(cursor.key.asInstanceOf[String])
              cursor.continue()
            } else {
              keysBuffer.foreach(key => {
                val getValue = store.get(key)
                getValue.addEventListener[Event]("success", {
                (event: Event) => {
                  if (getValue.result != null && getValue.result.toString.isInstanceOf[String]) {
                    val json: String = getValue.result.toString
                    valueBuffer.+=(json)
                  }
                }})
              })
            }
          }
        })

        tx.addEventListener[Event]("error", {
          (event: Event) => {
            db.close()
            promise.failure(new Exception(event.toString))
          }
        })

        tx.addEventListener[Event]("complete", {
          (event: Event) => {
            db.close()
            promise.success(valueBuffer)

          }
        })
      }

    }, false)

    promise.future
  }

  def getSetAsync(keys : Seq[UUID], commandStore : Boolean)(implicit config : IDBConfig) : Future[Seq[String]] = {
    val promise : scala.concurrent.Promise[Seq[String]] = scala.concurrent.Promise[Seq[String]]()

    def objectStoreName : String = if (commandStore) config.commandStoreName else config.aggregateStoreName

    def db = dom.window.indexedDB

    val open = db.open(config.dbName, config.version.toInt)

    // Create the schema
    open.addEventListener[Event]("upgradeneeded", {
      (event: Event) => {
        var db2 = open.result.asInstanceOf[IDBDatabase]
        var storeA = db2.createObjectStore(config.aggregateStoreName)
        var storeB = db2.createObjectStore(config.commandStoreName)
      }
    }, true)

    open.addEventListener[Event]("success", {
      (event: Event) => {
        // Start a new transaction
        val db = open.result.asInstanceOf[IDBDatabase]

        val tx = db.transaction(objectStoreName, "readonly")

        val store = tx.objectStore(objectStoreName)


        val valueBuffer = mutable.Buffer.empty[String]

        keys.foreach(key => {
          val getValue = store.get(write(key))
          getValue.addEventListener[Event]("success", {
            (event: Event) => {
              if (getValue.result != null && getValue.result.toString.isInstanceOf[String]) {
                val json: String = getValue.result.toString
                valueBuffer.+=(json)
              }
            }})
        })

        tx.addEventListener[Event]("error", {
          (event: Event) => {
            db.close()
            promise.failure(new Exception(event.toString))
          }
        })

        tx.addEventListener[Event]("complete", {
          (event: Event) => {
            db.close()
            promise.success(valueBuffer)

          }
        })
      }

    }, false)

    promise.future
  }

  def removeAsync(key : UUID, commandStore : Boolean)(implicit config : IDBConfig) : Future[Unit] = removeSetAsync(Seq(key), commandStore)

  def removeSetAsync(keys : Seq[UUID], commandStore : Boolean)(implicit config: IDBConfig) : Future[Unit] = {

    val promise : scala.concurrent.Promise[Unit] = scala.concurrent.Promise[Unit]()

    def objectStoreName : String = if (commandStore) config.commandStoreName else config.aggregateStoreName

    def db = dom.window.indexedDB

    val open = db.open(config.dbName, config.version.toInt)

    // Create the schema
    open.addEventListener[Event]("upgradeneeded", {
      (event: Event) => {
        var db2 = open.result.asInstanceOf[IDBDatabase]
        var storeA = db2.createObjectStore(config.aggregateStoreName)
        var storeB = db2.createObjectStore(config.commandStoreName)
      }
    }, true)

    open.addEventListener[Event]("success", {
      (event: Event) => {
        // Start a new transaction
        val db = open.result.asInstanceOf[IDBDatabase]

        val tx = db.transaction(objectStoreName, "readwrite")

        val store = tx.objectStore(objectStoreName)

        keys.foreach(a => store.delete(write(a))) //This is probably not optimal, as it is called as fire-and-forget

        tx.addEventListener[Event]("error", {
          (event: Event) => {
            db.close()
            promise.failure(new Exception(event.toString))
          }
        })

        tx.addEventListener[Event]("complete", {
          (event: Event) => {
            db.close()
            promise.success(())

          }
        })
      }

    }, false)

    promise.future
  }


  def setAsync(key : UUID, value : String, commandStore : Boolean)(implicit config : IDBConfig) : Future[Unit] = setSetAsync(Map(key -> value), commandStore)

  def setSetAsync(keyValueMap : Map[UUID, String], commandStore : Boolean)(implicit config : IDBConfig) : Future[Unit] = {

    val promise : scala.concurrent.Promise[Unit] = scala.concurrent.Promise[Unit]()

    def objectStoreName : String = if (commandStore) config.commandStoreName else config.aggregateStoreName

    def db = dom.window.indexedDB

    val open = db.open(config.dbName, config.version.toInt)

    // Create the schema
    open.addEventListener[Event]("upgradeneeded", {
      (event: Event) => {
        var db2 = open.result.asInstanceOf[IDBDatabase]
        var storeA = db2.createObjectStore(config.aggregateStoreName)
        var storeB = db2.createObjectStore(config.commandStoreName)
      }
    }, true)

    open.addEventListener[Event]("success", {
      (event: Event) => {
        // Start a new transaction
        val db = open.result.asInstanceOf[IDBDatabase]

        val tx = db.transaction(objectStoreName, "readwrite")

        val store = tx.objectStore(objectStoreName)

        keyValueMap.foreach(a => store.put(a._2, write(a._1))) //This is probably not optimal, as it is called as fire-and-forget

        tx.addEventListener[Event]("error", {
          (event: Event) => {
            db.close()
            promise.failure(new Exception(event.toString))
          }
        })

        tx.addEventListener[Event]("complete", {
          (event: Event) => {
            db.close()
            promise.success(())

          }
        })
      }

    }, false)

    promise.future
  }

}
