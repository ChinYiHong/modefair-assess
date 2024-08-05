import java.util.*;

public class Patternlock {
    // visited character in a single pattern
    private static final boolean[] visited = new boolean[9];
    //answers
    private static List<String> resultPatterns;
    // all the keys
    private static final char [] keys ={'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

    // generate the pattern list strating from first character
    public static List<String> listPatterns(char first, char second, char third) {
        resultPatterns = new ArrayList<>();
        visited[first - 'A'] = true;
        searchPatterns(first, second, third, new StringBuilder().append(first));
        visited[first - 'A'] = false;
        return resultPatterns;
    }

    private static void searchPatterns(char current, char second, char third, StringBuilder pattern) {
        // check if last character is in place and length is > 1
        if (pattern.length() > 1 && pattern.charAt(pattern.length() - 1) == third) {
            // check if second character is in place 
            if (pattern.indexOf(String.valueOf(second)) == -1) {
                //search thru all keys
                for (char next : keys) {
                    if (!visited[next - 'A']) {
                        visited[next - 'A'] = true;
                        pattern.append(next);
                        searchPatterns(third, second, third, pattern);
                        //after exploring a path, it removes the last character from the pattern and marks it as unvisited,
                        pattern.deleteCharAt(pattern.length() - 1); 
                        visited[next - 'A'] = false;
                    }
                }
            } else {
                resultPatterns.add(pattern.toString());
            }
        }
        // continue searching
        for (char next : keys) {
            if (!visited[next - 'A']) {
                visited[next - 'A'] = true;
                pattern.append(next);
                searchPatterns(next, second, third, pattern);
                pattern.deleteCharAt(pattern.length() - 1);
                visited[next - 'A'] = false;
            }
        }
    }
    //remove pattern function
    public static List<String> removeInvalidPatterns(List<String> patterns) {
        patterns.removeIf(pattern -> !isValidPattern(pattern));
        return patterns;
    }
     
    // check if valid
    private static boolean isValidPattern(String pattern) {
        // Loop through the pattern, checking each pair of consecutive characters.
        for (int i = 0; i < pattern.length() - 1; i++) {
            if (!isValidLine(pattern.charAt(i), pattern.charAt(i + 1), pattern.substring(0, i + 1))) {
                return false;
            }
        }
        return true;
    }
    // remove patterns that passed thru unused line
    private static boolean isValidLine(char from, char to, String usedPath) {
        switch (from) {
            case 'E':
                return true;
            case 'B':
                return !(to == 'H' && !usedPath.contains("E"));
            case 'D':
                return !(to == 'F' && !usedPath.contains("E"));
            case 'F':
                return !(to == 'D' && !usedPath.contains("E"));
            case 'H':
                return !(to == 'B' && !usedPath.contains("E"));
            case 'A':
                return !(to == 'C' && !usedPath.contains("B")) && 
                       !(to == 'G' && !usedPath.contains("D")) && 
                       !(to == 'I' && !usedPath.contains("E"));
            case 'C':
                return !(to == 'A' && !usedPath.contains("B")) && 
                       !(to == 'G' && !usedPath.contains("E")) && 
                       !(to == 'I' && !usedPath.contains("F"));
            case 'G':
                return !(to == 'A' && !usedPath.contains("D")) && 
                       !(to == 'C' && !usedPath.contains("E")) && 
                       !(to == 'I' && !usedPath.contains("H"));
            case 'I':
                return !(to == 'A' && !usedPath.contains("E")) && 
                       !(to == 'C' && !usedPath.contains("F")) && 
                       !(to == 'G' && !usedPath.contains("H"));
            default:
                return false;
        }
    }
    public static void main(String[] args) {
        List<String> patterns = listPatterns('B', 'I', 'C');
        patterns = removeInvalidPatterns(patterns);
        System.out.println(patterns);
        System.out.println("Total patterns: " + patterns.size());
    }
}