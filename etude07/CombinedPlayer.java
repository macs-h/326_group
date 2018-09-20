import java.util.ArrayList;
import java.util.Collections;

public class CombinedPlayer implements Player {

    private ArrayList<Float> pointEstimates = new ArrayList<>();
    private int numTurn = 1;
    float bestEstimate = 18;
    
    public Action takeTurn(State s, int[] dice){

        //Self-centredBot
        int max = 0;
        int min = 6;
        //numTurn++;
        for (int d : dice){
            if (d > max){
                max = d;
            }
            if (d < min){
                min = d;
            }
        }
        /*if (max < 3){
          return Action.FOLD;
          } else if (min < 3){
          selfAction = Action.ROLL;
          }*/

        //GuessBot
            
           
        int mySum = 0;
        ArrayList <Action> myAction = new ArrayList<>();
        for(int i : dice){
            mySum += i;
        }
            
        if (min > 4){
            return Action.SHOWDOWN;
        }
        
        for(Integer player : s.playersRemaining){
            
            int thisMax = 0;
            int thisMin = 0;
            int numRolls = 0;
            ArrayList<Integer> playersRolls = (s.getRolls(player));
            ArrayList<Integer> maxDice = new ArrayList<>(Collections.nCopies(5,0));
            int notTaken = 1;
            for(Integer roll : playersRolls){
                Integer arrayMin = Collections.min(maxDice);
                if(roll > 0) {
                    thisMin += roll;
                    thisMax += roll;
                    numRolls++;
                    if(roll > arrayMin){
                        maxDice.set(maxDice.indexOf(arrayMin),roll);
                    }
                } else {
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
            float estimate = 0;
            if(numRolls <= 5){
                thisMin += (5 - numRolls)*notTaken;
                thisMax += (6*(5-numRolls));
                estimate = (float)(thisMax + thisMin)/2;
                pointEstimates.add(estimate);
            }else{
                for(int num : maxDice){
                    estimate += num;
                }
            }
            if (estimate < bestEstimate){
                bestEstimate = estimate;
            }
            System.out.println("known Rolls: " + playersRolls + 
                               " minimum Points: " + thisMin + 
                               " maximumPoints: " + thisMax +
                               " Point Estimate: " + estimate +
                               " My Points: " + mySum);
                
            if(estimate*(0.8+(0.04*numTurn)) > mySum || mySum < thisMin || max < 3){
               return Action.FOLD;
            }else if(mySum*(0.8+(0.04*numTurn)) > estimate || mySum >= thisMax){
                myAction.add(Action.SHOWDOWN);//Unless our hand is very good and we want to be greedy
            }else { //this ensures that if ANYONE has a really good hand the bot will fold
                myAction.add(Action.ROLL);
            }
            
        }
        //System.out.println("numTurn = " + numTurn);
        for (Action action : myAction){
            if (action == Action.ROLL){
                return Action.ROLL;
            }
        }
        return Action.SHOWDOWN;
    }
    
    public Action actAtShowdown(State s, int[] dice){
        /*int sum = 0;
          Action selfAction = Action.STAY;
          Action deducAction = Action.STAY;
          for (int d : dice){
          sum += d;
          }
          for (Float estimate : pointEstimates){
          if (estimate*(0.9+(0.04*numTurn)) > sum){
          deducAction = Action.EXIT;
          }
          }
          if (sum < 15){
          selfAction = Action.EXIT;
          }
          if (deducAction == Action.EXIT || selfAction == Action.EXIT){
          return Action.EXIT;
          }
          return Action.STAY;*/

        int sum = 0;
        for (int d : dice){
            sum += d;
        }
        /*if (sum < 20){
            return Action.EXIT;
        }
        return Action.STAY;*/
        //System.out.println(bestEstimate + " " + sum);
        if(sum >= bestEstimate){
            return Action.STAY;
        }else{
            return Action.EXIT;
        }
    }
}
