package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ClientCreationTest {


    @Test
    void shouldLogToAccountIfTheUserIsActive() {

        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");
        RegistrationInfo registeredUser = DataGenerator.getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(byText("Личный кабинет")).should(visible);

    }

    @Test
    void shouldGetNotificationIfUsernameAndPasswordInvalid() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        RegistrationInfo NotRegisteredUser = DataGenerator.getUser("active");
        $("[data-test-id=login] input").setValue(NotRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(NotRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));

    }

    @Test
    void shouldGetNotificationIfUserBlocked() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        RegistrationInfo BlockedUser = DataGenerator.getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(BlockedUser.getLogin());
        $("[data-test-id=password] input").setValue(BlockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));

    }


}
