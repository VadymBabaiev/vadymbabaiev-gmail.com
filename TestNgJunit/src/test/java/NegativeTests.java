import HTML_Elements.LoginPage;
import Pages.HomePage;
import Pages.ResultPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NegativeTests {
    private static final String URL = "https://www.baseball-reference.com/";
    private static final String NOT_EXIST_PLAYER = "Vadym Babaiev";
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @Test
    public void searchNonExistentPlayer() {
        driver.navigate().to(URL);
        HomePage page = new HomePage(driver);
        ResultPage resultPage = new ResultPage(driver);
        page.searchPlayer(NOT_EXIST_PLAYER);
        assertThat(resultPage.zeroHits()).isTrue();
    }

    @Test
    public void emptySearch() {
        driver.navigate().to(URL);
        HomePage page = new HomePage(driver);
        ResultPage resultPage = new ResultPage(driver);
        page.emptySearch();
        assertThat(resultPage.successfulSearches()).isTrue();
    }

    @Test
    public void signInWithoutParameters() {
        driver.navigate().to(URL);
        HomePage page = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        page.logInWithoutParameters();
        assertThat(loginPage.signIn()).isTrue();
    }

    @Test
    public void createAccountWithEmptyFields() {
        driver.navigate().to(URL);
        HomePage page = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        page.clickCreateAccount();
        loginPage.signUp();
        assertThat(loginPage.minimumPasswordMassage()).isTrue();
    }

    @Test
    public void checkAlertOnClickGoButtonWithoutParameters() {
        driver.navigate().to(URL);
        HomePage page = new HomePage(driver);
        page.clickGoButton();
        assertThat(page.selectAnOptionForEachAlert()).isTrue();
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

}

