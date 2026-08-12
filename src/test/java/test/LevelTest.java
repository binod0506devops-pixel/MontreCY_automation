package test;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LevelPage;
import utils.TestDataUtil;
import utils.TestListener;

@Listeners(TestListener.class)
public class LevelTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LevelTest.class);
    private LevelPage levelPage;

    @BeforeMethod
    public void initializePages() {
        levelPage = new LevelPage(driver);
    }

    @Test(description = "TC_LEVEL_001 - Create Level with Valid Details")
    public void verifyAddNewLevel() {
        List<Map<String, String>> contentData = TestDataUtil.loadContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(contentPage.isSubjectsDisplayed(), "Subjects page is not displayed.");

        for (Map<String, String> data : contentData) {
            String subjectName = TestDataUtil.getRequiredValue(data, "SubjectName");
            String levelNumber = TestDataUtil.getRequiredValue(data, "LevelNumber");
            String grade = TestDataUtil.getRequiredValue(data, "Grade");
            String description = TestDataUtil.getRequiredValue(data, "Description");
            String imagePath = TestDataUtil.getResourcePath(
                    TestDataUtil.getRequiredValue(data, "ImageName")
            );

            if (!contentPage.isSubjectDisplayed(subjectName)) {
                contentPage.clickAddNewSubject();
                contentPage.addNewSubject(subjectName, imagePath);

                Assert.assertTrue(
                        contentPage.verifyToastMessage("Subject created successfully")
                                || contentPage.verifyToastMessage(
                                "A subject with this title already exists. Please choose a different title."
                        ),
                        "Subject creation result was not displayed."
                );
            }

            levelPage.addNewLevel(subjectName, levelNumber, grade, description, imagePath);

            if (levelPage.isLevelAlreadyExists()) {
                logger.warn("Level [{}] already exists for Subject [{}]", levelNumber, subjectName);
                continue;
            }

            Assert.assertTrue(
                    levelPage.verifyToastMessage("Level created successfully"),
                    "Level creation success message was not displayed for Level " + levelNumber
            );
        }

        logger.info("TC_LEVEL_001 PASSED");
    }

    @Test(description = "TC_LEVEL_002 - Verify Level Active and Inactive Status")
    public void verifyLevelActiveInactiveStatus() {
        List<Map<String, String>> contentData = TestDataUtil.loadContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(contentPage.isSubjectsDisplayed(), "Subjects page is not displayed.");

        for (Map<String, String> data : contentData) {
            String subjectName = TestDataUtil.getRequiredValue(data, "SubjectName");

            Assert.assertTrue(
                    contentPage.isSubjectDisplayed(subjectName),
                    "Subject '" + subjectName + "' was not found."
            );

            levelPage.openSubject(subjectName);
            levelPage.setInactive();

            Assert.assertTrue(
                    levelPage.verifyToastMessage("Level status changed to Inactive"),
                    "Inactive status toast message was not displayed."
            );

            levelPage.waitForToastToDisappear();
            levelPage.setActive();

            Assert.assertTrue(
                    levelPage.verifyToastMessage("Level status changed to Active"),
                    "Active status toast message was not displayed."
            );

            levelPage.waitForToastToDisappear();
        }

        logger.info("TC_LEVEL_002 PASSED");
    }

    @Test(description = "TC_LEVEL_003 - Edit Level Details")
    public void verifyEditLevelDetails() {
        List<Map<String, String>> contentData = TestDataUtil.loadContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(contentPage.isSubjectsDisplayed(), "Subjects page is not displayed.");

        for (Map<String, String> data : contentData) {
            String subjectName = TestDataUtil.getRequiredValue(data, "SubjectName");
            String editLevelNumber = TestDataUtil.getRequiredValue(data, "EditLevelNumber");
            String editLevelName = TestDataUtil.getRequiredValue(data, "Edit Level Name");
            String editDescription = TestDataUtil.getRequiredValue(data, "Edit Description");
            String editImagePath = TestDataUtil.getResourcePath(
                    TestDataUtil.getRequiredValue(data, "Edit Image")
            );

            Assert.assertTrue(
                    contentPage.isSubjectDisplayed(subjectName),
                    "Subject '" + subjectName + "' was not found."
            );

            levelPage.openSubject(subjectName);
            levelPage.openEditLevel();

            Assert.assertTrue(
                    levelPage.isEditFormDisplayed(),
                    "Edit Level form was not displayed."
            );

            levelPage.editLevel(
                    editLevelNumber,
                    editLevelName,
                    editDescription,
                    editImagePath
            );

            Assert.assertTrue(
                    levelPage.isEditFormDisplayed(),
                    "Edit Level form is no longer displayed after update."
            );
        }

        logger.info("TC_LEVEL_003 PASSED");
    }
}