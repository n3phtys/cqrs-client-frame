package nephtys.dualframe.cqrs.client.oidchelper

import upickle.default._
import com.github.marklister.base64.Base64._

import scala.util.{Failure, Try}

/**
  * Created by nephtys on 12/6/16.
  */
object OIDC {
  case class IdentityToken(
                            iss: String,
                            iat: Int,
                            exp: Int,
                            at_hash: String,
                            aud: String,
                            sub: String,
                            email_verified: Boolean,
                            azp: String,
                            email: String
                          ) {
    def iat_long : Long = iat.toLong
    def exp_long : Long = exp.toLong
  }

  def extractToken(token : String) : Try[IdentityToken] = {
    base64EncodedTokenWithoutBearerHeader(token)
  }

  /*
  protected def base64EncodedTokenWithBearerHeader(token : String) : Try[IdentityToken] = {
    val extract : String = decodeFromBase64(removeHeader(token).trim.split('.')(1))
    //println(s"Extract = $extract")
    read[IdentityToken](extract)
  }*/

  protected def base64EncodedTokenWithoutBearerHeader(token : String) : Try[IdentityToken] = {
    val splitted : Array[String] = token.trim.split('.')
    if (splitted.length < 3) {
      Failure(new Exception("Not enough parts of JWT"))
    } else {
      println(s"Splitted = ${splitted.mkString(" | ")}")
      val split: String = splitted(1)
      println(s"Split = $split")
      val decoded: Try[String] = decodeFromBase64(split)
      decoded.flatMap(s => {
        println(s"Extract = $s")
        Try {
          val t = read[IdentityToken](s)
          println(s"Parsing worked for token $t")
          t
        }
      })
    }
  }

  def decodeFromBase64(value : String) : Try[String] = Try{new String(value.toByteArray(base64Url), "utf-8")}
  def removeHeader(headerValue : String) : String = headerValue.split("Bearer ").last.trim
}
