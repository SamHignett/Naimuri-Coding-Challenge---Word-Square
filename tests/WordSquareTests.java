import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class WordSquareTests {

    static Stream<Arguments> wordSquareValidData() {
        return Stream.of(
                Arguments.of(4, "aaccdeeeemmnnnoo"),
                Arguments.of(5, "aaaeeeefhhmoonssrrrrttttw"),
                Arguments.of(5, "aabbeeeeeeeehmosrrrruttvv"),
                Arguments.of(7, "aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy")
        );
    }

    @DisplayName("Get - Produce Suitable Word Square with valid input")
    @ParameterizedTest(name = "Grid Size {0} and letters {1}")
    @MethodSource("wordSquareValidData")
    public void Get_withValidInput_ProducesWordSquare(int squareSize, String letters)
    {

        //Act
        var wordSquare = WordSquare.Get(squareSize, letters.toCharArray());

        //Assert
        assert(CheckWordSquare(wordSquare));
    }

    static Stream<Arguments> wordSquareInvalidArguments() {
        return Stream.of(
                Arguments.of(4, "test"),
                Arguments.of(5, "aaaeeeef123oonssrrrrttttw"),
                Arguments.of(5, "aaaeeeef$^%oonssrrrrttttw"),
                Arguments.of(-1, "test")
        );
    }

    @DisplayName("Get - Throw with Invalid input")
    @ParameterizedTest(name = "Grid Size {0} and letters {1}")
    @MethodSource("wordSquareInvalidArguments")
    public void Get_withInvalidInput_ThrowsIllegalArgumentException(int squareSize, String letters)
    {
        //Act && Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> WordSquare.Get(squareSize, letters.toCharArray()));
    }

    static Stream<Arguments> wordSquareInputsWithNoSolution() {
        return Stream.of(
                Arguments.of(3, "aaabbbcdd"),
                Arguments.of(4, "aaaabbbccddefghh")
        );
    }
    @DisplayName("Get - Returns empty array on no solution found")
    @ParameterizedTest(name = "Grid Size {0} and letters {1}")
    @MethodSource("wordSquareInputsWithNoSolution")
    public void Get_WithNoSolution_ReturnsEmptyStringArray(int squareSize, String letters)
    {
        //Act
        var wordSquare = WordSquare.Get(squareSize, letters.toCharArray());

        //Assert
        assert(!CheckWordSquare(wordSquare));
    }

    private boolean CheckWordSquare(String[] wordSquare)
    {
        if (wordSquare == null || wordSquare.length == 0)
            return false;

        for (String word : wordSquare) {
            IO.println(word);
        }

        for (int i = 0; i < wordSquare.length; i++) {
            String row = wordSquare[i];

            if (row.length() != wordSquare.length) return false;

            for (int j = 0; j < row.length(); j++) {
                if (wordSquare[j].length() <= i) return false;

                if (row.charAt(j) != wordSquare[j].charAt(i)) return false;
            }
        }

        return true;
    }
}
