import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Kirill Liubun on 17/01/2016.
 */
public class Main {
    public static void main(String[] args) throws MalformedURLException {
        URLReader reader = new URLReader("https://en.wikipedia.org/wiki/Planet");
        Finder finder = new Finder();
        List<String> file = reader.saveSite();
        Set<String> address = finder.find(file);
        address.forEach(System.out::println);
    }
}
