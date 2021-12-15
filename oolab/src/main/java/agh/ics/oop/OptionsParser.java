package agh.ics.oop;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OptionsParser {

    public static MoveDirection[] parse(String[] args) throws IllegalArgumentException{
        String[] correctString = {"f", "b", "r", "l", "forward", "backward", "right", "left"};
        Set<String> correct = new HashSet<String>(Arrays.asList(correctString));

        for (String arg : args) {
            if(!correct.contains(arg)){
                throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }
        int len = args.length;
        MoveDirection[] res = new MoveDirection[len];
        int j=0;
        for (String arg : args) {
            res[j] = MoveDirection.fromString(arg);
            j++;
        }
        return res;
    }
}
