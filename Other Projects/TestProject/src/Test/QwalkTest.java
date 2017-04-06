package Test;


import Qwalk.*;
import org.junit.Test;

/**
 * Created by Kraft on 2017-04-04.
 */
public class QwalkTest {

    private class QuestionTest{
        @Test
        public void questionGuessTest(){

            String[] alternatives = {"5", "353", "3", "22"};
            Question q1 = new MultipleChoice("Hur gammal är Pia?", alternatives, 3);

            assert(!q1.guess(0));
            assert(q1.guess(3));
        }

        @Test
        public void questionTest(){
            String[] alternatives = {"5", "353", "3", "22"};
            Question q1 = new MultipleChoice("Hur gammal är Pia?", alternatives, 3);
            assert (q1.getQuestionText().equals("Hur gammal är Pia?"));

        }

        @Test(expected=IllegalArgumentException.class)
        public void questionExceptionTest(){
            String[] alternatives = new String[0];
            Question q1 = new MultipleChoice("Hur gammal är Pia?", alternatives, 3);
        }
    }


}
