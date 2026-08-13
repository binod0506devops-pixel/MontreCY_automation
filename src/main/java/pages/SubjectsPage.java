package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SubjectsPage {

    private final WebDriver driver;
    private final CommonActions commonActions;

    private final By dashboardText = By.xpath("//*[normalize-space()='Dashboard']");
    private final By contentMenu = By.xpath("//*[normalize-space()='Content']");

    private final By subjectsTitle = By.xpath("//*[normalize-space()='Subjects']");
    private final By addNewSubjectButton = By.xpath("//button[normalize-space()='Add New Subject']");
    private final By editSubjectButton = By.xpath("//div[@class='subjects-container']//div[1]//div[1]//div[1]//button[1]//span[2]");
    private final By editSubjectTitle = By.xpath("//h2[normalize-space()='Edit Subject']");
    private final By updateSubjectButton = By.xpath("//span[normalize-space()='Update Subject']");

    private final By cancelButton = By.xpath("//span[normalize-space()='Cancel']");
    private final By subjectNameInput = By.xpath("//input[@placeholder='Subject Name' or @name='subjectName' or @type='text']");
    private final By fileInput = By.xpath("//input[@type='file']");
    private final By uploadedImage = By.xpath("//img[contains(@src,'blob:') or contains(@src,'data:image')]");
    private final By addSubjectButton = By.xpath("//div[contains(@class,'modal-actions')]//button[@type='button' and .//span[normalize-space()='Add Subject']]");
    private final By toastMessage = By.xpath("//div[@class='toast-message']");
    private final By subjectNameRequiredError = By.xpath("//span[@class='validation-error']");
    private final By addNewSubjectTitle = By.xpath("//h2[normalize-space()='Add New Subject']");

    private final By subjectImageInput = By.xpath("//input[@type='file']");

    public SubjectsPage(WebDriver driver) {
        this.driver = driver;
        this.commonActions = new CommonActions(driver);
    }

    public boolean isSubjectsTitleDisplayed() {
        return commonActions.isDisplayed(subjectsTitle);
    }

    public boolean isAddNewSubjectFormDisplayed() {
        return commonActions.isDisplayed(addNewSubjectTitle);
    }

    public boolean isAddNewSubjectButtonDisplayed() {
        return commonActions.isDisplayed(addNewSubjectButton);
    }

    public void waitForDashboard() {
        commonActions.waitForElementToBeDisplayed(dashboardText);
    }

    public void clickContent() {
        commonActions.click(contentMenu);
    }

    public void clickAddNewSubject() {
        commonActions.click(addNewSubjectButton);
    }

    public void addNewSubject(String subjectName, String imagePath) {
        commonActions.enterText(subjectNameInput, subjectName);
        commonActions.uploadFile(fileInput, uploadedImage, imagePath);
        commonActions.sleepTwoSeconds();
        commonActions.click(addSubjectButton);
        commonActions.sleepTwoSeconds();


    }

    public void editSubject(String subjectName, String updatedSubjectName, String updatedImagePath) {
        commonActions.click(getEditSubjectButton(subjectName));
        commonActions.click(editSubjectButton);
        commonActions.uploadFile(subjectImageInput, null, updatedImagePath);
        commonActions.enterText(subjectNameInput, updatedSubjectName);
        commonActions.click(updateSubjectButton);
    }

    public boolean verifyToastMessage(String expectedMessage) {
        return commonActions.verifyText(toastMessage, expectedMessage);
    }

    public boolean validateSubjectNameRequired() {
        return commonActions.validateField(
                addSubjectButton,
                subjectNameRequiredError,
                "Subject name is required"
        );
    }

    public boolean isCancelButtonDisplayed() {
        return commonActions.isDisplayed(cancelButton);
    }

    public boolean isUpdateSubjectButtonDisplayed() {
        return commonActions.isDisplayed(updateSubjectButton);
    }

    public void clickEditSubject() {
        commonActions.waitForElementToBeClickable(editSubjectButton);
        commonActions.click(editSubjectButton);
    }

    public boolean isEditSubjectDisplayed() {
        return commonActions.isDisplayed(editSubjectTitle);
    }

    private By getEditSubjectButton(String subjectName) {
        return By.xpath(
                "//h3[normalize-space()='" + subjectName +
                        "']/ancestor::div[contains(@class,'card')]//button[contains(.,'Edit')]"
        );
    }

    private By getDeleteSubjectButton(String subjectName) {
        return By.xpath(
                "//h3[normalize-space()='" + subjectName +
                        "']/ancestor::div[contains(@class,'card')]//button[contains(.,'Delete')]"
        );
    }

    private By getDeleteConfirmationMessage(String subjectName) {
        return By.xpath(
                "//p[normalize-space()='Are you sure you want to delete the subject \"" +
                        subjectName + "\"?']"
        );
    }

    private By getConfirmDeleteButton(String subjectName) {
        return By.xpath(
                "//div[contains(@class,'confirmation-modal-content')]" +
                        "[.//p[contains(text(),'" + subjectName + "')]]" +
                        "//button[.//span[normalize-space()='Delete']]"
        );
    }
    public String getSubjectUid(String subjectName) {
        By subjectTitle = By.xpath(
                "//h3[contains(@class,'subject-card-title') and normalize-space()='" + subjectName + "']"
        );

        WebElement titleElement = commonActions.getElement(subjectTitle);

        WebElement card = titleElement.findElement(
                By.xpath("./ancestor::div[contains(@class,'subject-card')][1]")
        );

        String imageUrl = card.findElement(By.tagName("img")).getAttribute("src");

        String uid = imageUrl.split("/subjects/")[1].split("/")[0];

        System.out.println("UI UID: [" + uid + "]");

        return uid;
    }
}