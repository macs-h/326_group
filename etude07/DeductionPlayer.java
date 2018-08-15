import java.util.ArrayList;

public class DeductionPlayer implements Player {
    
        public Action takeTurn(State s, int[] dice){
            ArrayList<Float> pointEstimates = new ArrayList<>();
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
                
                int numTurn = 1;//need to find the number of turns we are at so we can scale into later turns
                if(estimate*(0.8+(0.04*numTurn)) > mySum){
                    myAction = Action.FOLD;
                }else if(mySum*(0.8+(0.04*numTurn))  > estimate){
                    myAction = Action.SHOWDOWN;//Unless our hand is very good and we want to be greedy
                }else if(myAction != Action.FOLD){ //this ensures that if ANYONE has a really good hand the bot will fold
                    myAction = Action.ROLL;
                }

                System.out.println("known Rolls: " + playersRolls + 
                                    " minimum Points: " + thisMin + 
                                    " maximumPoints: " + thisMax +
                                    " Point Estimate: " + estimate +
                                    " My Points: " + mySum);
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