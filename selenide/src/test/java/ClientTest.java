import com.codeborne.selenide.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AuthorPage;
import pages.LibraryPage;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ClientTest {

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        baseUrl = "http://localhost:3000";
        assertionMode = AssertionMode.STRICT;
        holdBrowserOpen = false;
        timeout = 4000;
        browserSize = "1920x1080";
        screenshots = true;
        pollingInterval = 200;
        headless = false;
    }

    @BeforeMethod
    public void beforeMethod() {
        Selenide.open("");
    }

    @Test
    public void testLibraryHeader() {
        $(LibraryPage.libraryHeader).shouldHave(Condition.text("Getting started with React testing library"));
    }

    @Test
    public void testLinksExistence() {
        ElementsCollection authors = $$(LibraryPage.songAuthors);
        ElementsCollection links = $$(LibraryPage.authorsLinks);

        authors.shouldHave(CollectionCondition.sizeLessThanOrEqual(15 * 5));
        authors.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(15));
        authors.shouldHave(CollectionCondition.size(links.size()));
    }

    @Test
    public void testLink() {
        ElementsCollection links = $$(LibraryPage.authorsLinks);
        String name = links.get(0).innerText();

        links.get(0).click();
        $(AuthorPage.authorHeader).shouldHave(Condition.text("Getting started with React testing library"));
        $(AuthorPage.authorName).shouldHave(Condition.text(name));
        $$(AuthorPage.songsNames).shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
    }

    @Test
    public void test() {
        ElementsCollection songs = $$(LibraryPage.librarySongs);
        songs.shouldHave(CollectionCondition.size(15));

        for (SelenideElement song : songs) {
            ElementsCollection authors = song.$$(LibraryPage.songAuthors);
            authors.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
            authors.shouldHave(CollectionCondition.sizeLessThanOrEqual(5));
        }

    }

    @Test
    public void testAuthorHeader() {
        Selenide.open("/author/1");
        $(AuthorPage.authorHeader).shouldHave(Condition.text("Getting started with React testing library"));
    }

}