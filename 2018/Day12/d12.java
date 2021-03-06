import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class d12 {
    public static void main(String[] args) throws Exception {
        // Read input
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Pattern p = Pattern.compile("^([\\.#]{5}) => ([\\.#])");

        String startG = br.readLine().split(" ")[2];

        String str;
        HashMap<String, Character> spread = new HashMap<String, Character>();
        while((str = br.readLine()) != null) {
            Matcher m = p.matcher(str);
            if (m.find());
                spread.put(m.group(1), m.group(2).charAt(0));
        }

        ArrayList<Character> currentG = new ArrayList<Character>();
        ArrayList<Character> nextG = new ArrayList<Character>();

        for(Character c : startG.toCharArray()) {
            currentG.add(c);
        }


        int gen20Score = -1;
        int numFrontPadding = updatePadding(currentG);
        // Loop through 20 generations and update the list (plants)
        for(int i = 1; i <= 180; i++) {
            // Check every 5 sublist of letters
                nextG.add(0,'.'); nextG.add(0,'.');
            for(int index = 0; index < currentG.size() - 4; index++) {
                List<Character> subList = currentG.subList(index, index + 5);
                StringBuilder sb = new StringBuilder();
                for(Character c : subList) {
                    sb.append(c);
                }
                String currentStr = sb.toString();
                if(spread.containsKey(currentStr)) {
                    nextG.add(spread.get(currentStr));
                }
            }
            nextG.add('.'); nextG.add('.');

            for(int n = 0; n < nextG.size(); n++) {
                currentG.set(n ,nextG.get(n));
            }
            nextG.clear();
            numFrontPadding += updatePadding(currentG);
            //System.out.println(currentG.size());


            // Count Score
            int score = 0;
            int currentPlant = -numFrontPadding;
            for(int k = 0; k < currentG.size(); k++) {
                if (currentG.get(k).equals('#')) {
                    score += currentPlant;
                }
                currentPlant++;
            }
            if(i == 20) gen20Score = score;
            // --- Printing ---
            System.out.print(i + ": "+score + " ");

            // Print generation
            for(Character c : currentG) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println("------------- PART 1 -------------");
        System.out.println("Score for 20 generations is " + gen20Score);
        System.out.println("------------- PART 2 -------------");
        System.out.println("Would take way to long to run the program 5 000 000 000 times.");
        System.out.println("Instead run for a couple of hundred times and try to find pattern.");
        System.out.println("In my case, after generation 126 each generations score increased by 36.");
        System.out.println("(50000000000-126)*36 + 3994 = 1799999999458");



    }

    public static int updatePadding(ArrayList<Character> list) {
        int frontPadding = 0;
        int endPadding = 0;
        for(int i = 0; i < 4; i++) {
            if(list.get(i).equals('#') && frontPadding < 4 - i) {
                frontPadding = 4 - i;
            }
        }
        for(int i = 0; i < 4; i++) {
            if(list.get(list.size() - 4 + i).equals('#') && endPadding < i + 1) {
                endPadding = i + 1;
            }
        }

        for(int i = 0; i < frontPadding; i++) list.add(0,'.');
        for(int i = 0; i < endPadding; i++) list.add('.');

        return frontPadding;
    }
}
