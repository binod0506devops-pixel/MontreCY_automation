
        package pages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentPage {

    private static final Logger logger = LoggerFactory.getLogger(ContentPage.class);

    private final WebDriver driver;
    private final CommonActions commonActions;

    // Dashboard / Content
    private final By dashboardText = By.xpath("//*[normalize-space()='Dashboard']");
    private final By contentMenu = By.xpath("//*[normalize-space()='Content']");
    private final By subjectsPage = By.xpath("//*[normalize-space()='Subjects']");

    // Subject
    private final By addNewSubjectButton = By.xpath("//button[normalize-space()='Add New Subject']");
    private final By subjectNameInput = By.xpath("//input[@placeholder='Subject Name' or @name='subjectName' or @type='text']");
    private final By fileInput = By.xpath("//input[@type='file']");
    private final By uploadedImage = By.xpath("//img[contains(@src,'blob:') or contains(@src,'data:image')]");
    private final By addSubjectButton = By.xpath("//div[contains(@class,'modal-actions')]//button[@type='button' and .//span[normalize-space()='Add Subject']]");
    private final By toastMessage = By.xpath("//div[@class='toast-message']");
    private final By subjectNameRequiredError = By.xpath("//span[@class='validation-error']");

    // Level
    private final By addNewLevelButton = By.xpath("//span[contains(text(),'Add New Level')]");
    private final By levelNumberTextBox = By.xpath("//input[@placeholder='Enter Level Number']");
    private final By gradeTextBox = By.xpath("//input[@placeholder='e.g. Fifth Grade']");
    private final By descriptionTextArea = By.xpath("//textarea[@placeholder='Enter Description']");
    private final By saveLevelButton = By.xpath("//div[@class='modal-actions']//span[normalize-space()='Add New Level']");

    // Chapter
    private final By theoryVideoTab = By.xpath("//button[@class='tab active']");
    private final By addYourFirstChapter = By.xpath("//span[normalize-space()='+ Add Your First Chapter']");
    private final By addNewChapterTitle = By.xpath("//h2[normalize-space()='Add New Chapter']");
    private final By chapterNumberTextBox = By.xpath("//input[@placeholder='e.g. 1']");
    private final By chapterTitleTextBox = By.xpath("//input[@placeholder='e.g. Introduction']");
    private final By chapterDescriptionTextArea = By.xpath("//textarea[@placeholder='Enter Description']");
    private final By addChapterButton = By.xpath("//span[normalize-space()='Add Chapter']");

    // Video / Cover Image
    private final By chooseVideo = By.xpath("//span[normalize-space()='Choose Video']");
    private final By coverImageFileInput = By.xpath("//input[@type='file' and contains(@accept,'image')]");

    // Chapter Content / Learning Outcomes
    private final By chapterContent = By.xpath("//div[@class='ql-editor ql-blank']");
    private final By learningOutcome1 = By.xpath("//input[@placeholder='Enter Learning Outcome 1...']");
    private final By learningOutcome2 = By.xpath("//input[@placeholder='Enter Learning Outcome 2...']");
    private final By learningOutcome3 = By.xpath("//input[@placeholder='Enter Learning Outcome 3...']");

    // Trivia
    private final By triviaQuestion = By.xpath("//input[@placeholder='e.g. What percentage of income is recommended for savings?']");
    private final By triviaOptionA = By.xpath("//input[@placeholder='Option A']");
    private final By triviaOptionB = By.xpath("//input[@placeholder='Option B']");
    private final By triviaOptionC = By.xpath("//input[@placeholder='Option C']");
    private final By triviaOptionD = By.xpath("//input[@placeholder='Option D']");
    private final By triviaCorrectOption = By.xpath("//div[@class='stv-trivia-options-grid']//div[1]//input[1]");
    private final By textButton = By.xpath("//button[contains(text(),'📝 Text')]");
    private final By triviaContent = By.xpath("//div[@class='ql-editor ql-blank']//p");
    private final By submitButton = By.xpath("//span[normalize-space()='Submit']");

    // Quiz
    private final By quizQuestionBankButton = By.xpath("//*[normalize-space()='Quiz Question Bank']");
    private final By addQuizButton = By.xpath("//span[text()='+ Add']");
    private final By addQuestionTitle = By.xpath("//span[contains(text(),'Select...')]");
    private final By questionTypeDropdown = By.xpath("//span[contains(text(),'Select...')]");
    private final By multipleAnswerMCQ = By.xpath("//span[contains(text(),'Multiple Answer MCQ')]");
    private final By questionTextArea = By.xpath("//textarea[contains(@placeholder,'Enter your question here...')]");
    private final By option1 = By.xpath("//input[@placeholder='Option 1']");
    private final By option2 = By.xpath("//input[@placeholder='Option 2']");
    private final By option3 = By.xpath("//input[@placeholder='Option 3']");
    private final By option4 = By.xpath("//input[@placeholder='Option 4']");
    private final By correctOption1 = By.xpath("//div[contains(@class,'options')]//div[1]//input[@type='checkbox']");
    private final By allOption = By.xpath("//*[normalize-space()='All']");
    private final By difficultyLevelDropdown = By.xpath("//label[contains(normalize-space(),'Difficulty Level')]/following::div[contains(@class,'common-select-content')][1]");
    private final By easyDifficulty = By.xpath("//*[normalize-space()='Easy']");
    private final By explanationTextArea = By.xpath("//label[normalize-space()='Explanation (Optional)']/following-sibling::textarea");
    private final By submitQuestionButton = By.xpath("//button[.//span[normalize-space()='Submit Question']]");

    // Activity
    private final By activityTab = By.xpath("//button[contains(text(),'Activity')]");
    private final By activityContentEditor = By.xpath("//h2[contains(text(),'📚 Activity Content Editor')]");
    private final By activityTitle = By.xpath("//input[contains(@placeholder,'Enter activity title...')]");
    private final By activityTextButton = By.xpath("//button[contains(@title,'Text')]");
    private final By activityTextEditor = By.xpath("(//div[@contenteditable='true' and contains(@class,'ql-editor')])[1]");
    private final By activityImageButton = By.xpath("//button[contains(.,'Image')]");
    private final By activityImageUpload = By.xpath("//span[text()='Click to upload image']");
    private final By activitySubmitButton = By.xpath("//span[normalize-space()='Submit']");

    public ContentPage(WebDriver driver) {
        this.driver = driver;
        this.commonActions = new CommonActions(driver);
    }

    public void waitForDashboard() {
        commonActions.waitForElementToBeDisplayed(dashboardText);
    }

    public void clickContent() {
        commonActions.click(contentMenu);
    }

    public boolean isSubjectsDisplayed() {
        return commonActions.isDisplayed(subjectsPage);
    }

    public void clickAddNewSubject() {
        commonActions.click(addNewSubjectButton);
    }

    public boolean isSubjectDisplayed(String subjectName) {
        return commonActions.isDisplayed(getSubjectLocator(subjectName));
    }

    public boolean verifyToastMessage(String expectedMessage) {
        return commonActions.verifyText(toastMessage, expectedMessage);
    }

    public void addNewSubject(String subjectName, String imagePath) {
        logger.info("Creating Subject: {}", subjectName);
        commonActions.enterText(subjectNameInput, subjectName);
        commonActions.uploadFile(fileInput, uploadedImage, imagePath);
        commonActions.click(addSubjectButton);
        logger.info("Subject creation submitted: {}", subjectName);
    }

    public void addNewLevel(String subjectName, String levelNumber, String grade, String description, String imagePath) {
        logger.info("Creating Level [{}] for Subject [{}]", levelNumber, subjectName);
        commonActions.click(getSubjectLocator(subjectName));
        commonActions.click(addNewLevelButton);
        commonActions.enterText(levelNumberTextBox, levelNumber);
        commonActions.enterText(gradeTextBox, grade);
        commonActions.enterText(descriptionTextArea, description);
        commonActions.uploadFile(fileInput, uploadedImage, imagePath);
        commonActions.click(saveLevelButton);
        logger.info("Level creation submitted: {}", levelNumber);
    }

    public void addNewChapter(
            String subjectName,
            String grade,
            String chapterNumber,
            String chapterTitle,
            String chapterDescription,
            String videoPath,
            String imagePath,
            String content,
            String outcome1,
            String outcome2,
            String outcome3,
            String triviaQuestionData,
            String triviaOptionAData,
            String triviaOptionBData,
            String triviaOptionCData,
            String triviaOptionDData,
            String triviaContentData) throws AWTException {

        logger.info("Creating Chapter [{}] for Subject [{}]", chapterTitle, subjectName);

        commonActions.click(getSubjectLocator(subjectName));
        commonActions.click(getLevelLocator(grade));
        commonActions.click(theoryVideoTab);
        commonActions.click(addYourFirstChapter);
        commonActions.waitForElementToBeDisplayed(addNewChapterTitle);

        commonActions.enterText(chapterNumberTextBox, chapterNumber);
        commonActions.enterText(chapterTitleTextBox, chapterTitle);
        commonActions.enterText(chapterDescriptionTextArea, chapterDescription);
        commonActions.click(addChapterButton);

        commonActions.waitForElementToBeDisplayed(chooseVideo);
        commonActions.uploadVideo(chooseVideo, videoPath);

        addTriviaQuestion(
                triviaQuestionData,
                triviaOptionAData,
                triviaOptionBData,
                triviaOptionCData,
                triviaOptionDData,
                triviaContentData
        );

        commonActions.uploadFile(coverImageFileInput, uploadedImage, imagePath);
        commonActions.enterTextIntoEditor(chapterContent, content);
        commonActions.enterText(learningOutcome1, outcome1);
        commonActions.enterText(learningOutcome2, outcome2);
        commonActions.enterText(learningOutcome3, outcome3);
        commonActions.click(submitButton);

        logger.info("Chapter creation submitted: {}", chapterTitle);
    }

    public void addTriviaQuestion(
            String question,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            String triviaContentData) {

        logger.info("Adding trivia question: {}", question);

        commonActions.enterText(triviaQuestion, question);
        commonActions.enterText(triviaOptionA, optionA);
        commonActions.enterText(triviaOptionB, optionB);
        commonActions.enterText(triviaOptionC, optionC);
        commonActions.enterText(triviaOptionD, optionD);
        commonActions.click(triviaCorrectOption);
        commonActions.click(textButton);
        commonActions.enterTextIntoEditor(triviaContent, triviaContentData);
    }

    public void addQuizQuestion(
            String subjectName,
            String grade,
            String question,
            String option1Data,
            String option2Data,
            String option3Data,
            String option4Data,
            String explanation) {

        logger.info("Adding quiz question for Subject [{}]", subjectName);

        commonActions.click(getSubjectLocator(subjectName));
        commonActions.click(getLevelLocator(grade));
        commonActions.click(quizQuestionBankButton);
        commonActions.click(addQuizButton);
        commonActions.waitForElementToBeDisplayed(addQuestionTitle);
        commonActions.click(questionTypeDropdown);
        commonActions.click(multipleAnswerMCQ);

        commonActions.enterText(questionTextArea, question);
        commonActions.enterText(option1, option1Data);
        commonActions.enterText(option2, option2Data);
        commonActions.enterText(option3, option3Data);
        commonActions.enterText(option4, option4Data);

        commonActions.click(correctOption1);
        commonActions.click(allOption);
        commonActions.click(difficultyLevelDropdown);
        commonActions.click(easyDifficulty);
        commonActions.enterText(explanationTextArea, explanation);
        commonActions.click(submitQuestionButton);

        logger.info("Quiz question submitted.");
    }

    public void addActivity(
            String subjectName,
            String grade,
            String activityTitleData,
            String activityText) {

        logger.info("Creating activity for Subject [{}]", subjectName);

        commonActions.click(getSubjectLocator(subjectName));
        commonActions.click(getLevelLocator(grade));
        commonActions.click(activityTab);
        commonActions.waitForElementToBeDisplayed(activityContentEditor);
        commonActions.enterText(activityTitle, activityTitleData);
        commonActions.click(activityTextButton);
        commonActions.enterTextIntoEditor(activityTextEditor, activityText);
        commonActions.click(activityImageButton);
        commonActions.click(activityImageUpload);
        commonActions.click(activitySubmitButton);

        logger.info("Activity submitted.");
    }

    public boolean validateSubjectNameRequired() {
        return commonActions.validateField(
                addSubjectButton,
                subjectNameRequiredError,
                "Subject name is required"
        );
    }

    private By getSubjectLocator(String subjectName) {
        return By.xpath("//h3[normalize-space()='" + subjectName + "']");
    }

    private By getLevelLocator(String grade) {
        return By.xpath("//img[contains(@class,'level-card-image') and @alt='" + grade + "']");
    }
}

