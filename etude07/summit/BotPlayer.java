import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class BotPlayer implements Player {

    public Action takeTurn(State s, int[] dice){
        int max = 0;
        for (int d : dice){
            if (d > max){
                max = d;
            }
        }
        ArrayList<Integer> players = new ArrayList<Integer>();
        players.addAll(s.playersRemaining);
        Object[] opponents = players.toArray();
        int i = 0;
        while (opponents[i] != null){
            if (Collections.min(s.getRolls(i)) < -(dice[2])){
                return Action.FOLD;
            }
            i++;
        }
                
        if (max < 3){
            return Action.FOLD;
        }
        for (int d : dice){
            if (d < 3){
                return Action.ROLL;
            }
        }
        return Action.SHOWDOWN;
    }

    public Action  actAtShowdown(State s, int[] dice){
        int sum = 0;
        for (int d : dice){
            sum += d;
        }
        if (sum < 20){
            return Action.EXIT;
        }
        return Action.STAY;
    }
}
