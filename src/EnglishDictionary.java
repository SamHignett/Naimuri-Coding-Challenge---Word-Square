import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class EnglishDictionary {

    private static final String uri = "https://norvig.com/ngrams/enable1.txt";
    private static final String fileName = "dictionary.txt";

    private static String[] dictionary;

    private static final URI path;

    static {
        try {
            path = EnglishDictionary.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Path filePath = Path.of(path.resolve(fileName));

    public static String[] Get() throws IOException, InterruptedException
    {
        if (dictionary != null)
            return dictionary;

        try{
            dictionary = LoadFromFile();
        }
        catch (NoSuchFileException _)
        {
            IO.println("No existing dictionary file found.");
            try {
                IO.println("Fetching online dictionary");
                dictionary = LoadFromURL();
            }
            catch (Exception urlLoadException)
            {
                IO.println("Error fetching Dictionary: " + urlLoadException);
                throw urlLoadException;
            }
            try{
                SaveToFile(dictionary);
                IO.println("Saved to " + fileName);
            }
            catch (IOException fileSaveException)
            {
                IO.println("Obtained Dictionary, but unable to save to file: " + fileSaveException);
            }
        }
        catch (IOException e)
        {
            IO.println("Error fetching Dictionary: " + e);
            throw e;
        }
        return dictionary;
    }

    private static String[] LoadFromURL()throws IOException, InterruptedException
    {
        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(URI.create(uri))
                .GET()
                .build();

        try {
            var result = client.send(request, HttpResponse.BodyHandlers.ofString());

            return result.body().split("\\r?\\n");
        }
        catch (IOException | InterruptedException e) {
            IO.println("Error requesting word Dictionary: " + e);
            throw e;
        }
    }

    private static String[] LoadFromFile() throws IOException {

        return Files.lines(filePath).toArray(String[]::new);
    }

    private static void SaveToFile(String[] lines) throws IOException {


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString(), true))) {

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.newLine();
        }
    }
}
