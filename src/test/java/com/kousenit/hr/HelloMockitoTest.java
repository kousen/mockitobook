package com.kousenit.hr;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HelloMockitoTest {
    @Mock
    private PersonRepository repository;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private HelloMockito helloMockito;

    @Test
    @DisplayName("Greet Admiral Hopper")
    void greetForPersonThatExists() {
        when(repository.findById(anyInt()))
                .thenReturn(Optional.of(new Person(1, "Grace", "Hopper", LocalDate.now())));
        when(translationService.translate("Hello, Grace, from Mockito!", "en", "en"))
                .thenReturn("Hello, Grace, from Mockito!");

        String greeting = helloMockito.greet(1, "en", "en");
        assertEquals("Hello, Grace, from Mockito!", greeting);

        InOrder inOrder = inOrder(repository, translationService);

        inOrder.verify(repository)
                .findById(anyInt());
        inOrder.verify(translationService)
                .translate(anyString(), eq("en"), eq("en"));
    }

    @Test
    @DisplayName("Greet a person not in the database")
    void greetForPersonThatDoesNotExist() {
        when(repository.findById(anyInt()))
                .thenReturn(Optional.empty());
        when(translationService.translate("Hello, World, from Mockito!", "en", "en"))
                .thenReturn("Hello, World, from Mockito!");

        String greeting = helloMockito.greet(100, "en", "en");
        assertEquals("Hello, World, from Mockito!", greeting);

        InOrder inOrder = inOrder(repository, translationService);

        inOrder.verify(repository)
                .findById(anyInt());
        inOrder.verify(translationService)
                .translate(anyString(), eq("en"), eq("en"));
    }

    @Test
    void greetWithDefaultTranslator() {
        PersonRepository mockRepo = mock(PersonRepository.class);
        when(mockRepo.findById(anyInt()))
                .thenReturn(Optional.of(new Person(1, "Grace", "Hopper", LocalDate.now())));
        HelloMockito helloMockito = new HelloMockito(mockRepo);
        String greeting = helloMockito.greet(1, "en", "en");
        assertThat(greeting).isEqualTo("Hello, Grace, from Mockito!");
    }

    @Test
    void greetWithMockedConstructor() {
        // Mock for repo (needed for HelloMockito constructor)
        PersonRepository mockRepo = mock(PersonRepository.class);
        when(mockRepo.findById(anyInt()))
                .thenReturn(Optional.of(new Person(1, "Grace", "Hopper", LocalDate.now())));

        // Mock for translator (instantiated inside HelloMockito constructor)
        try (MockedConstruction<DefaultTranslationService> ignored =
                     mockConstruction(DefaultTranslationService.class,
                             (mock, context) -> when(mock.translate(anyString(), anyString(), anyString()))
                                     .thenAnswer(invocation -> invocation.getArgument(0) + " (translated)"))) {

            // Instantiate HelloMockito with mocked repo and locally instantiated translator
            HelloMockito hello = new HelloMockito(mockRepo);
            String greeting = hello.greet(1, "en", "en");
            assertThat(greeting).isEqualTo("Hello, Grace, from Mockito! (translated)");
        }
    }

    @Test
    void testGetterAndSetter() {
        assertThat(helloMockito.getGreeting()).isNotNull();
        assertThat(helloMockito.getGreeting()).isEqualTo("Hello, %s, from Mockito!");

        helloMockito.setGreeting("Hi there, %s, from Mockito!");
        assertThat(helloMockito.getGreeting()).isEqualTo("Hi there, %s, from Mockito!");
    }

    @Test
    void helloMockitoWithExplicitStubs() {
        PersonRepository personRepo = new InMemoryPersonRepository();

        helloMockito = new HelloMockito(
                personRepo,
                new DefaultTranslationService()
        );

        // Save a person
        Person person = new Person(1, "Grace", "Hopper", LocalDate.now());
        personRepo.save(person);

        // Greet a user that exists
        String greeting = helloMockito.greet(1, "en", "en");
        assertThat(greeting).isEqualTo("Hello, Grace, from Mockito!");

        // Greet a user that does not exist
        greeting = helloMockito.greet(100, "en", "en");
        assertThat(greeting).isEqualTo("Hello, World, from Mockito!");
    }
}