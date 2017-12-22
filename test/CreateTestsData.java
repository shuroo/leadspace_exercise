import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shirirave on 22/12/2017.
 */
public class CreateTestsData {

    public static File createTwoWordFileForDataDictionaryTestReplace() throws IOException {
        String test_file_name = "replace-file-test.txt";
        List<String> lines = Arrays.asList("President", "Shiris Test");
        Path file_with_path = Paths.get(test_file_name);
        Files.write(file_with_path, lines, Charset.forName("UTF-8"));
        return new File(test_file_name);
    }

    public static File createThreeWordFileForDataDictionaryTestReplace() throws IOException {
        String test_file_name = "replace-file-test2.txt";
        List<String> lines = Arrays.asList("President", "Shiris Test","Business");
        Path file_with_path = Paths.get(test_file_name);
        Files.write(file_with_path, lines, Charset.forName("UTF-8"));
        return new File(test_file_name);
    }
}
