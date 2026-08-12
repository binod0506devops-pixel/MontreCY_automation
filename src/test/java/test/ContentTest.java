package test;

import java.awt.AWTException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.CsvReader;
import utils.TestListener;

@Listeners(TestListener.class)
public class ContentTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(ContentTest.class);
    private static final String CONTENT_DATA_FILE = "testdata/contentmoduledata.csv";

    @Test(description = "TC_SUB_005 - Create Subject with Valid Name")
    public void verifyAddNewSubject() {
        Map<String, String> data = getContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );

        contentPage.clickAddNewSubject();

        contentPage.addNewSubject(
                data.get("SubjectName"),
                getResourcePath(data.get("ImageName"))
        );

        Assert.assertTrue(
                contentPage.verifyToastMessage("Subject created successfully"),
                "Subject creation success message was not displayed."
        );

        logger.info("TC_SUB_005 PASSED");
    }

    @Test(
            description = "TC_LEVEL_001 - Create Level with Valid Details",
            dependsOnMethods = "verifyAddNewSubject"
    )
    public void verifyAddNewLevel() {
        Map<String, String> data = getContentData();

        loginAndNavigate();
        contentPage.clickContent();

        contentPage.addNewLevel(
                data.get("SubjectName"),
                data.get("LevelNumber"),
                data.get("Grade"),
                data.get("Description"),
                getResourcePath(data.get("ImageName"))
        );

        logger.info("TC_LEVEL_001 PASSED");
    }

    @Test(
            description = "TC_CHAPTER_001 - Create Chapter with Valid Details",
            dependsOnMethods = {"verifyAddNewSubject", "verifyAddNewLevel"}
    )
    public void verifyAddNewChapter() throws InterruptedException, AWTException {
        Map<String, String> data = getContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );

        contentPage.addNewChapter(
                data.get("SubjectName"),
                data.get("Grade"),
                data.get("ChapterNumber"),
                data.get("ChapterTitle"),
                data.get("ChapterDescription"),
                getResourcePath(data.get("VideoName")),
                getResourcePath(data.get("CoverImageName")),
                data.get("Content"),
                data.get("LearningOutcome1"),
                data.get("LearningOutcome2"),
                data.get("LearningOutcome3"),
                data.get("TriviaQuestion"),
                data.get("TriviaOptionA"),
                data.get("TriviaOptionB"),
                data.get("TriviaOptionC"),
                data.get("TriviaOptionD"),
                data.get("TriviaContent")
        );

        logger.info("TC_CHAPTER_001 PASSED");
    }

    @Test(
            priority = 4,
            dependsOnMethods = "verifyAddNewChapter",
            description = "TC_007 - Verify Add Quiz Question"
    )
    public void verifyAddQuizQuestion() {
        Map<String, String> data = getContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );

        contentPage.addQuizQuestion(
                data.get("SubjectName"),
                data.get("Grade"),
                data.get("TriviaQuestion"),
                data.get("TriviaOptionA"),
                data.get("TriviaOptionB"),
                data.get("TriviaOptionC"),
                data.get("TriviaOptionD"),
                data.get("TriviaContent")
        );

        logger.info("TC_007 PASSED");
    }

    @Test(
            priority = 5,
            dependsOnMethods = "verifyAddQuizQuestion",
            description = "TC_ACTIVITY_001 - Add Activity"
    )
    public void verifyAddActivity() {
        Map<String, String> data = getContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );

        contentPage.addActivity(
                data.get("SubjectName"),
                data.get("Grade"),
                data.get("ActivityTitle"),
                data.get("ActivityText")
        );

        logger.info("TC_ACTIVITY_001 PASSED");
    }

    @Test(description = "TC_SUB_008 - Add Subject with Blank Name")
    public void verifySubjectNameMandatoryValidation() {
        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                contentPage.isSubjectsDisplayed(),
                "Subjects page not displayed."
        );

        contentPage.clickAddNewSubject();

        Assert.assertTrue(
                contentPage.validateSubjectNameRequired(),
                "Subject name required validation message was not displayed."
        );

        logger.info("TC_SUB_008 PASSED");
    }

    private Map<String, String> getContentData() {
        List<Map<String, String>> data = CsvReader.read(CONTENT_DATA_FILE);

        if (data == null || data.isEmpty()) {
            throw new IllegalStateException(
                    "No content data found in " + CONTENT_DATA_FILE
            );
        }

        return data.get(0);
    }

    private String getResourcePath(String fileName) {
        return Paths.get(
                System.getProperty("user.dir"),
                "src",
                "main",
                "resources",
                fileName
        ).toString();
    }
}