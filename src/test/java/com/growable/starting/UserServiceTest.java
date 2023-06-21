package com.growable.starting;

import com.growable.starting.model.Mentee;
import com.growable.starting.model.User;
import com.growable.starting.repository.UserRepository;
import com.growable.starting.service.MenteeServiceImpl;
import com.growable.starting.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private MenteeServiceImpl menteeService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void becomeMentee_ValidUser_ShouldSuccess() {
        // Prepare test data
        Long userId = 1L;
        User testUser = new User();
        testUser.setKakaoNickname("Test Nickname");
        testUser.setKakaoEmail("test@example.com");

        // Set up the behavior of UserRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // Call the method to test
        Mentee testMenteeDetails = new Mentee();
        testMenteeDetails.setStartingUrl("https://example.com");
        menteeService.becomeMentee(userId, testMenteeDetails);

        // Verify the results
        assertNotNull(testUser.getMentee());
        assertEquals(testMenteeDetails.getStartingUrl(), testUser.getMentee().getStartingUrl());
    }
}