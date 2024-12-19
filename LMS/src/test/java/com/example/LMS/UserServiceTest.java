package com.example.LMS;

import com.example.LMS.models.Profile;
import com.example.LMS.models.User;
import com.example.LMS.models.loginData;
import com.example.LMS.repositories.UserRepository;
import com.example.LMS.repositories.profileRepository;
import com.example.LMS.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private profileRepository profileRepository;

    @InjectMocks
    private UserService userService;


    //                                             register
    @Test
    public void testSuccessfulRegister() {
        User user = new User();
        user.setName("dina");
        user.setEmail("dina@gmail.com");
        user.setPassword("password");
        user.setRole("ADMIN");

        when(userRepository.findByNameAndEmail(user.getName(), user.getEmail()))
                .thenReturn(Optional.empty());
//Instead of performing the real save operation
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<Object> response = userService.register(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        User savedUser = (User) response.getBody();
        assertNotNull(savedUser);
        assertEquals("dina", savedUser.getName());
        assertEquals("dina@gmail.com", savedUser.getEmail());
        assertEquals("password", savedUser.getPassword());
        assertEquals("ADMIN", savedUser.getRole());

    }

    @Test
    void testRegister_EmptyName() {
        User user = new User();
        user.setName("");
        user.setEmail("empty@gmail.com");
        user.setPassword("password");
        user.setRole("Admin");

        ResponseEntity<Object> response = userService.register(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Name is required.", response.getBody());
    }


    @Test
    void testRegister_EmptyRole() {
        User user = new User();
        user.setName("dina");
        user.setEmail("empty@gmail.com");
        user.setPassword("password");
        user.setRole("");

        ResponseEntity<Object> response = userService.register(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Role is required.", response.getBody());
    }


    @Test
    void testRegister_EmptyEmail() {
        User user = new User();
        user.setName("dina");
        user.setEmail("");
        user.setPassword("password");
        user.setRole("Admin");

        ResponseEntity<Object> response = userService.register(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("mail is required.", response.getBody());
    }


    @Test
    void testRegister_EmptyPassword() {
        User user = new User();
        user.setName("dina");
        user.setEmail("empty@gmail.com");
        user.setPassword("");
        user.setRole("Admin");

        ResponseEntity<Object> response = userService.register(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Password is required.", response.getBody());
    }


    @Test
    void testRegister_duplicateUser() {
        User user = new User();
        user.setName("dina");
        user.setEmail("empty@gmail.com");
        user.setPassword("password");
        user.setRole("Admin");


        // this behavior ensures that the register method in UserService behaves as if a user with the given name and email already exists in the database.
        when(userRepository.findByNameAndEmail("dina", "empty@gmail.com"))
                .thenReturn(Optional.of(new User()));

        ResponseEntity<Object> response = userService.register(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User with the same name and  email already exists.", response.getBody());
    }





    //                 ----------------------------       login  -------------------------

    @Test
    public void testLogin_SuccessfulLogin() {
        // login
        loginData loginRequest = new loginData();
        loginRequest.setUsername("dina");
        loginRequest.setEmail("dina@gmail.com");
        loginRequest.setPassword("password123");

        // in the database
        User existingUser = new User();
        existingUser.setName("dina");
        existingUser.setEmail("dina@gmail.com");
        existingUser.setPassword("password123");

        when(userRepository.findByNameAndEmail("dina", "dina@gmail.com")).thenReturn(Optional.of(existingUser));

        ResponseEntity<String> response = userService.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful!", response.getBody());
    }




    @Test
    public void testLogin_FailedLogin_IncorrectPassword() {
        // Initialize mocks


        // Mock login data
        loginData loginRequest = new loginData();
        loginRequest.setUsername("john_doe");
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("wrongPassword");

        // Mock the existing user in the database
        User existingUser = new User();
        existingUser.setName("john_doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("password123");

        // Mock repository behavior
        when(userRepository.findByNameAndEmail("john_doe", "john.doe@example.com")).thenReturn(Optional.of(existingUser));

        // Call the login method
        ResponseEntity<String> response = userService.login(loginRequest);

        // Verify behavior
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect password! Please try again.", response.getBody());
    }

    @Test
    public void testLogin_FailedLogin_UserNotFound() {

        loginData loginRequest = new loginData();
        loginRequest.setUsername("non_existent_user");
        loginRequest.setEmail("non.existent@example.com");
        loginRequest.setPassword("password123");

        when(userRepository.findByNameAndEmail("non_existent_user", "non.existent@example.com")).thenReturn(Optional.empty());

        ResponseEntity<String> response = userService.login(loginRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("check that username correct or email or  register first .", response.getBody());
    }





//    -----------------------------  profile --------------------

    @Test
    void testEditProfile_ProfileNotFound() {
        Profile profile = new Profile();
        User user = new User();
        user.setId(1);
        profile.setUser(user);

        when(profileRepository.getProfileById(1)).thenReturn(null);

        ResponseEntity<Object> response = userService.editProfile(profile);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Profile not found for user ID: 1", response.getBody());
    }

    @Test
    void testEditProfile_DuplicateNameAndEmail() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("JohnDoe");
        user1.setEmail("john.doe@example.com");

        Profile profile1 = new Profile();
        profile1.setId(1);
        profile1.setUser(user1);


        User user2 = new User();
        user2.setId(2);
        user2.setName("JaneDoe");
        user2.setEmail("jane.doe@example.com");

        // New profile data to be updated
        Profile updatedProfile = new Profile();
        User updatedUser = new User();
        updatedUser.setId(1); // Same as User1
        updatedUser.setName("JaneDoe"); // Conflicting name
        updatedUser.setEmail("jane.doe@example.com"); // Conflicting email
        updatedProfile.setUser(updatedUser);

        when(profileRepository.getProfileById(1)).thenReturn(profile1); // Existing profile
        when(userRepository.findByNameAndEmail("JaneDoe", "jane.doe@example.com"))
                .thenReturn(Optional.of(user2)); // Conflicting user


        ResponseEntity<Object> response = userService.editProfile(updatedProfile);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(
                "The name 'JaneDoe' and email 'jane.doe@example.com' are already in use by another user. Please choose a different name or email.",
                response.getBody()
        );

        verify(userRepository, never()).save(any(User.class));
        verify(profileRepository, never()).save(any(Profile.class));}


    @Test
    public void testEditProfile_SuccessfulEdit() {


        //existing profile
        User user1 = new User();
        user1.setId(1);
        user1.setName("JohnDoe");
        user1.setEmail("john.doe@example.com");

        Profile profile1 = new Profile();
        profile1.setId(1);
        profile1.setUser(user1);
        profile1.setAddress("123 Main St");
        profile1.setPhoneNumber("1234567890");

        // New profile  updated
        Profile updatedProfile = new Profile();
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setName("JohnUpdated");
        updatedUser.setEmail("john.updated@example.com");
        updatedProfile.setUser(updatedUser);
        updatedProfile.setAddress("456 Updated Ave");
        updatedProfile.setPhoneNumber("9876543210");

        when(profileRepository.getProfileById(1)).thenReturn(profile1); // Existing profile
        when(userRepository.findByNameAndEmail("JohnUpdated", "john.updated@example.com"))
                .thenReturn(Optional.empty()); // No conflicting user

        ResponseEntity<Object> response = userService.editProfile(updatedProfile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Profile savedProfile = (Profile) response.getBody();
        assertNotNull(savedProfile);
        assertEquals("JohnUpdated", savedProfile.getUser().getName());
        assertEquals("john.updated@example.com", savedProfile.getUser().getEmail());
        assertEquals("456 Updated Ave", savedProfile.getAddress());
        assertEquals("9876543210", savedProfile.getPhoneNumber());

    }

}