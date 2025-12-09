import java.io.IOException;
import java.util.*;

public class WordSquare {

    static FrequencyMap letterFrequencyMap;
    static HashMap<String, List<String>> wordHashMap;

    public static String[] Get(int squareSize, char[] letters)
    {
        ValidateInput(squareSize, letters).ifPresent(errorMessage -> {
            IO.println(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        });

        IO.println("Attempting to produce word Square of size " +  squareSize + " with letters " + Arrays.toString(letters));

        try
        {
            Setup(squareSize, letters);
        }
        catch (RuntimeException e)
        {
            IO.println(e.getMessage());
            return new String[] {};
        }

        var wordSquare = FindWordSquare(wordHashMap, letterFrequencyMap, new String[squareSize], 0);

        if (!CheckWordSquareCompletion(wordSquare))
        {
            IO.println("Unable to Create word Square");
            return new String[] {};
        }

        return wordSquare;
    }

    private static Optional<String> ValidateInput(int squareSize, char[] letters)
    {
        if (squareSize < 1)
        {
            return Optional.of("Square size must be greater than zero.");
        }

        if (letters.length != (squareSize*squareSize))
            return Optional.of("Number of letters must be grid-size^2 to make a word square.");

        for(char c :letters)
        {
            if (!Character.isLetter(c)) {
                return Optional.of("<letters> must contain only alphabetical characters.");
            }
        }

        return Optional.empty();
    }

    private static void Setup(int squareSize, char[] letters) throws RuntimeException
    {
        letterFrequencyMap = new FrequencyMap(letters);

        var possibleWords = GetPossibleWords(squareSize, letters);
        if (possibleWords.length < squareSize)
        {
            throw new RuntimeException("Not enough possible words to form word square with letters: " + Arrays.toString(letters));
        }

        wordHashMap = BuildWordHashMap(possibleWords);
    }

    private static String[] GetPossibleWords(int wordLength, char[] letters)
    {
        String[] dictionary;
        try {
            dictionary = EnglishDictionary.Get();
        } catch (IOException | InterruptedException e) {
            return new String[] {};
        }

        var potentialWords = Arrays.stream(dictionary).filter(word ->
                word.length() == wordLength && word.chars().allMatch(c -> Arrays.toString(letters).indexOf(c) != -1));

        return potentialWords.toArray(String[]::new);
    }

    private static boolean CheckWordSquareCompletion(String[] wordSquare)
    {
        for (String word : wordSquare) {
            if (word == null || word.isEmpty())
                return false;
        }
        return true;
    }


    private static HashMap<String, List<String>> BuildWordHashMap(String[] words)
    {
        HashMap<String, List<String>> wordHashMap = new HashMap<>();

        for(String word : words) {
            for(int i = 0; i< word.length(); i++)
            {
                wordHashMap.computeIfAbsent(word.substring(0, i), k -> new ArrayList<>()).add(word);
            }
        }

        return wordHashMap;
    }

    private static String[] FindWordSquare(HashMap<String, List<String>> wordHashMap, FrequencyMap letterMap, String[] wordSquare, int index)
    {
        var prefix = BuildPrefix(wordSquare, index);
        var words = wordHashMap.getOrDefault(prefix, Collections.emptyList());

        if (words == null || words.isEmpty())
        {
            wordSquare[index] = "";
            return wordSquare;
        }

        for (var word : words)
        {
            if (letterMap.Contains(word))
            {
                wordSquare[index] = word;
                if (index == wordSquare.length - 1)
                {
                    IO.println("Word Square complete!");
                    return wordSquare;
                }

                var newLetterMap = letterMap.Clone();
                newLetterMap.Remove(word);
                wordSquare = FindWordSquare(wordHashMap, newLetterMap, wordSquare, index + 1);

                if (CheckWordSquareCompletion(wordSquare))
                {
                    return wordSquare;
                }
            }
        }

        return wordSquare;
    }

    private static String BuildPrefix(String[] wordSquare, int currentIndex)
    {
        StringBuilder requiredLetters = new StringBuilder();
        for(int i = 0; i < currentIndex; i++)
        {
            requiredLetters.append(wordSquare[i].charAt(currentIndex));
        }

        return requiredLetters.toString();
    }
}
