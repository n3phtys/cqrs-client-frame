package nephtys.dualframe.cqrs.client.oidchelper

import upickle.default._
import com.github.marklister.base64.Base64._

import scala.util.Try

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

  def extractToken(token : String) : Try[IdentityToken] = Try{
    base64EncodedTokenWithoutBearerHeader(token)
  }

  protected def base64EncodedTokenWithBearerHeader(token : String) : IdentityToken = {
    val extract : String = decodeFromBase64(removeHeader(token).trim.split('.')(1))
    //println(s"Extract = $extract")
    read[IdentityToken](extract)
  }

  protected def base64EncodedTokenWithoutBearerHeader(token : String) : IdentityToken = {
    val extract : String = decodeFromBase64(token.trim.split('.')(1))
    //println(s"Extract = $extract")
    read[IdentityToken](extract)
  }

  def decodeFromBase64(value : String) : String = new String(value.toByteArray(base64Url), "utf-8")
  def removeHeader(headerValue : String) : String = headerValue.split("Bearer ").last.trim
}
