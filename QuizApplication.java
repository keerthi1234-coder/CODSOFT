import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private String questionText;
    private List<String>options;
    private int correctAnswerIndex;
    
    public Question(String questionText,List<String>options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public List<String> getOptions() {
        return options;
    }
    
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

public class QuizApplication {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer questionTimer;
    private boolean answerSubmitted;
    private Scanner scanner;
    private List<Boolean> answerResults;
    
    public QuizApplication() {
        initializeQuestions();
        currentQuestionIndex = 0;
        score = 0;
        scanner = new Scanner(System.in);
        answerResults = new ArrayList<>();
    }
    
    private void initializeQuestions() {
        questions = new ArrayList<>();
        
        // Java-related questions
        List<String> options1 = new ArrayList<>();
        options1.add("A) Platform independent programming language");
        options1.add("B) Platform dependent programming language");
        options1.add("C) Platform independent scripting language");
        options1.add("D) Platform dependent scripting language");
        questions.add(new Question("What is Java?", options1, 0));
        
        List<String> options2 = new ArrayList<>();
        options2.add("A) JVM");
        options2.add("B) JRE");
        options2.add("C) JDK");
        options2.add("D) JIT");
        questions.add(new Question("Which component is responsible for converting bytecode to machine code?", options2, 0));
        
        List<String> options3 = new ArrayList<>();
        options3.add("A) 1991");
        options3.add("B) 1995");
        options3.add("C) 2000");
        options3.add("D) 1998");
        questions.add(new Question("In which year was Java first released?", options3, 1));
        
        List<String> options4 = new ArrayList<>();
        options4.add("A) Object-oriented");
        options4.add("B) Functional");
        options4.add("C) Procedural");
        options4.add("D) Logical");
        questions.add(new Question("Java is primarily which type of programming language?", options4, 0));
        
        List<String> options5 = new ArrayList<>();
        options5.add("A) int x = 5;");
        options5.add("B) float x = 5;");
        options5.add("C) double x = 5;");
        options5.add("D) All of the above");
        questions.add(new Question("Which of the following is a valid declaration of a variable in Java?", options5, 3));
    }
    
    public void startQuiz() {
        System.out.println("Welcome to the Java Quiz!");
        System.out.println("You will have 15 seconds to answer each question.");
        System.out.println("Let's begin!\n");
        
        while (currentQuestionIndex < questions.size()) {
            displayQuestion();
            startTimer(15); // 15 seconds per question
            
            answerSubmitted = false;
            while (!answerSubmitted) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim().toUpperCase();
                    if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                        checkAnswer(input.charAt(0) - 'A');
                        break;
                    } else {
                        System.out.println("Please enter a valid option (A, B, C, or D):");
                    }
                }
            }
            
            questionTimer.cancel();
            currentQuestionIndex++;
        }
        
        showResults();
    }
    
    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ":");
        System.out.println(currentQuestion.getQuestionText());
        
        for (String option : currentQuestion.getOptions()) {
            System.out.println(option);
        }
        
        System.out.print("\nYour answer (A/B/C/D): ");
    }
    
    private void startTimer(int seconds) {
        questionTimer = new Timer();
        questionTimer.schedule(new TimerTask() {
            int timeLeft = seconds;
            
            @Override
            public void run() {
                if (timeLeft > 0 && !answerSubmitted) {
                    System.out.print("\rTime left: " + timeLeft + " seconds. ");
                    timeLeft--;
                } else if (!answerSubmitted) {
                    System.out.println("\n\nTime's up!");
                    checkAnswer(-1); // -1 indicates no answer was given
                    this.cancel();
                }
            }
        }, 0, 1000);
    }
    
    private void checkAnswer(int selectedOptionIndex) {
        answerSubmitted = true;
        Question currentQuestion = questions.get(currentQuestionIndex);
        
        if (selectedOptionIndex == currentQuestion.getCorrectAnswerIndex()) {
            System.out.println("Correct!");
            score++;
            answerResults.add(true);
        } else if (selectedOptionIndex == -1) {
            System.out.println("You didn't answer in time. The correct answer was: " + 
                (char)('A' + currentQuestion.getCorrectAnswerIndex()));
            answerResults.add(false);
        } else {
            System.out.println("Incorrect. The correct answer was: " + 
                (char)('A' + currentQuestion.getCorrectAnswerIndex()));
            answerResults.add(false);
        }
    }
    
    private void showResults() {
        System.out.println("\nQuiz completed!");
        System.out.println("Your final score: " + score + "/" + questions.size());
        
        System.out.println("\nQuestion Summary:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ": " + 
                (answerResults.get(i) ? "Correct" : "Incorrect"));
        }
        
        System.out.println("\nThank you for taking the Java Quiz!");
    }
    
    public static void main(String[] args) {
        QuizApplication quiz = new QuizApplication();
        quiz.startQuiz();
    }
}