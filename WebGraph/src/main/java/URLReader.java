import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Liubun on 17/01/2016.
 */
public class URLReader {

    private String path = "sites";
    private URL site;


    public URLReader(String address) throws MalformedURLException {
        this.site = new URL(address);
        new File(path).mkdir();
    }

    public URLReader(String address, String path) throws MalformedURLException {
        this.site = new URL(address);
        this.path = path;
        new File(path).mkdir();
    }

    public List<String> saveSite() {
        List<String> res = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(site.openStream()));
            BufferedWriter write = new BufferedWriter(new FileWriter(path + "\\" + site.getHost() + ".html"))
        ) {
            String line;
            while((line = reader.readLine()) != null) {
                res.add(line);
                write.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

}
