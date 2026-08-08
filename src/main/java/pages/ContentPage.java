package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentPage {

    private static final Logger logger =
            LoggerFactory.getLogger(ContentPage.class);

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final CommonActions commonActions;

    private final By contentMenu =
            By.xpath("//a[normalize-space()='Content']");

    private final By subjectsTitle =
            By.xpath("//h1[normalize-space()='Subjects']");

    private final By addNewSubjectButton =
            By.xpath("//span[normalize-space()='Add New Subject']");

    private final By subjectName =
            By.xpath("//input[@placeholder='Enter Subject Name']");

    private final By imageUploadArea =
            By.cssSelector("div.image-upload-area");

    private final By fileInput1 =
            By.cssSelector("input[type='file']");

    private final By addSubjectButton =
            By.xpath("//button[normalize-space()='Add Subject']");

    // Level Locators
    private final By addNewLevelButton =
            By.xpath("//span[normalize-space()='Add New Level']");

    private final By levelNumberTextBox =
            By.xpath("//input[@placeholder='Enter Level Number']");

    private final By gradeTextBox =
            By.xpath("//input[@placeholder='e.g. Fifth Grade']");

    private final By descriptionTextArea =
            By.xpath("//textarea[@placeholder='Enter Description']");

    private final By saveLevelButton =
            By.xpath("//div[@class='modal-actions']//span[normalize-space()='Add New Level']");

    // Chapter Locators
    private final By theoryVideoTab =
            By.xpath("//button[@class='tab active']");

    private final By addYourFirstChapter =
            By.xpath("//span[normalize-space()='+ Add Your First Chapter']");

    private final By addNewChapterTitle =
            By.xpath("//h2[normalize-space()='Add New Chapter']");

    private final By chapterNumberTextBox =
            By.xpath("//input[@placeholder='e.g. 1']");

    private final By chapterTitleTextBox =
            By.xpath("//input[@placeholder='e.g. Introduction']");

    private final By chapterDescriptionTextArea =
            By.xpath("//textarea[@placeholder='Enter Description']");

    private final By addChapterButton =
            By.xpath("//span[normalize-space()='Add Chapter']");

    private final By chooseVideo =
            By.xpath("//span[normalize-space()='Choose Video']");

    private final By coverImage =
            By.xpath("//span[normalize-space()='Cover Image']");

    private final By chapterContent =
            By.xpath("//div[@class='ql-editor ql-blank']");

    private final By learningOutcome1 =
            By.xpath("//input[@placeholder='Enter Learning Outcome 1...']");

    private final By learningOutcome2 =
            By.xpath("//input[@placeholder='Enter Learning Outcome 2...']");

    private final By learningOutcome3 =
            By.xpath("//input[@placeholder='Enter Learning Outcome 3...']");

    private final By submitButton =
            By.xpath("//span[normalize-space()='Submit']");

    private final By fileInput =
            By.cssSelector("input[type='file']");

    // Quiz Question Bank
    private final By quizQuestionBankButton =
            By.xpath("//button[normalize-space()='Quiz Question Bank']");

    private final By addQuizButton =
            By.xpath("//span[normalize-space()='+ Add']");

    private final By addQuestionTitle =
            By.xpath("//h2[normalize-space()='Add Question']");

    private final By questionTypeDropdown =
            By.xpath("//div[contains(@class,'form-group')]//span[contains(@class,'arrow')]//*[name()='svg']");

    private final By multipleAnswerMCQ =
            By.xpath("//span[text()='Multiple Answer MCQ']");

    private final By questionTextArea =
            By.xpath("//textarea[@placeholder='Enter your question here...']");

    private final By option1 =
            By.xpath("//input[@placeholder='Option 1']");

    private final By option2 =
            By.xpath("//input[@placeholder='Option 2']");

    private final By option3 =
            By.xpath("//input[@placeholder='Option 3']");

    private final By option4 =
            By.xpath("//input[@placeholder='Option 4']");

    private final By correctOption1 =
            By.xpath("//div[@class='options-container']//div[1]//input[1]");

    private final By applicableDropdown =
            By.xpath("//div[contains(@class,'form-group')]//div[contains(@class,'common-multi-select-label')]//div[contains(@class,'')]");

    private final By allOption =
            By.xpath("//span[contains(@class,'option-label')][normalize-space()='All']");

    private final By explanationTextArea =
            By.xpath("//textarea[contains(@placeholder,'Enter explanation here...')]");
    private final By difficultyLevelDropdown =
            By.xpath("//div[5]//div[1]//div[1]//div[1]//div[1]//span[1]");

    private final By easyDifficulty =
            By.xpath("//span[normalize-space()='Easy']");
    private final By submitQuestionButton =
            By.xpath("//span[normalize-space()='Submit Question']");
    public ContentPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.commonActions = new CommonActions(driver);

    }

    public void clickContent() {

        logger.info("Clicking Content menu");

        wait.until(
                ExpectedConditions.elementToBeClickable(contentMenu)
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(subjectsTitle)
        );

        logger.info("Content page opened");

    }

    public boolean isSubjectsDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(subjectsTitle)
        ).isDisplayed();

    }

    public String getSubjectsTitle() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(subjectsTitle)
        ).getText();

    }

    public void addNewSubject(String name, String imagePath) {

        logger.info("Opening Add New Subject");

        wait.until(
                ExpectedConditions.elementToBeClickable(addNewSubjectButton)
        ).click();

        logger.info("Entering Subject Name");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(subjectName)
        ).sendKeys(name);

        logger.info("Uploading Subject Image");

        commonActions.uploadFile(
                imageUploadArea,
                fileInput1,
                imagePath
        );

        logger.info("Clicking Add Subject");

        wait.until(
                ExpectedConditions.elementToBeClickable(addSubjectButton)
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(getSubjectLocator(name))
        );

        logger.info("Subject '{}' created successfully", name);

    }

    /**
     * Dynamic locator for Subject Card
     */
    private By getSubjectLocator(String subjectName) {

        return By.xpath("//h3[normalize-space()='" + subjectName + "']");

    }

    /**
     * Add New Level
     */
    public void addNewLevel(String subjectName,
                            String levelNumber,
                            String grade,
                            String description) {

        logger.info("Opening Subject : {}", subjectName);

        wait.until(
                ExpectedConditions.elementToBeClickable(getSubjectLocator(subjectName))
        ).click();

        logger.info("Clicking Add New Level");

        wait.until(
                ExpectedConditions.elementToBeClickable(addNewLevelButton)
        ).click();

        logger.info("Entering Level Number : {}", levelNumber);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(levelNumberTextBox)
        ).sendKeys(levelNumber);

        logger.info("Entering Grade : {}", grade);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(gradeTextBox)
        ).sendKeys(grade);

        logger.info("Entering Description");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(descriptionTextArea)
        ).sendKeys(description);

        logger.info("Clicking Add New Level");

        wait.until(
                ExpectedConditions.elementToBeClickable(saveLevelButton)
        ).click();

        logger.info("Level added successfully.");

    }


    public void addNewChapter(
            String subjectName,
            String grade,
            String chapterNumber,
            String chapterTitle,
            String chapterDescription,
            String videoPath,
            String coverImagePath,
            String content,
            String outcome1,
            String outcome2,
            String outcome3) throws InterruptedException {

        // ==================================================
        // STEP 1 : CLICK SUBJECT
        // ==================================================

        logger.info("Opening Subject : {}", subjectName);

        By subjectLocator =
                By.xpath("//h3[normalize-space()='" + subjectName + "']");

        wait.until(
                ExpectedConditions.elementToBeClickable(subjectLocator)
        ).click();

        logger.info("Subject clicked successfully");

        // Wait 3 seconds for Level page
        Thread.sleep(3000);


        // ==================================================
        // STEP 2 : CLICK LEVEL
        // ==================================================

        logger.info("Opening Level : {}", grade);

        By levelLocator =
                By.xpath("//img[contains(@class,'level-card-image') and @alt='"
                        + grade + "']");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(levelLocator)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(levelLocator)
        ).click();

        Thread.sleep(3000);
        // ==================================================
        // STEP 3 : CLICK THEORY / VIDEO
        // ==================================================

        logger.info("Clicking Theory/Video");

        wait.until(
                ExpectedConditions.elementToBeClickable(theoryVideoTab)
        ).click();

        Thread.sleep(3000);
        // ==================================================
        // STEP 4 : ADD FIRST CHAPTER
        // ==================================================

        logger.info("Clicking Add Your First Chapter");

        wait.until(
                ExpectedConditions.elementToBeClickable(addYourFirstChapter)
        ).click();


        // ==================================================
        // STEP 5 : VERIFY FORM
        // ==================================================

        logger.info("Verifying Add New Chapter form");
        Thread.sleep(3000);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        addNewChapterTitle
                )
        );


        // ==================================================
        // STEP 6 : ENTER CHAPTER NUMBER
        // ==================================================
        Thread.sleep(3000);
        logger.info("Entering Chapter Number : {}", chapterNumber);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        chapterNumberTextBox
                )
        ).sendKeys(chapterNumber);


        // ==================================================
        // STEP 7 : ENTER CHAPTER TITLE
        // ==================================================
        Thread.sleep(3000);
        logger.info("Entering Chapter Title : {}", chapterTitle);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        chapterTitleTextBox
                )
        ).sendKeys(chapterTitle);

        Thread.sleep(3000);
        // ==================================================
        // STEP 8 : ENTER DESCRIPTION
        // ==================================================

        logger.info("Entering Chapter Description");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        chapterDescriptionTextArea
                )
        ).sendKeys(chapterDescription);

        Thread.sleep(3000);
        // ==================================================
        // STEP 9 : ADD CHAPTER
        // ==================================================

        logger.info("Clicking Add Chapter");

        wait.until(
                ExpectedConditions.elementToBeClickable(addChapterButton)
        ).click();


        // ==================================================
        // STEP 10 : REFRESH
        // ==================================================

        logger.info("Refreshing page after chapter creation");

        driver.navigate().refresh();

        Thread.sleep(3000);


        // ==================================================
        // STEP 11 : UPLOAD VIDEO
        // ==================================================

        logger.info("Uploading Video : {}", videoPath);

        commonActions.uploadFile(
                chooseVideo,
                fileInput,
                videoPath
        );


        // ==================================================
        // STEP 12 : UPLOAD COVER IMAGE
        // ==================================================
        Thread.sleep(3000);
        logger.info("Uploading Cover Image : {}", coverImagePath);

        commonActions.uploadFile(
                coverImage,
                fileInput,
                coverImagePath
        );

        Thread.sleep(3000);
        // ==================================================
        // STEP 13 : ADD CONTENT
        // ==================================================

        logger.info("Adding Chapter Content");

        WebElement editor =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                chapterContent
                        )
                );
        Thread.sleep(3000);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        editor
                );
        Thread.sleep(3000);
        editor.click();
        editor.sendKeys(content);


        // ==================================================
        // STEP 14 : ADD LEARNING OUTCOMES
        // ==================================================

        logger.info("Adding Learning Outcome 1");
        Thread.sleep(3000);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        learningOutcome1
                )
        ).sendKeys(outcome1);

        logger.info("Adding Learning Outcome 2");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        learningOutcome2
                )
        ).sendKeys(outcome2);
        Thread.sleep(3000);
        logger.info("Adding Learning Outcome 3");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        learningOutcome3
                )
        ).sendKeys(outcome3);

        Thread.sleep(3000);
        // ==================================================
        // STEP 15 : SUBMIT
        // ==================================================

        logger.info("Clicking Submit");

        wait.until(
                ExpectedConditions.elementToBeClickable(submitButton)
        ).click();
        Thread.sleep(6000);
        logger.info("Chapter added successfully");
    }

