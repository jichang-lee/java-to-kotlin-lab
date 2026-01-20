package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest(
    @Autowired private val bookService: BookService,
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
        bookRepository.deleteAll()
    }

    @Test
    @DisplayName("S : 책 등록이 정상 작동한다")
    fun saveBookTest() {
        //given
        val bookRequest = BookRequest("하루 100엔 보관가게")

        //when
        bookService.saveBook(bookRequest)

        //then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("하루 100엔 보관가게")
    }

    @Test
    @DisplayName("S : 회원이 책을 정상적으로 대출한다")
    fun loanBookSuccessTest() {
        //given
        val saveBook = bookRepository.save(Book("하루 100엔 보관가게"))
        val saveUser = userRepository.save(User("이지창", 30))
        val bookLoanRequest = BookLoanRequest(saveUser.name, saveBook.name)

        //when
        bookService.loanBook(bookLoanRequest)

        //then
        val result = userLoanHistoryRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].bookName).isEqualTo("하루 100엔 보관가게")
        assertThat(result[0].user.id).isEqualTo(saveUser.id)
        assertThat(result[0].isReturn).isFalse

    }

    @Test
    @DisplayName("F : 책이 이미 대출되어 있다면, 회원이 신규 대출이 실패한다.")
    fun loanBookFailTest() {
        //given
        val saveBook = bookRepository.save(Book("하루 100엔 보관가게"))
        val saveUser = userRepository.save(User("이지창", 30))
        userLoanHistoryRepository.save(UserLoanHistory(saveUser,saveBook.name,false))

        val bookLoanRequest = BookLoanRequest(saveUser.name, saveBook.name)

        //when & then
        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(bookLoanRequest)
        }.message
        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @Test
    @DisplayName("책을 정상적으로 반납한다.")
    fun returnBookTest() {
        //given
        val saveBook = bookRepository.save(Book("하루 100엔 보관가게"))
        val saveUser = userRepository.save(User("이지창", 30))
        userLoanHistoryRepository.save(UserLoanHistory(saveUser,saveBook.name,false))

        val bookReturnRequest = BookReturnRequest(saveUser.name, saveBook.name)

        //when
        bookService.returnBook(bookReturnRequest)

        //then
        val result = userLoanHistoryRepository.findAll()
        assertThat(result[0].isReturn).isTrue
        assertThat(result[0].user.id).isEqualTo(saveUser.id)
        assertThat(result[0].bookName).isEqualTo(saveBook.name)
    }
}