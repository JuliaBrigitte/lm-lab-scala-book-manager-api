package controllers

import models.Book
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._
import repositories.BookRepository

import javax.inject.{Inject, Singleton}

@Singleton
class BooksController @Inject()(val controllerComponents: ControllerComponents, dataRepository: BookRepository) extends BaseController {

  def getAll: Action[AnyContent] = Action {
    Ok(Json.toJson(dataRepository.getAllBooks))
  }

  def getBook(bookId: Long): Action[AnyContent] = Action
  {
    var bookToReturn: Book = null
    dataRepository.getBook(bookId) foreach
    { book =>
      bookToReturn = book
    }
    //NotFound(Json.toJson("Book not found"))
    if (bookToReturn eq null)
    {
      NotFound(Json.toJson("Book not found"))
    }
    else
    {
      Ok(Json.toJson(bookToReturn))
    }
  
    //Ok(Json.toJson(bookToReturn))
    
  }
  
  def deleteBook(delete: String, bookId: Long) : Action[AnyContent] = Action
  {
    val success=dataRepository.deleteBookById(bookId)
    if (success)
    {
      Ok(Json.toJson("Book with id " + bookId + " deleted"))
    }
    else
    {
      Ok(Json.toJson("Book with id " + bookId + " does not exist"))
    }
  }

  def addBook() : Action[AnyContent] = Action {
    implicit request => {
      val requestBody = request.body
      val bookJsonObject = requestBody.asJson

      // This type of JSON un-marshalling will only work
      // if ALL fields are POSTed in the request body
      val bookItem: Option[Book] =
        bookJsonObject.flatMap(
          Json.fromJson[Book](_).asOpt
        )

      val savedBook: Option[Book] = dataRepository.addBook(bookItem.get)
      Created(Json.toJson(savedBook))
    }


  }
}
