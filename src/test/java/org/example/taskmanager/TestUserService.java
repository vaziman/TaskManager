package org.example.taskmanager;


import org.example.taskmanager.models.entities.User;
import org.example.taskmanager.models.enums.UserRole;
import org.example.taskmanager.repositories.UserRepository;
import org.example.taskmanager.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestUserService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("testEmail");
        user.setPassword("testPassword");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        boolean result = userService.createUser(user);
        verify(userRepository).save(user);

        assertTrue(result);

        assertEquals("encodedPassword", user.getPassword());
        assertTrue(user.isActive());
        assertTrue(user.getRoles().contains(UserRole.USER));

        assertEquals("testUser", user.getUsername());
        assertEquals("testEmail", user.getEmail());
    }

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("testEmail");
        user.setPassword("testPassword");
        user.setActive(true);
        user.getRoles().add(UserRole.USER);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User result = userService.findByEmail(user.getEmail());

        assertEquals(user, result);
    }

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("testEmail");
        user.setPassword("testPassword");
        user.setActive(true);
        user.getRoles().add(UserRole.USER);

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        User result = userService.findUserById(user.getId());

        assertEquals(user, result);
    }

  @Test
  public void testFindUserByUsername() {
    User user = new User();
    user.setUsername("testUser");
    user.setEmail("testEmail");
    user.setPassword("testPassword");
    user.setActive(true);
    user.getRoles().add(UserRole.USER);

    when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

    User result = userService.findUserByUsername(user.getUsername());

    assertEquals(user, result);
  }

}
