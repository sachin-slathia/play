package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.Future

case class AssignmentRepo(id: Long, title: String, description: String)

class AssignmentDatabase @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends Impl1 with UserTable1 {


}

trait Impl1 {

  self: UserTable1 =>

  import profile.api._

  def store(assignmentRepo: AssignmentRepo): Future[Long] = {


    db.run {

      userProfileQuery returning userProfileQuery.map(_.id) += assignmentRepo

    }

  }

  def getAll(): Future[Seq[AssignmentRepo]] = {

    db.run(userProfileQuery.result)

  }
}


trait UserTable1 extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._


  val userProfileQuery: TableQuery[UserProfile] = TableQuery[UserProfile]

  private[models] class UserProfile(tag: Tag) extends Table[AssignmentRepo](tag, "assignment") {
    def * : ProvenShape[AssignmentRepo] = (id, title, description) <> (AssignmentRepo.tupled, AssignmentRepo.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def title: Rep[String] = column[String]("title")

    def description: Rep[String] = column[String]("description")

  }


}
