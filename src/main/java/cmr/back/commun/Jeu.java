package cmr.back.commun;

import java.util.Map;
import static java.util.Map.entry;

public class Jeu {

    public static Map<String, String[]> availableMoveTab = Map.ofEntries(
            entry("0", new String[]{"1","3"}),
            entry("1", new String[]{"0", "2"}),
            entry("2", new String[]{"1", "5"}),
            entry("3", new String[]{"0", "6"}),
            entry("5", new String[]{"2", "8"}),
            entry("6", new String[]{"3", "7"}),
            entry("7", new String[]{"6", "8"}),
            entry("8", new String[]{"5", "7"})
    );


}
