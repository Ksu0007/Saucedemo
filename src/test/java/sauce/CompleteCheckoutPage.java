package sauce;

import core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CompleteCheckoutPage extends BasePage {

    @FindBy (css = ".complete-header")
    private WebElement thankYouMessage;

    public CompleteCheckoutPage() {
        PageFactory.initElements(driver, this);
    }

    public String getThankYouMessageText() {
        String thankYou = thankYouMessage.getText();
        return thankYou;
    }
}


