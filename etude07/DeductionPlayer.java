import java.util.ArrayList;

public class DeductionPlayer implements Player {
    
    int turns = 0;
    public Action takeTurn(State s, int[] dice){
        ArrayList<Float> pointEstimates = new ArrayList<>();
        int mySum = 0;
        turns ++;
        if (turns > 20){
            return Action.SHOWDOWN;
        }
        Action myAction = Action.ROLL;
        for(int i : dice){
            mySum += i;
        }
            
        for(Integer player : s.playersRemaining){
            int thisMax = 0;
            int thisMin = 0;
            int numRolls = 0;
            ArrayList<Integer> playersRolls = (s.getRolls(player));
            int[] maxDice = new int[5];
            for(Integer roll : playersRolls){
                if(roll > 0) {
                    for(int i = 0;i < maxDice.length;i++){
                        if( roll > maxDice[i]){
                            maxDice[i] = roll;
                            break;
                        }
                    }
                    thisMin += roll;
                    thisMax += roll;
                    numRolls++;
                }
            }
            float estimate = 0;
            if(numRolls <= 5){
                thisMin += (5 - numRolls);
                thisMax += (6*(5-numRolls));
                estimate = (float)(thisMax + thisMin)/2;
                pointEstimates.add(estimate);
            }else{
                for(int num : maxDice){
                    estimate += num;
                }
            }
                
            int numTurn = 1;//need to find the number of turns we are at so we can scale into later turns
            if(estimate*(0.8+(0.04*numTurn)) > mySum){
                myAction = Action.FOLD;
            }else if(mySum*(0.8+(0.04*numTurn))  > estimate){
                myAction = Action.SHOWDOWN;//Unless our hand is very good and we want to be greedy
            }else if(myAction != Action.FOLD){ //this ensures that if ANYONE has a really good hand the bot will fold
                myAction = Action.ROLL;
            }

            /*System.out.println("known Rolls: " + playersRolls + 
                               " minimum Points: " + thisMin + 
                               " maximumPoints: " + thisMax +
                               " Point Estimate: " + estimate +
                               " My Points: " + mySum);*/
        }
        return myAction;
    }
    
    public Action actAtShowdown(State s, int[] dice){
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
