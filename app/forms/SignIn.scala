package forms

import play.api.data.Form
import play.api.data.Forms.{mapping, text}


case class userAuthentication(userName: String, password: String)

class SignIn {

  val signIn = Form(

    mapping(
      "user-name" -> text.verifying("Please enter a valid email", _.nonEmpty),

      "password" -> text.verifying("Please enter a valid email", _.nonEmpty)
    )(userAuthentication.apply)(userAuthentication.unapply))


}
