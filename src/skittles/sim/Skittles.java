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
		
		for (int i = 2; i < 8; i++) {
		
			Parameters.BIG_AMOUNT_DIVISOR = i;
			
			for (int j = 1; j <= 15; j++) {
				Parameters.GIVE_UP_TURNS = j;
				
				double total = 0;
				double totalRank = 0;
				
				for (int k = 1; k <= 10; k++) {
					Game gamNew = new Game( "GameConfig.xml" );
					gamNew.runGame();
					total+=gamNew.storedScore();
					totalRank+=gamNew.rank;
				}
				double avg = total / 10.0;
				double avgRank = totalRank/10.0;
				
				try {
					bw.write(i+ "," + j + ","+avg + "," + avgRank);
					bw.newLine();
				} catch (IOException e) {
				}
	            
			}
		}
			
		try {
			bw.close();
		} catch (IOException e) {
		}
		
	}
}
