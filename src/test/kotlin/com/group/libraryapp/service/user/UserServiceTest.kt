package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    /**
     * S : 성공 케이스
     * F : 실패 케이스
     */

    @Test
    @DisplayName("S : 회원 저장이 정상 동작한다")
    fun saveUserTest() {
        //given
        val request = UserCreateRequest("이지창",null)

        //when
        userService.saveUser(request)

        //then
        val findAllUser = userRepository.findAll()
        assertThat(findAllUser).hasSize(1)
        assertThat(findAllUser[0].name).isEqualTo("이지창")
        assertThat(findAllUser[0].age).isNull()
    }

    @Test
    @DisplayName("S : 회원들 정보 가져오는데 정상 동작한다")
    fun getUsersTest() {
        //given
        val userList = listOf(
            User("A", 20),
            User("B", null),
        )
        userRepository.saveAll(userList)

        //when
        val result = userService.getUsers()

        //then
        assertThat(result).hasSize(2) // [ UserResponse(), UserResponse() ]
        assertThat(result).extracting("name").containsExactlyInAnyOrder("A","B") //["A","B"]
        assertThat(result).extracting("age").containsExactlyInAnyOrder(20,null)

    }


    @Test
    @DisplayName("S : 회원 이름 수정이 정상 작동한다")
    fun updateUserTest() {
        //given
        val saveUser = userRepository.save(User("A", null))
        val updateUser = UserUpdateRequest(saveUser.id, "B")

        //when
        userService.updateUserName(updateUser)

        //then
        val user = userRepository.findAll()[0]
        assertThat(user.id).isEqualTo(saveUser.id)
        assertThat(user.name).isEqualTo("B")
        assertThat(user.age).isNull()
    }

    @Test
    @DisplayName("S : 회원 삭제가 정상 작동한다")
    fun deleteUserTest() {
        //given
        userRepository.save(User("A", null))

        //when
        userService.deleteUser("A")

        //then
        assertThat(userRepository.findAll()).isEmpty()
    }

}