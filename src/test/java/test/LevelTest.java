package test;

import java.util.List;
import java.util.Map;

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
            String imagePath = TestDataUtil.getResourcePath(TestDataUtil.getRequiredValue(data, "ImageName"));

            levelPage.addNewLevel(subjectName, levelNumber, grade, description, imagePath);

            if (levelPage.isLevelAlreadyExists()) {
                continue;
            }

            Assert.assertTrue(levelPage.verifyToastMessage("Level created successfully"), "Level creation failed for Level " + levelNumber);
        }
    }

    @Test(description = "TC_LEVEL_002 - Verify Level Active and Inactive Status")
    public void verifyLevelActiveInactiveStatus() {
        List<Map<String, String>> contentData = TestDataUtil.loadContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(contentPage.isSubjectsDisplayed(), "Subjects page is not displayed.");

        for (Map<String, String> data : contentData) {
            String subjectName = TestDataUtil.getRequiredValue(data, "SubjectName");

            Assert.assertTrue(contentPage.isSubjectDisplayed(subjectName), "Subject '" + subjectName + "' was not found.");

            levelPage.openSubject(subjectName);
            levelPage.setInactive();

            Assert.assertTrue(levelPage.verifyToastMessage("Level status changed to Inactive"), "Inactive status toast message was not displayed.");

            levelPage.waitForToastToDisappear();
            levelPage.setActive();

            Assert.assertTrue(levelPage.verifyToastMessage("Level status changed to Active"), "Active status toast message was not displayed.");

            levelPage.waitForToastToDisappear();
        }
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
            String editImagePath = TestDataUtil.getResourcePath(TestDataUtil.getRequiredValue(data, "Edit Image"));

            Assert.assertTrue(contentPage.isSubjectDisplayed(subjectName), "Subject '" + subjectName + "' was not found.");

            levelPage.openSubject(subjectName);
            levelPage.openEditLevel();

            Assert.assertTrue(levelPage.isEditFormDisplayed(), "Edit Level form was not displayed.");

            levelPage.editLevel(editLevelNumber, editLevelName, editDescription, editImagePath);

            Assert.assertTrue(levelPage.isEditFormDisplayed(), "Edit Level form is no longer displayed after update.");
        }
    }
}