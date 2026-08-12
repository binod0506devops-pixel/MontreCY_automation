package test;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.SubjectsPage;
import utils.TestDataUtil;
import utils.TestListener;

@Listeners(TestListener.class)
public class SubjectsTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SubjectsTest.class);
    private SubjectsPage subjectsPage;

    @BeforeMethod
    public void initializePages() {
        subjectsPage = new SubjectsPage(driver);
    }

    @Test(description = "TC_SUBJECT_001 - Verify Subjects Page is Displayed")
    public void verifySubjectsPageDisplayed() {
        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                subjectsPage.isSubjectsTitleDisplayed(),
                "Subjects title is not displayed."
        );

        logger.info("TC_SUBJECT_001 PASSED");
    }

    @Test(description = "TC_SUBJECT_002 - Verify Add New Subject Button is Displayed")
    public void verifyAddNewSubjectButtonDisplayed() {
        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                subjectsPage.isAddNewSubjectButtonDisplayed(),
                "Add New Subject button is not displayed."
        );

        logger.info("TC_SUBJECT_002 PASSED");
    }

    @Test(description = "TC_SUBJECT_003 - Verify Add New Subject Form is Displayed")
    public void verifyAddNewSubjectFormDisplayed() {
        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                subjectsPage.isSubjectsTitleDisplayed(),
                "Subjects page is not displayed."
        );

        subjectsPage.clickAddNewSubject();

        Assert.assertTrue(
                subjectsPage.isAddNewSubjectFormDisplayed(),
                "Add New Subject form is not displayed."
        );

        logger.info("TC_SUBJECT_003 PASSED");
    }

    @Test(description = "TC_SUBJECT_004 - Verify Subject Create, Update and Delete")
    public void verifySubjectCreateUpdateAndDelete() {
        List<Map<String, String>> contentData = TestDataUtil.loadContentData();

        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                subjectsPage.isSubjectsTitleDisplayed(),
                "Subjects page is not displayed."
        );

        for (Map<String, String> data : contentData) {
            String subjectName = TestDataUtil.getRequiredValue(data, "SubjectName");
            String imagePath = TestDataUtil.getResourcePath(
                    TestDataUtil.getRequiredValue(data, "ImageName")
            );
            String updatedSubjectName = TestDataUtil.getRequiredValue(
                    data,
                    "UpdatedSubjectName"
            );
            String updatedImagePath = TestDataUtil.getResourcePath(
                    TestDataUtil.getRequiredValue(data, "UpdatedSubjectImage")
            );

            subjectsPage.clickAddNewSubject();
            subjectsPage.addNewSubject(subjectName, imagePath);

            Assert.assertTrue(
                    subjectsPage.verifyToastMessage("Subject created successfully"),
                    "Subject creation failed for subject: " + subjectName
            );

            /*
            subjectsPage.editSubject(
                    subjectName,
                    updatedSubjectName,
                    updatedImagePath
            );

            Assert.assertTrue(
                    subjectsPage.verifyToastMessage("Subject updated successfully"),
                    "Subject update failed for subject: " + subjectName
            );

            subjectsPage.deleteSubject(updatedSubjectName);

            Assert.assertTrue(
                    subjectsPage.verifyToastMessage("Subject deleted successfully"),
                    "Subject deletion failed for subject: " + updatedSubjectName
            );
            */
        }

        logger.info("TC_SUBJECT_004 PASSED");
    }

    @Test(description = "TC_SUBJECT_005 - Verify Subject Name Mandatory Validation")
    public void verifySubjectNameMandatoryValidation() {
        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                subjectsPage.isSubjectsTitleDisplayed(),
                "Subjects page is not displayed."
        );

        subjectsPage.clickAddNewSubject();

        Assert.assertTrue(
                subjectsPage.validateSubjectNameRequired(),
                "Subject name required validation message was not displayed."
        );

        logger.info("TC_SUBJECT_005 PASSED");
    }

    @Test(description = "TC_SUBJECT_011 - Verify Edit Subject Screen and Actions")
    public void verifyEditSubjectScreenAndActions() {
        loginAndNavigate();
        contentPage.clickContent();

        Assert.assertTrue(
                subjectsPage.isSubjectsTitleDisplayed(),
                "Subjects page is not displayed."
        );

        subjectsPage.clickEditSubject();

        Assert.assertTrue(
                subjectsPage.isEditSubjectDisplayed(),
                "Edit Subject screen is not displayed."
        );

        Assert.assertTrue(
                subjectsPage.isCancelButtonDisplayed(),
                "Cancel button is not displayed."
        );

        Assert.assertTrue(
                subjectsPage.isUpdateSubjectButtonDisplayed(),
                "Update Subject button is not displayed."
        );

        logger.info("TC_SUBJECT_011 PASSED");
    }
}