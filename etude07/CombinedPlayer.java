import java.util.ArrayList;

public class CombinedPlayer implements Player {

    private ArrayList<Float> pointEstimates = new ArrayList<>();
    private int numTurn = 1;
    float bestEstimate = 1000;
    
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
            
        if (min > 3){
            return Action.SHOWDOWN;
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
            if (estimate < bestEstimate){
                bestEstimate = estimate;
            }
                
            if(estimate*(0.8+(0.04*numTurn)) > mySum || max < 3){
               return Action.FOLD;
            }else if(mySum*(0.8+(0.04*numTurn)) > estimate && min > 3){
                myAction.add(Action.SHOWDOWN);//Unless our hand is very good and we want to be greedy
            }else { //this ensures that if ANYONE has a really good hand the bot will fold
                myAction.add(Action.ROLL);
            }
            /*System.out.println("known Rolls: " + playersRolls + 
                               " minimum Points: " + thisMin + 
                               " maximumPoints: " + thisMax +
                               " Point Estimate: " + estimate +
                               " My Points: " + mySum);*/
        }
        System.out.println("numTurn = " + numTurn);
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
        if(sum > bestEstimate){
            return Action.STAY;
        }else{
            return Action.EXIT;
        }
    }
}
