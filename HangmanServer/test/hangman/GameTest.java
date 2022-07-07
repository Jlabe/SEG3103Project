package hangman;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest extends TestCase {

    public static final int MAX_FAILED_ATTEMPTS = 7;

    public void testMakeAttempt(){
        Game game = new Game("Test");
        List<Character> attemptComparison = new ArrayList<>();

        // Make attempt
        game.makeAttempt('C');
        attemptComparison.add('C');
        List<Character> attempts = game.attempts;
        Assert.assertEquals(attemptComparison, attempts);

        // Make attempt with same letter
        game.makeAttempt('C');
        game.makeAttempt('C');
        attempts = game.attempts;
        Assert.assertEquals(attemptComparison, attempts);
    }

    public void testGetSecretWord(){
        Game game = new Game("Test");
        String secretWord = game.getSecretWord();
        Assert.assertEquals("TEST", secretWord);
    }

    @Test
    public void testGetKnownLetters(){
        Game game = new Game("Test");
        List<Character> attempts = new ArrayList<>();

        //With no known letters
        String knownLetters = game.getKnownLetters();
        Assert.assertEquals("T__T", knownLetters);

        //All known letters
        attempts.add('T');
        attempts.add('E');
        attempts.add('S');
        attempts.add('T');
        game.attempts = attempts;
        knownLetters = game.getKnownLetters();
        Assert.assertEquals("TEST", knownLetters);
    }

    @Test
    public void testCountFailedAttempts(){
        Game game = new Game("Test");
        List<Character> attempts = new ArrayList<>();

        //No attempts
        game.attempts = attempts;
        int failedAttemptsCount = game.countFailedAttempts();
        Assert.assertEquals(0, failedAttemptsCount);

        //Max Failed attempts
        for (int i = 0; i < 7; i++) {
            attempts.add((char)i);
        }
        game.attempts = attempts;
        failedAttemptsCount = game.countFailedAttempts();
        Assert.assertEquals(7, failedAttemptsCount);

    }

    @Test
    public void testCountMissingLetters(){
        Game game = new Game("Test");
        List<Character> attempts = new ArrayList<>();

        // 0 Missing letters
        attempts.add('T');
        attempts.add('E');
        attempts.add('S');
        attempts.add('T');
        game.attempts = attempts;
        int misingLetterCount = game.countMissingLetters();
        Assert.assertEquals(0, misingLetterCount);

        //Max letters remaining
        attempts.clear();
        game.attempts = attempts;
        misingLetterCount = game.countMissingLetters();
        Assert.assertEquals(2, misingLetterCount);
    }

    @Test
    public void testGetResult() {

        //Test for OPEN
        Game game = new Game("Test");
        GameResult result = game.getResult();
        Assert.assertEquals("OPEN", result.name());

        //Test for FAILED
        List<Character> attempts = new ArrayList<>();
        for (int i = 0; i < MAX_FAILED_ATTEMPTS; i++) {
            attempts.add((char)i);
        }
        game.attempts = attempts;
        result = game.getResult();
        Assert.assertEquals("FAILED", result.name());

        //Test for SOLVED
        game.secret = "";
        result = game.getResult();
        Assert.assertEquals("SOLVED", result.name());
    }
}