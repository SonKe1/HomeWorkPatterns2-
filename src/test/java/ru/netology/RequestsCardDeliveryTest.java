package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.byText;

import java.time.Duration;


public class RequestsCardDeliveryTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldCardDelivery() {
        Information user = Generation.generateUser();

        $("[data-test-id='city'] input").val(user.getCity());
        $("[data-test-id='date'] input[class='input__control']").
                sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input[class='input__control']").setValue(Generation.generateDate(5));
        $("[data-test-id='name'] input").val(user.getName());
        $("[data-test-id='phone'] input").val(user.getPhone());
        $x(".//label[@data-test-id='agreement']").click();
        $(byText("Запланировать")).click();

        $("[data-test-id='success-notification'] div[class='notification__content']").
                shouldBe(visible, Duration.ofSeconds(15)).should(text("Встреча успешно запланирована на " +
                        Generation.generateDate(5)));
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] input[class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input[class='input__control']").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input[class='input__control']").setValue(Generation.generateDate(8));
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification']").shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification'] div[class='notification__content']").
                shouldBe(visible, Duration.ofSeconds(15)).should(text("Встреча успешно запланирована на " +
                        Generation.generateDate(8)));
    }

}
