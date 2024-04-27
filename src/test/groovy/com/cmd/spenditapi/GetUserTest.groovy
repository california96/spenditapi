package com.cmd.spenditapi

import com.cmd.spenditapi.models.User
import com.cmd.spenditapi.repository.UserRepository
import com.cmd.spenditapi.services.UserService

import java.util.concurrent.ExecutionException

import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*

import java.util.concurrent.CompletableFuture

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetUserTest {

    @Mock
    private UserRepository userRepository

    @InjectMocks
    private UserService userService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testUserByIdExisting() {
        int userId = 1
        User user = new User()
        user.setUserID(userId)
        user.setFirstName("Admin")
        user.setLastName("Istrator")
        when(userRepository.findById(userId)).thenReturn(Optional.of(user))

        CompletableFuture<User> userFuture = userService.getUserById(userId)

        try {
            User retrievedUser = userFuture.get()
            assertNotNull(retrievedUser)
            assertEquals(user.getUserID(), retrievedUser.getUserID())
            assertEquals(user.getFirstName(), retrievedUser.getFirstName())
            assertEquals(user.getLastName(), retrievedUser.getLastName())

        } catch (Exception e) {
            fail("Exception should not be thrown")
        }
    }

    @Test
    void testUserByIdNotExisting() {
        int userId = 456

        when(userRepository.findById(userId)).thenReturn(Optional.empty())

        CompletableFuture<User> userFuture = userService.getUserById(userId)

        assertThrows(ExecutionException.class, () -> {
            userFuture.get()
        })
    }
}