package nephtys.dualframe.cqrs.client.httphelper

import nephtys.dualframe.cqrs.client.httphelper.HttpResults.{ETag, ResultCode}

/**
  * Created by nephtys on 12/4/16.
  */
case class HttpResult(resultCode : ResultCode, body : String, etag : Option[ETag])


