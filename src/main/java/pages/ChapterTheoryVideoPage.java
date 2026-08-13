package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChapterTheoryVideoPage {

    private final WebDriver driver;
    private final CommonActions commonActions;
    private final By videoUploadSuccess =
            By.xpath("//*[normalize-space()='Video uploaded successfully']");
    private final By theoryVideoTab = By.xpath("//button[@class='tab active']");
    private final By addYourFirstChapter = By.xpath("//span[normalize-space()='+ Add Your First Chapter']");
    private final By addNewChapterTitle = By.xpath("//h2[normalize-space()='Add New Chapter']");
    private final By chapterNumberTextBox = By.xpath("//input[@placeholder='e.g. 1']");
    private final By chapterTitleTextBox = By.xpath("//input[@placeholder='e.g. Introduction']");
    private final By chapterDescriptionTextArea = By.xpath("//textarea[@placeholder='Enter Description']");
    private final By addChapterButton = By.xpath("//span[normalize-space()='Add Chapter']");
    private final By chooseVideo = By.xpath("//span[normalize-space()='Choose Video']");
    private final By coverImageFileInput = By.xpath("//input[@type='file' and contains(@accept,'image')]");
    private final By uploadedImage = By.xpath("//img[contains(@src,'blob:') or contains(@src,'data:image')]");
    private final By chapterContent = By.xpath("//div[@class='ql-editor ql-blank']");
    private final By learningOutcome1 = By.xpath("//input[@placeholder='Enter Learning Outcome 1...']");
    private final By learningOutcome2 = By.xpath("//input[@placeholder='Enter Learning Outcome 2...']");
    private final By learningOutcome3 = By.xpath("//input[@placeholder='Enter Learning Outcome 3...']");
    private final By triviaQuestion = By.xpath("//input[@placeholder='e.g. What percentage of income is recommended for savings?']");
    private final By triviaOptionA = By.xpath("//input[@placeholder='Option A']");
    private final By triviaOptionB = By.xpath("//input[@placeholder='Option B']");
    private final By triviaOptionC = By.xpath("//input[@placeholder='Option C']");
    private final By triviaOptionD = By.xpath("//input[@placeholder='Option D']");
    private final By triviaCorrectOption = By.xpath("//div[@class='stv-trivia-options-grid']//div[1]//input[1]");
    private final By textButton = By.xpath("//button[contains(text(),'📝 Text')]");
    private final By triviaContent = By.xpath("//div[@class='ql-editor ql-blank']//p");
    private final By submitButton = By.xpath("//span[normalize-space()='Submit']");
//    private final By toastMessage = By.xpath("//div[@class='toast-message']");
private final By toastMessage =
        By.xpath("//*[normalize-space()='Theory created successfully']");

    public ChapterTheoryVideoPage(WebDriver driver) {
        this.driver = driver;
        this.commonActions = new CommonActions(driver);
    }

    public void openChapter(String subjectName, String grade) {
        commonActions.click(getSubjectLocator(subjectName));
        commonActions.click(getLevelLocator(grade));
        commonActions.click(theoryVideoTab);
    }

    public void addNewChapter(String subjectName, String grade, String chapterNumber, String chapterTitle, String chapterDescription, String videoPath, String imagePath, String content, String outcome1, String outcome2, String outcome3, String triviaQuestionData, String triviaOptionAData, String triviaOptionBData, String triviaOptionCData, String triviaOptionDData, String triviaContentData) throws InterruptedException {
        openChapter(subjectName, grade);
        commonActions.click(addYourFirstChapter);
        commonActions.waitForElementToBeDisplayed(addNewChapterTitle);
        commonActions.enterText(chapterNumberTextBox, chapterNumber);
        commonActions.enterText(chapterTitleTextBox, chapterTitle);
        commonActions.enterText(chapterDescriptionTextArea, chapterDescription);
        commonActions.click(addChapterButton);
        commonActions.sleepTwoSeconds();
        commonActions.waitForElementToBeDisplayed(chooseVideo);
        commonActions.sleepTwoSeconds();
        commonActions.uploadVideo(
                chooseVideo,
                videoPath
        );
        commonActions.sleepTwoSeconds();
        addTriviaQuestion(triviaQuestionData, triviaOptionAData, triviaOptionBData, triviaOptionCData, triviaOptionDData, triviaContentData);
        commonActions.sleepTwoSeconds();
        commonActions.uploadFile(coverImageFileInput, uploadedImage, imagePath);
        commonActions.enterTextIntoEditor(chapterContent, content);
        commonActions.enterText(learningOutcome1, outcome1);
        commonActions.enterText(learningOutcome2, outcome2);
        commonActions.enterText(learningOutcome3, outcome3);
        commonActions.click(submitButton);
        commonActions.sleepTwoSeconds();
//        commonActions.logVisibleText();
    }

    private void addTriviaQuestion(String question, String optionA, String optionB, String optionC, String optionD, String content) {
        commonActions.enterText(triviaQuestion, question);
        commonActions.enterText(triviaOptionA, optionA);
        commonActions.enterText(triviaOptionB, optionB);
        commonActions.enterText(triviaOptionC, optionC);
        commonActions.enterText(triviaOptionD, optionD);
        commonActions.click(triviaCorrectOption);
        commonActions.click(textButton);
        commonActions.enterTextIntoEditor(triviaContent, content);
    }

    public boolean verifyToastMessage(String expectedMessage) {
        return commonActions.verifyText(toastMessage, expectedMessage);
    }

    private By getSubjectLocator(String subjectName) {
        return By.xpath("//h3[normalize-space()='" + subjectName + "']");
    }

    private By getLevelLocator(String grade) {
        return By.xpath("//img[contains(@class,'level-card-image') and @alt='" + grade + "']");
    }
}