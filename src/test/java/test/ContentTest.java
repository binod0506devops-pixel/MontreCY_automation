package test;

import java.nio.file.Paths;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ContentPage;
import pages.LoginPage;
import utils.TestListener;

@Listeners(TestListener.class)
public class ContentTest extends BaseTest {

    private static final Logger logger =
            LoggerFactory.getLogger(ContentTest.class);

    @Test(
            priority = 1,
            description = "TC_004 - Verify Add New Subject"
    )
    public void verifyAddNewSubject() throws InterruptedException {

        logger.info("======================================================");
        logger.info("TEST CASE : TC_004 - Verify Add New Subject");
        logger.info("======================================================");

        LoginPage loginPage = new LoginPage(driver);
        ContentPage contentPage = new ContentPage(driver);

        HashMap<String, String> data = testData.get(0);

        logger.info("STEP 1 : Login");

        loginPage.login(
                data.get("Username"),
                data.get("Password")
        );



        logger.info("STEP 2 : Navigate to Content");

        contentPage.clickContent();



        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );

//        Thread.sleep(3000);

        Assert.assertEquals(
                contentPage.getSubjectsTitle(),
                "Subjects",
                "Page title mismatch."
        );

        Thread.sleep(5000);

        logger.info("STEP 3 : Add New Subject");

        String subjectName = data.get("SubjectName");

        String imagePath = Paths.get(
                System.getProperty("user.dir"),
                "src",
                "main",
                "resources",
                "Health.png"
        ).toString();

        logger.info("Subject Name : {}", subjectName);
        logger.info("Image Path : {}", imagePath);

        Thread.sleep(3000);

        contentPage.addNewSubject(
                subjectName,
                imagePath
        );

        Thread.sleep(5000);
        driver.navigate().refresh();
        logger.info("TEST RESULT : PASSED");
        logger.info("======================================================");
    }

    @Test(
            priority = 2,
            dependsOnMethods = "verifyAddNewSubject",
            description = "TC_005 - Verify Add New Level"
    )
    public void verifyAddNewLevel() throws InterruptedException {

        logger.info("======================================================");
        logger.info("TEST CASE : TC_005 - Verify Add New Level");
        logger.info("======================================================");

        LoginPage loginPage = new LoginPage(driver);
        ContentPage contentPage = new ContentPage(driver);

        HashMap<String, String> data = testData.get(0);

        logger.info("STEP 1 : Login");

        loginPage.login(
                data.get("Username"),
                data.get("Password")
        );

        Thread.sleep(3000);

        logger.info("STEP 2 : Navigate to Content");

        contentPage.clickContent();

        Thread.sleep(3000);

        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );

        Thread.sleep(3000);

        logger.info("STEP 3 : Add New Level");

        contentPage.addNewLevel(
                data.get("SubjectName"),
                data.get("LevelNumber"),
                data.get("Grade"),
                data.get("Description")
        );

        Thread.sleep(3000);

        logger.info("TEST RESULT : PASSED");
        logger.info("======================================================");
    }

    @Test(
            priority = 3,
            dependsOnMethods = "verifyAddNewLevel",
            description = "TC_006 - Verify Add New Chapter"
    )
    public void verifyAddNewChapter() throws InterruptedException {

        logger.info("======================================================");
        logger.info("TEST CASE : TC_006 - Verify Add New Chapter");
        logger.info("======================================================");

        LoginPage loginPage = new LoginPage(driver);
        ContentPage contentPage = new ContentPage(driver);

        HashMap<String, String> data = testData.get(0);


        // ==================================================
        // STEP 1 : LOGIN
        // ==================================================

        logger.info("STEP 1 : Login");

        loginPage.login(
                data.get("Username"),
                data.get("Password")
        );


        // ==================================================
        // STEP 2 : NAVIGATE TO CONTENT
        // ==================================================

        logger.info("STEP 2 : Navigate to Content");

        contentPage.clickContent();

        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );


        // ==================================================
        // STEP 3 : VIDEO PATH
        // ==================================================

        String videoPath = Paths.get(
                System.getProperty("user.dir"),
                "src",
                "main",
                "resources",
                data.get("VideoName")
        ).toString();


        // ==================================================
        // STEP 4 : COVER IMAGE PATH
        // ==================================================

        String coverImagePath = Paths.get(
                System.getProperty("user.dir"),
                "src",
                "main",
                "resources",
                data.get("CoverImageName")
        ).toString();


        // ==================================================
        // STEP 5 : ADD NEW CHAPTER
        // ==================================================

        logger.info("STEP 5 : Add New Chapter");

        contentPage.addNewChapter(
                data.get("SubjectName"),
                data.get("Grade"),
                data.get("ChapterNumber"),
                data.get("ChapterTitle"),
                data.get("ChapterDescription"),
                videoPath,
                coverImagePath,
                data.get("Content"),
                data.get("LearningOutcome1"),
                data.get("LearningOutcome2"),
                data.get("LearningOutcome3")
        );


        logger.info("TEST RESULT : PASSED");
        logger.info("======================================================");
    }

