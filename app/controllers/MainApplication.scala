package controllers

import forms.{SignIn, SignUp}
import javax.inject.Inject
import models.{AssignmentDatabase, StoreInDatabase, UserRepo}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MainApplication @Inject()(controllerComponent: ControllerComponents,
                                signUp: SignUp, signIn: SignIn, storeInDatabase: StoreInDatabase,
                                assignmentDatabase: AssignmentDatabase)

  extends AbstractController(controllerComponent) {


  def signInPage: Action[AnyContent] = Action{ implicit request =>

   Ok(views.html.signIn())
  }

  def viewProfile: Action[AnyContent] = Action.async { implicit request =>

    Future.successful(Ok(views.html.viewProfile()))

  }


  /*
    def viewProfile: Action[AnyContent] = Action.async { implicit request =>

      signIn.signIn.bindFromRequest().fold(

        formWithError => {
          Future.successful(Ok("An error occured"))
        },

        { data => {

          storeInDatabase.get(data.userName).flatMap { optionalUser =>
            optionalUser.fold {
              Future.successful(Ok(views.html.signIn()))
            } { data =>
               Future(Ok("All fjdsfkjd"))


            }
          }
        }
        })
    }
  */


  def viewAssignment: Action[AnyContent] = Action.async {
    implicit request =>
      assignmentDatabase.getAll().map {
        data =>
          Ok(views.html.viewAssignment(data))
      }


  }

  def viewUser: Action[AnyContent] = Action.async {
    implicit request =>

      storeInDatabase.getAll().map {
        data =>
          Ok(views.html.viewUser(data))
      }
  }

  def addAssignment: Action[AnyContent] = Action.async {
    implicit request =>

      Future.successful(Ok(views.html.addAssignment()))

  }


  def homePage: Action[AnyContent] = Action.async {
    implicit request =>

      Future.successful(Ok(views.html.homePage()))

  }

  def signUpPage: Action[AnyContent] = Action.async {
    implicit request =>

      signUp.signUp.bindFromRequest().fold(

        formWithError => {
          Future.successful(Ok(views.html.register(formWithError)))
        }, {
          data => {

            storeInDatabase.get(data.userName).flatMap {
              optionalUser =>
                optionalUser.fold {
                  val dbPlayLoad = UserRepo(0, data.firstName, data.middleName, data.lastName, data.userName,
                    data.password.confirmPassword, data.phoneNumber, data.gender)
                  storeInDatabase.store(dbPlayLoad).map {
                    case _: Long => Ok(views.html.signIn())
                    case _ => InternalServerError

                  }
                } {
                  _ => Future.successful(Ok("User already exist"))
                }
            }
          }
        })

  }

  def getUser(userName: String): Action[AnyContent] = Action.async {
    implicit request =>

      storeInDatabase.get(userName).map {
        optionalUser =>
          optionalUser.fold {
            NotFound("User does not exist")

          } {
            user =>
              Ok(s"${
                user.userName
              },${
                user.gender
              },${
                user.mobileNumber
              }")
          }


      }


  }


  def authenticate: Action[AnyContent] = Action.async {
    implicit request =>
      signIn.signIn.bindFromRequest().fold(
        formWithError => {
          Future.successful(Ok("Internal Server error"))
        },
        data => {
          storeInDatabase.get(data.userName).flatMap {
            optionalUser =>
              optionalUser.fold {
                Future.successful(Ok("No user exist with user Name you have typed"))
              } {
                user => {
                  if (user.password == data.password) {
                    Future.successful(Ok(views.html.homePage()))
                  }
                  else {
                       Ok("Password does not match")
                    Future.successful(Redirect(routes.MainApplication.signInPage))
                  }
                }
              }
          }
        }
      )
  }


}