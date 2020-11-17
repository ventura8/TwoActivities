package com.example.twoactivities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {

        Math math = new Math();
        int sum = math.addition(3, 2);
        assertEquals(5, sum);
    }

    @Test
    public void substraction_isCorrect() {

        Math math = new Math();
        int sum = math.subtraction(2, 2);
        assertEquals(0, sum);
    }

    @Test
    public void multiply_isCorrect() {

        Math math = new Math();
        int result = math.multiply(2, 2);
        assertEquals(4, result);
    }

    @Test
    public void div_isCorrect() {

        Math math = new Math();
        float result = math.div(4, 2);
        assertEquals(2f, result, 0f);
    }
}