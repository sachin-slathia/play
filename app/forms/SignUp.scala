package forms

import play.api.data.Form
import play.api.data.Forms.{email, mapping, text}

case class User(firstName: String, middleName: String, lastName: String,
                userName: String, password: Password,
                phoneNumber: String, gender: String)

case class Password(password: String, confirmPassword: String)

class SignUp {

  val signUp = Form(

    mapping(
      "first-name" -> text.verifying("Please enter valid first-name", _.nonEmpty),
      "middle-name" -> text.verifying("Please enter valid first-name", _.nonEmpty),

      "last-name" -> text.verifying("Please enter valid last-name", _.nonEmpty),
      "user-name" -> email,
      "passwordGroup" -> mapping(

        "password" -> text.verifying("Please select a value", _.nonEmpty),
        "confirmPassword" -> text.verifying("Please select a value", _.nonEmpty)
      )(Password.apply)(Password.unapply).verifying("Password and confirm password should match",
        passwordGroup => passwordGroup.password.equals(passwordGroup.confirmPassword)),
      "phone-number" -> text.verifying("phone-number", _.length==10),
      "gender" -> text.verifying("Enter gender", _.nonEmpty)

    )(User.apply)(User.unapply)

  )


}



