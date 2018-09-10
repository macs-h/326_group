import java.util.ArrayList;

public class CombinedPlayer implements Player {

    private ArrayList<Float> pointEstimates = new ArrayList<>();
    private int numTurn = -1;
    
        public Action takeTurn(State s, int[] dice){

            //Self-centredBot
            int max = 0;
            int min = 6;
            numTurn++;
            Action selfAction = Action.SHOWDOWN;
            for (int d : dice){
                if (d > max){
                    max = d;
                }
                if (d < min){
                    min = d;
                }
            }
            if (max < 3){
                return Action.FOLD;
            } else if (min < 3){
                selfAction = Action.ROLL;
            }

            //GuessBot
            
           
            int mySum = 0;
            Action myAction = Action.ROLL;
            for(int i : dice){
                mySum += i;
            }
            
            for(Integer player : s.playersRemaining){
                int thisMax = 0;
                int thisMin = 0;
                int numRolls = 0;
                ArrayList<Integer> playersRolls = (s.getRolls(player));
                for(Integer roll : playersRolls){
                    if(roll > 0) {
                        thisMin += roll;
                        thisMax += roll;
                        numRolls++;
                    }
                }
                thisMin += (5 - numRolls);
                thisMax += (6*(5-numRolls));
                float estimate = (float)(thisMax + thisMin)/2;
                pointEstimates.add(estimate);

                
                if(estimate*(0.8+(0.04*numTurn)) > mySum){
                    return Action.FOLD; //this ensures that if ANYONE has a really good hand the bot will fold
                }else {
                    if(mySum*(0.8+(0.04*numTurn))  > estimate){
                        myAction = Action.SHOWDOWN;//Unless our hand is very good and we want to be greedy
                    }else{
                        myAction = Action.ROLL;
                    }
                }

                System.out.println("known Rolls: " + playersRolls + 
                                    " minimum Points: " + thisMin + 
                                    " maximumPoints: " + thisMax +
                                    " Point Estimate: " + estimate +
                                    " My Points: " + mySum);
            }
            System.out.println("numTurn = " + numTurn);
            if (selfAction == Action.SHOWDOWN && myAction == Action.SHOWDOWN){
                return Action.SHOWDOWN;
            }
            return Action.ROLL;
        }
    
        public Action actAtShowdown(State s, int[] dice){
            int sum = 0;
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
            return Action.STAY;
        }
    }
