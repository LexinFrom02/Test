import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;
import org.junit.jupiter.params.ParameterizedTest;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class ExerciseOneTest {

    @ParameterizedTest
    @CsvSource({
            //перед запуском тестов удостовериться, что на устройстве установлен часовой пояс -
            // "(UTC)Время в формате UTC"
            //задаем параметры для теста "UnixTimeStamp, дата в формате YYYY.M(MM).D(DD)TH(HH).M(MM).S(SS)
            "0, 1970.1.1T0:0:0",
            "1000, 1970.1.1T0:16:40",
            "-1000, 1969.12.31T23:43:20",
            "-2147483648, 1901.12.13T20:45:52",
            "2147483647, 2038.1.19T3:14:7",
            "1000000000, 2001.9.9T1:46:40",
            "999999999999, 33658.9.27T1:46:39",
            "-99999999999, -1199.2.15T14:13:21",
    })
    void shouldTestConversionWithPositiveValues(String unixTimeStamp, String date) {
        //запускаем WebDriver, открываем ресурс
        // при разворачивании тестов на другой локальной машине указать акутальный путь к artifacts/Test1.html
        open("file:///C:/QA/UnixTimeStamp/artifacts/Test1.html");
        //находим форму для ввода UnixTimeStamp, выедляем и удаляем значение по умолчанию
        $("[id='element_1']").doubleClick().sendKeys(Keys.DELETE);
        //задаем значение UnixTimeStamp
        $("[id='element_1']").setValue(unixTimeStamp);
        //выбираем форму где отображается конвертированное в дату значение
        $("[id='saveForm']").click();
        //"забираем" полностью значения элемента страницы (формы)
        String selectedText = $("[id='element_2']").toString();
        //выясняем содержит ли переменная selectedText значение переменной date
        boolean condition = selectedText.contains(date);
        //если содержится - assert выполняется - тест пройден, в противном случае тест падает
        Assertions.assertTrue(condition);
    }
}
