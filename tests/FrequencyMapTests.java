import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class FrequencyMapTests {

    FrequencyMap sut;

    @BeforeEach
    public void setUp() {
        var testData = "test".toCharArray();
        sut = new FrequencyMap(testData);
    }

    @Test
    @DisplayName("Constructor - Populated map with data")
    public void FrequencyMap_WithData_PopulatesMap() {

        //Arrange
        var testData = "moreTestData".toCharArray();
        var testMap = new int[26];
        for(char letter : testData)
        {
            testMap[Character.toUpperCase(letter) - 'A']++;
        }

        //Act
        sut = new FrequencyMap(testData);

        //Assert
        assert(Arrays.equals(sut.GetMap(), testMap));
    }

    @Test
    @DisplayName("GetMap - retrieves map")
    public void Clone_GetMap() {
        //Act
        var map = sut.GetMap();

        //Assert
        assert(Arrays.equals(sut.GetMap(), map));
    }

    @Test
    @DisplayName("Clone - copies Frequency map into new memory")
    public void Clone_ClonesMap() {
        //Act
        var clone = sut.Clone();

        //Assert
        assert(Arrays.equals(sut.GetMap(), clone.GetMap()));
        assert(clone != sut);
    }

    @Test
    @DisplayName("Remove - Removes word from map")
    public void Remove_RemovesWordFromMap() {

        //Arrange
        var testData = "testa".toCharArray();
        sut = new FrequencyMap(testData);
        var testMap = new int[26];
        testMap[0]++;

        //Act
        sut.Remove("test");

        //Assert
        assert(sut.GetMap()[5] == 0);
        assert(Arrays.equals(sut.GetMap(), testMap));
    }

    static Stream<Arguments> frequencyMapContainsTestData() {
        return Stream.of(
                Arguments.of("test", "test", true),
                Arguments.of("test", "tset", true),
                Arguments.of("test", "hello", false)
        );
    }

    @DisplayName("Contains - Check whether map contains word")
    @ParameterizedTest(name = "Stored Word {0} and Check word {1}")
    @MethodSource("frequencyMapContainsTestData")
    public void Contains_ChecksWhetherMapContainsWord(String storedWord, String checkWord, boolean expected) {

        //Arrange
        sut =  new FrequencyMap(storedWord.toCharArray());

        //Act
        var doesContain = sut.Contains(checkWord);

        //Assert
        assert(doesContain == expected);
    }

}
