package agh.ics.oop;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OptionsParser {
    public static MoveDirection[] parse(List<String> args) throws IllegalArgumentException{
        String[] correctString = {"f", "b", "r", "l", "forward", "backward", "right", "left"};
        Set<String> correct = new HashSet<String>(Arrays.asList(correctString));

        for (String arg : args) {
            if(!correct.contains(arg)){
                throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }
        int len = args.size();
        MoveDirection[] res = new MoveDirection[len];
        int j=0;
        for (String arg : args) {
            res[j] = MoveDirection.fromString(arg);
            j++;
        }
        return res;
    }
    public static MoveDirection[] parseDirections(String arg) throws IllegalArgumentException{
        String[] directions = arg.split(", ");
        String[] correctString = {"f", "b", "r", "l", "forward", "backward", "right", "left"};
        Set<String> correct = new HashSet<String>(Arrays.asList(correctString));

        for (String dir : directions) {
            if(!correct.contains(dir)){
                throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }

        int len = directions.length;
        MoveDirection[] res = new MoveDirection[len];
        int j=0;
        for (String dir : directions) {
            res[j] = MoveDirection.fromString(dir);
            j++;
        }
        return res;
    }
}
