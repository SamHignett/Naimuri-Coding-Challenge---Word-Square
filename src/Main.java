import java.util.*;

public class Main {

    final int sizeArgIndex = 0;
    final int letterListArgIndex = 1;

    List<Map.Entry<Integer, String>> exampleList = List.of(
            new AbstractMap.SimpleEntry<>(4, "aaccdeeeemmnnnoo"),
            new AbstractMap.SimpleEntry<>(5, "aaaeeeefhhmoonssrrrrttttw"),
            new AbstractMap.SimpleEntry<>(5, "aabbeeeeeeeehmosrrrruttvv"),
            new AbstractMap.SimpleEntry<>(7, "aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy")
    );

    void main(String[] args) {

        if (args.length == 0)
        {
            for(var example :  exampleList)
            {
                var wordSquare = WordSquare.Get(example.getKey(), example.getValue().toCharArray());
                PrintWordSquare(wordSquare);
            }
            System.exit(0);
        }

        ValidateArgs(args).ifPresent(errorMessage -> {
            IO.println(errorMessage);
            System.exit(1);
        });

        int squareSize = Integer.parseInt(args[sizeArgIndex]);
        char[] letters = args[letterListArgIndex].toCharArray();

        var wordSquare = WordSquare.Get(squareSize, letters);

        PrintWordSquare(wordSquare);
    }

    private Optional<String> ValidateArgs(String[] args)
    {
        if (args.length != 2) {
            return Optional.of("Incorrect number of arguments. \n Word Square Usage: <grid-size> <letters>");
        }

        try{
            Integer.parseInt(args[sizeArgIndex]);
        }
        catch (NumberFormatException e){
            return Optional.of("<grid-size> must be a whole number.");
        }

        return Optional.empty();
    }


    private void PrintWordSquare(String[] wordSquare)
    {
        for(int i = 0; i < wordSquare.length; i++)
            IO.print("-");

        IO.println();

        for (String word : wordSquare) {
            IO.println(word);
        }

        for(int i = 0; i < wordSquare.length; i++)
            IO.print("-");
    }
}
