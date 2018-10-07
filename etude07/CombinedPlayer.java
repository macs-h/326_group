import java.util.ArrayList;
import java.util.Collections;

public class CombinedPlayer implements Player {

    private int numTurn = 1;//scaling as the game goes on?
    float bestEstimate = 18;//used when we hit a showdown
    
    //our bot chooses an action based on certain threshold (self-centred) vales
    //as well as taking into account what it estimates the opponents scores to
    //be.
    public Action takeTurn(State s, int[] dice){

        //Self-centredBot
        //this part calculated the min and max of our dice as well as
        //the sum of our dice.
        int max = 0;
        int min = 6;
        for (int d : dice){
            if (d > max){
                max = d;
            }
            if (d < min){
                min = d;
            }
        }
           
        int mySum = 0;
        ArrayList <Action> myAction = new ArrayList<>();
        for(int i : dice){
            mySum += i;
        }
        
        //this acts as a baseline, if our dice are all 5's and 6's we showdown.
        if (min > 4){
            return Action.SHOWDOWN;
        }
        //GuessBot
        //the guessBot tries to guess what other players hands are and make
        //a decision based on that.
        for(Integer player : s.playersRemaining){
            
            int thisMax = 0;
            int thisMin = 0;
            int numRolls = 0;
            ArrayList<Integer> playersRolls = (s.getRolls(player));
            ArrayList<Integer> maxDice = new ArrayList<>(Collections.nCopies(5,0));
            int notTaken = 1;
            //loops through the previous rolls of opponents
            for(Integer roll : playersRolls){
                Integer arrayMin = Collections.min(maxDice);
                if(roll > 0) {
                    //if the dice was taken we use that to build up a hand that
                    //approximates the opponents hand.
                    thisMin += roll;
                    thisMax += roll;
                    numRolls++;
                    if(roll > arrayMin){
                        maxDice.set(maxDice.indexOf(arrayMin),roll);
                    }
                } else {
                    //if the dice wasnt taken we know that they have no die 
                    //under that number.
                    if ((-1)*roll > notTaken){
                        notTaken = (-1)*roll;
                    }
                    if (arrayMin < (-1)*roll){
                        while (Collections.min(maxDice) < (-1)*roll){
                            maxDice.set(maxDice.indexOf(Collections.min(maxDice)),(-1)*roll);
                        }
                    }
                }
            }
            //here we try to calculate an estimate of the opponents hand.
            //if the opponent hasnt taken 5 rolls we assume for the min that
            //the unknown dice are 1's(or the highest dice that they rejected),
            //for the max we assume that the unknown dice are all 6 and then 
            //we find an average based on that.
            //TODO sometimes a dice might overrite a dice that we know about 
            //but we assume that the new dice overrites an unknown dice.
            float estimate = 0;
            if(numRolls <= 5){
                thisMin += (5 - numRolls)*notTaken;
                thisMax += (6*(5-numRolls));
                estimate = (float)(thisMax + thisMin)/2;
            }else{
                for(int num : maxDice){
                    estimate += num;
                }
            }
            if (estimate < bestEstimate){
                bestEstimate = estimate;
            }
//            System.out.println("known Rolls: " + playersRolls + 
//                               " minimum Points: " + thisMin + 
//                               " maximumPoints: " + thisMax +
//                               " Point Estimate: " + estimate +
//                               " My Points: " + mySum);
            
            //here we choose, based on each opponents score, what action we 
            //wnt to take. to do this we check if our score is less than or
            //greater than the opponents score by a (possibly scaling) 
            //percentage and votes for a particular action based on that.
            if(estimate*(0.8+(0.04*numTurn)) > mySum || mySum < thisMin || max < 3){
               return Action.FOLD;
            }else if(mySum*(0.8+(0.04*numTurn)) > estimate || mySum >= thisMax){
                myAction.add(Action.SHOWDOWN);
                //maybe implement greedyness if our hand is well above anyone elses
            }else {
                myAction.add(Action.ROLL);
            }
            
        }
        //the action we take is determined by the following priority heirachy:
        // FOLD > ROLL > SHOWDOWN. Therefore if any of our opponents make us 
        //want to fold, we immediately fold. otherwise if any of the opponents 
        //want us to roll, we choose to roll. for us to choose to showdown
        //the votes must be unanimous. 
        for (Action action : myAction){
            if (action == Action.ROLL){
                return Action.ROLL;
            }
        }
        return Action.SHOWDOWN;
    }
    //our bot chooses whether to stay or not based on the largest estimate of
    //an opponents hand. this ensures that it will only stay in the showdown if 
    //it thinks it will win or tie (still possibility of losing due to chance,
    //its a gambler at heart).
    public Action actAtShowdown(State s, int[] dice){

        int sum = 0;
        for (int d : dice){
            sum += d;
        }
        
        if(sum >= bestEstimate){
            return Action.STAY;
        }else{
            return Action.EXIT;
        }
    }
}
