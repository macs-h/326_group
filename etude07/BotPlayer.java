public class BotPlayer implements Player {

    public Action takeTurn(State s, int[] dice){
        
        int max = 0;
        for (int d : dice){
            if (d > max){
                max = d;
            }
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
