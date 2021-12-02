package agh.ics.oop;
public class OptionsParser {

    public static MoveDirection[] parse(String[] args){

        int len = 0;
        for (String arg : args) {
            if (MoveDirection.fromString(arg) != null) len++;
        }
        MoveDirection[] res = new MoveDirection[len];
        int j=0;
        for (String arg : args) {
            if (MoveDirection.fromString(arg) != null) {
                res[j] = MoveDirection.fromString(arg);
                j++;
            }
        }
        return res;
    }
}
