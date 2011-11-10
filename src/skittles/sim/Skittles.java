package skittles.sim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import skittles.g6.strategy.Parameters;

public class Skittles 
{
	public static void main( String[] args )
	{		
		
		Game gamNew2 = new Game( "GameConfig.xml" );
		gamNew2.runGame();
		
		System.exit(0);
		
		
		BufferedWriter bw = null;
		try {
           bw = new BufferedWriter(new FileWriter("/Users/yufeiliu/Desktop/tuning.csv"));
            
           
		} catch (Exception e) {
		}
		
			Parameters.BIG_AMOUNT_DIVISOR = 5;
			
			for (int j = 1; j <= 15; j++) {
				Parameters.GIVE_UP_TURNS = j;
				
				double total = 0;
				
				for (int k = 1; k <= 10; k++) {
					Game gamNew = new Game( "GameConfig.xml" );
					gamNew.runGame();
					total+=gamNew.storedScore();
				}
				double avg = total / 10.0;
				
				try {
					bw.write(j + ","+avg);
					bw.newLine();
				} catch (IOException e) {
				}
	            
			}
			
		try {
			bw.close();
		} catch (IOException e) {
		}
		
	}
}
