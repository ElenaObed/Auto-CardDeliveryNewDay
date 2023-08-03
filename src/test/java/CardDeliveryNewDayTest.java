import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryNewDayTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldTestNewDate() {

        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        String date = DataGenerator.generaterDate(3);
        String secondDate = DataGenerator.generaterDate(7);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + date)).shouldBe(visible);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondDate);
        $(byText("Запланировать")).click();
        $(byText("Необходимо подтверждение")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .notification__content"). shouldHave(exactText("У вас уже запланирована встреча на другую дату. Перепланировать?")). shouldBe(visible, Duration.ofSeconds(15));;
        $(".button__text").shouldHave(exactText("Перепланировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(25));
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + secondDate)).shouldBe(visible, Duration.ofSeconds(15));

    }
}

