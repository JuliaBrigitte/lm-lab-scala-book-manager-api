package repositories

import models.Book

import javax.inject.Singleton
import scala.collection.mutable

@Singleton
class BookRepository {

  private val bookList = mutable.Set[Book]()
  bookList += Book(1,
    "Programming in Scala, Fifth Edition",
    "Martin Odersky",
    "Scala programming language",
    "Development"
  )
  bookList += Book(2,
    "Book 2",
    "Martin Odersky",
    "Scala programming language",
    "Development"
  )
  bookList += Book(3,
    "Book 3",
    "Martin Odersky",
    "Scala programming language",
    "Development"
  )

  def getAllBooks: mutable.Set[Book] = bookList

  /**
   * @param bookId The ID of the book to find
   * @return Option[Book] An option means this method can return a Book or optionally a "None"
   *         https://www.scala-lang.org/api/2.13.8/scala/Option.html
   */
  
  def getBook(bookId: Long): Option[Book] = bookList.collectFirst {
    case book if book.id == bookId => book
  }
  
  def deleteBookById(bookId: Long): Boolean =
  {
    val bookToDelete=getBook(bookId).get
    if (bookToDelete.isInstanceOf[Book] )
    {
      bookList.remove(bookToDelete)
      true
    }
    else
      false
  }

  def addBook(book: Book): Option[Book] = {
    // If book already exists then return none
    if(bookList.exists(b => b.id == book.id)) {
      None
    }
    else {
      // otherwise return the saved after adding it
      bookList.addOne(book).collectFirst {
        case b if b.id == book.id => b
      }
    }
  }

}
