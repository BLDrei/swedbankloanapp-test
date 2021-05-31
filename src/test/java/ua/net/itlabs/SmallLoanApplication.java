package ua.net.itlabs;

import com.codeborne.selenide.Condition;
import org.junit.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class SmallLoanApplication {

    @Test
    public void testFillLoanApplication() {

        open("https://www.swedbank.ee/private/credit/loans/newSmall/small_loan/small_loan");
        rejectCookies();
        $("#allow-apply-action").click();
        checkStepNumber(1);
        setLoanParameters();
        moveToNextStep();

        checkStepNumber(2);
        fillYourParameters();
        moveToNextStep();

        checkStepNumber(3);
        $("#sendCaptionMessageDiv > p").shouldHave(Condition.text("Teie väikelaenu taotlus limiidiga 300 EUR on edastamiseks valmis."));
        moveToPrevStep();

        checkStepNumber(2);

        fillKaasalaenujaParameters();
        moveToNextStep();
        checkLastStepInfo();
    }

    private static void checkLastStepInfo() {
        $(byText("Limiit")).parent().find("td:nth-child(2)").shouldHave(Condition.text("300"));
        $(byText("Laenu periood")).parent().find("td:nth-child(2)").shouldHave(Condition.text("6"));
        $(byText("Netosissetulek")).parent().find("td:nth-child(2)").shouldHave(Condition.text("666" + ".00"));
        $(byText("Telefon")).parent().find("td:nth-child(2)").shouldHave(Condition.text("+37258941797"));
    }
    private static void fillKaasalaenujaParameters() {
        checkStepNumber(2);
        $(byText("Lisa kaastaotleja")).click();
        $("input#co1clientFirstName").setValue("Max");
        $("input#co1clientLastName").setValue("Mustermann");
        $("input#co1clientRegNr").setValue("48102260261");
    }
    private static void rejectCookies() {
        if ($("div.ui-cookie-consent__controls").exists()) {
            $("div.ui-cookie-consent__controls > button.ui-cookie-consent__manage-button").click();
            $("div.ui-cookie-consent__controls > button.ui-cookie-consent__save-choice-button").click();
        }
    }
    private static void checkStepNumber(int n) {
        $("li.agreementStep" + n).shouldHave(Condition.attribute("class", "agreementStepActive agreementStep" + n + " active"));
    }
    private static void moveToNextStep() {
        $("#B_next").click();
    }
    private static void moveToPrevStep() {
        $("#B_prev").click();
    }
    private static void setLoanParameters() {
        checkStepNumber(1);
        $("#paymentDay").click();
        $("#paymentDay").find("option[value='10']").click();
        $("#incomeOwner").setValue("199").pressEnter();
        $("div.minIncomeWarning").shouldBe(Condition.visible);
        $("#incomeOwner").setValue("666").pressEnter();
        $("div.minIncomeWarning").shouldNotBe(Condition.visible);

    }

    private static void fillYourParameters() {
        checkStepNumber(2);
        $("#clientFirstName").setValue("Aigar");
        $("#clientLastName").setValue("Kivisaar");
        $("#clientRegNr").setValue("50305102758");
        $("#education").click();
        $("#education").find(byText("Kutseharidus")).click();
        $("#jobPosition").click();
        $("#jobPosition").find(byText("Vanemspetsialist")).click();
        $("#jobFieldDiv").click();
        $("#jobFieldDiv").find(byText("Infotehnoloogia")).click();
        $("#jobPeriod").click();
        $("#jobPeriod").find(byText("kuni 4 aastat")).click();
        $("input#phone_1").setValue("58941797");
    }

}
