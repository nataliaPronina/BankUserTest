package ru.netology.bankusertest.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.bankusertest.test.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.bankusertest.test.DataGenerator.Registration.getUser;
import static ru.netology.bankusertest.test.DataGenerator.getRandomLogin;
import static ru.netology.bankusertest.test.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:7777");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should get Error message with unregistered user")
    void shouldErrorIfNotRegisteredActiveUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get Error message with blocked registered user")
    void shouldErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification").shouldHave(Condition.text("Ошибка! Пользователь заблокирован")).shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should get Error message if wrong login")
    void shouldErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"),
                Duration.ofSeconds(10)).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get Error message if wrong password")
    void shouldErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(getRandomLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id='error-notification").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"),
                Duration.ofSeconds(10)).shouldBe(Condition.visible);
    }
}
