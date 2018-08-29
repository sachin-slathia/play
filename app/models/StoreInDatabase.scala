package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class UserRepo(id: Long,firstName: String,middleName: String,lastName:String ,userName: String,
                    password: String,  mobileNumber: String,gender: String)

class StoreInDatabase @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends Impl with UserTable {


}

trait Impl {

  self: UserTable =>

  import profile.api._

  def store(userRepo: UserRepo): Future[Long] = {


    db.run {

      userProfileQuery returning userProfileQuery.map(_.id) += userRepo

    }

  }

  def get(email: String): Future[Option[UserRepo]] = {

    db.run(userProfileQuery.filter(_.userName === email.toLowerCase).result.headOption)

  }
  def getAll(): Future[Seq[UserRepo]] = {

    db.run( userProfileQuery.result)

  }
  def updatePassword(userName: String, password: String): Future[Int] = {
    db.run {
      val q = {
        for {user <- userProfileQuery if user.userName === userName} yield user.password
      }
      q.update(password)
    }
  }
}


trait UserTable extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._


  val userProfileQuery: TableQuery[UserProfile] = TableQuery[UserProfile]

  private[models] class UserProfile(tag: Tag) extends Table[UserRepo](tag, "userprofile") {
    def * : ProvenShape[UserRepo] = (id, firstName, middleName,lastName,userName, password,mobileNumber, gender) <> (UserRepo.tupled, UserRepo.unapply)

    def id: Rep[Long] = column[Long]("id",O.PrimaryKey, O.AutoInc)

    def firstName: Rep[String] = column[String]("firstName")
    def middleName: Rep[String] = column[String]("middleName")
    def lastName: Rep[String] = column[String]("lastName")
    def userName: Rep[String] = column[String]("userName")


    def password: Rep[String] = column[String]("password")

    def mobileNumber: Rep[String] = column[String]("mobileNumber")
    def gender: Rep[String] = column[String]("gender")
  }


}