//    @Test(
//            priority = 4,
//            dependsOnMethods = "verifyAddNewChapter",
//            description = "TC_007 - Verify Add Quiz Question"
//    )
//    public void verifyAddQuizQuestion() throws InterruptedException {
//
//        logger.info("======================================================");
//        logger.info("TEST CASE : TC_007 - Verify Add Quiz Question");
//        logger.info("======================================================");
//
//        LoginPage loginPage = new LoginPage(driver);
//        ContentPage contentPage = new ContentPage(driver);
//
//        HashMap<String, String> data = testData.get(0);
//
//
//        // STEP 1 : LOGIN
//
//        logger.info("STEP 1 : Login");
//
//        loginPage.login(
//                data.get("Username"),
//                data.get("Password")
//        );
//
//
//        // STEP 2 : CONTENT
//
//        logger.info("STEP 2 : Navigate to Content");
//
//        contentPage.clickContent();
//
//        Assert.assertTrue(
//                contentPage.isSubjectsDisplayed(),
//                "Subjects page not displayed."
//        );
//
//
//        // STEP 3 : ADD QUIZ QUESTION
//
//        logger.info("STEP 3 : Add Quiz Question");
//
//        contentPage.addQuizQuestion(
//                data.get("SubjectName"),
//                data.get("Grade"),
//                data.get("Question"),
//                data.get("Option1"),
//                data.get("Option2"),
//                data.get("Option3"),
//                data.get("Option4"),
//                data.get("Explanation")
//        );
//
//
//        logger.info("TEST RESULT : PASSED");
//
//        logger.info("======================================================");
//    }

    @Test(
            priority = 4,
            dependsOnMethods = "verifyAddNewChapter",
            description = "TC_007 - Verify Add Quiz Question"
    )
    public void verifyAddQuizQuestion() throws InterruptedException {

        logger.info("======================================================");
        logger.info("TEST CASE : TC_007 - Verify Add Quiz Question");
        logger.info("======================================================");

        LoginPage loginPage = new LoginPage(driver);
        ContentPage contentPage = new ContentPage(driver);

        HashMap<String, String> data = testData.get(0);


        // STEP 1 : LOGIN

        logger.info("STEP 1 : Login");

        loginPage.login(
                data.get("Username"),
                data.get("Password")
        );


        // STEP 2 : CONTENT

        logger.info("STEP 2 : Navigate to Content");

        contentPage.clickContent();

        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );


        // STEP 3 : ADD QUIZ QUESTION

        logger.info("STEP 3 : Add Quiz Question");

        contentPage.addQuizQuestion(
                data.get("SubjectName"),
                data.get("Grade"),
                data.get("Question"),
                data.get("Option1"),
                data.get("Option2"),
                data.get("Option3"),
                data.get("Option4"),
                data.get("Explanation")
        );


        logger.info("TEST RESULT : PASSED");
        logger.info("======================================================");
    }
}