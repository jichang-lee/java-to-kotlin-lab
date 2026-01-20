package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest(
    @Autowired private val bookService: BookService,
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val userLoanHistoryRepository: UserLoanHistoryRepository
) {


}