//    public void addQuizQuestion(
//            String subjectName,
//            String grade,
//            String question,
//            String option1Data,
//            String option2Data,
//            String option3Data,
//            String option4Data,
//            String explanation) throws InterruptedException {
//
//        // Click Subject
//        logger.info("Clicking Subject : {}", subjectName);
//
//        By subjectLocator =
//                By.xpath("//h3[normalize-space()='" + subjectName + "']");
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(subjectLocator)
//        ).click();
//
//        Thread.sleep(3000);
//
//
//        // Click Level
//        logger.info("Clicking Level : {}", grade);
//
//        By levelLocator =
//                By.xpath("//img[contains(@class,'level-card-image') and @alt='"
//                        + grade + "']");
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(levelLocator)
//        ).click();
//        Thread.sleep(3000);
//
//        // Click Quiz Question Bank
//        logger.info("Clicking Quiz Question Bank");
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        quizQuestionBankButton
//                )
//        ).click();
//        Thread.sleep(3000);
//
//        // Click Add
//        logger.info("Clicking Add");
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        addQuizButton
//                )
//        ).click();
//        Thread.sleep(3000);
//
//        // Verify Add Question
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        addQuestionTitle
//                )
//        );
//
//        Thread.sleep(3000);
//        // Select Multiple Answer MCQ
//        logger.info("Selecting Multiple Answer MCQ");
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        questionTypeDropdown
//                )
//        ).click();
//        Thread.sleep(3000);
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        multipleAnswerMCQ
//                )
//        ).click();
//
//        Thread.sleep(3000);
//        // Question
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        questionTextArea
//                )
//        ).sendKeys(question);
//
//        Thread.sleep(3000);
//        // Options
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(option1)
//        ).sendKeys(option1Data);
//        Thread.sleep(3000);
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(option2)
//        ).sendKeys(option2Data);
//        Thread.sleep(3000);
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(option3)
//        ).sendKeys(option3Data);
//        Thread.sleep(3000);
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(option4)
//        ).sendKeys(option4Data);
//        Thread.sleep(3000);
//
//        // Correct Option 1
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        correctOption1
//                )
//        ).click();
//
//        Thread.sleep(3000);
//        // Applicable -> All
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        applicableDropdown
//                )
//        ).click();
//
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        allOption
//                )
//        ).click();
//        Thread.sleep(3000);
//
//        // Close dropdown
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        applicableDropdown
//                )
//        ).click();
//
//        Thread.sleep(3000);
//        // Explanation
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        explanationTextArea
//                )
//        ).sendKeys(explanation);
//
//        Thread.sleep(7000);
//        // Submit
//        logger.info("Submitting Question");
//        Thread.sleep(3000);
//        wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        submitQuestionButton
//                )
//        ).click();
//
//        logger.info("Quiz Question added successfully");
//    }

    public void addQuizQuestion(
            String subjectName,
            String grade,
            String question,
            String option1Data,
            String option2Data,
            String option3Data,
            String option4Data,
            String explanation) throws InterruptedException {

        // Click Subject
        logger.info("Clicking Subject : {}", subjectName);

        By subjectLocator =
                By.xpath("//h3[normalize-space()='" + subjectName + "']");

        wait.until(
                ExpectedConditions.elementToBeClickable(subjectLocator)
        ).click();

        Thread.sleep(3000);


        // Click Level
        logger.info("Clicking Level : {}", grade);

        By levelLocator =
                By.xpath("//img[contains(@class,'level-card-image') and @alt='"
                        + grade + "']");

        wait.until(
                ExpectedConditions.elementToBeClickable(levelLocator)
        ).click();
        Thread.sleep(3000);

        // Quiz Question Bank
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        quizQuestionBankButton
                )
        ).click();

        Thread.sleep(3000);
        // Add
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addQuizButton
                )
        ).click();

        Thread.sleep(3000);
        // Verify Add Question
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        addQuestionTitle
                )
        );

        Thread.sleep(3000);
        // Select Multiple Answer MCQ
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        questionTypeDropdown
                )
        ).click();
        Thread.sleep(3000);
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        multipleAnswerMCQ
                )
        ).click();

        Thread.sleep(3000);
        // Question
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        questionTextArea
                )
        ).sendKeys(question);

        Thread.sleep(3000);
        // Options
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(option1)
        ).sendKeys(option1Data);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(option2)
        ).sendKeys(option2Data);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(option3)
        ).sendKeys(option3Data);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(option4)
        ).sendKeys(option4Data);


        // Correct Option 1
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        correctOption1
                )
        ).click();

        Thread.sleep(3000);
        // Applicable -> All
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        applicableDropdown
                )
        ).click();
        Thread.sleep(3000);
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        allOption
                )
        ).click();

        // Close dropdown
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        applicableDropdown
                )
        ).click();

        Thread.sleep(3000);
        // Difficulty Level
        logger.info("Selecting Difficulty Level : Easy");

        WebElement difficulty =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                difficultyLevelDropdown
                        )
                );
        Thread.sleep(3000);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        difficulty
                );
        Thread.sleep(3000);
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        difficultyLevelDropdown
                )
        ).click();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        easyDifficulty
                )
        ).click();

        Thread.sleep(3000);
        // Explanation
        logger.info("Entering Explanation");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        explanationTextArea
                )
        ).sendKeys(explanation);
        Thread.sleep(3000);

        // Submit
        logger.info("Clicking Submit Question");

        WebElement submitButton =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                submitQuestionButton
                        )
                );

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        submitButton
                );
        Thread.sleep(3000);
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        submitQuestionButton
                )
        ).click();


        // IMPORTANT: verify submission actually happened
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        addQuestionTitle
                )
        );
        Thread.sleep(3000);
        logger.info("Quiz Question added successfully");
    }










}