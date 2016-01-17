import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kirill Liubun on 17/01/2016.
 */
public class Finder {

    private Pattern findAnchor = Pattern.compile("<a.*href=\"(.+?)\".*?>");


    public Set<String> find(List<String> filePiece) {
        Set<String> res = new HashSet<>();
        // search over piece of file
        for (String s : filePiece) {
            Matcher match = findAnchor.matcher(s);
            // search over line of file
            while (match.find()) {
                String address = match.group(1);
                // TODO refactor
                if(!address.contains("#") && address.contains("://"))
                    res.add(address);
            }
        }
        return res;
    }


}
