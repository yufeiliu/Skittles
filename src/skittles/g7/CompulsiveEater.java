package skittles.g7;

import skittles.g7.strategy.OfferEvaluator;
import skittles.g7.strategy.OfferGenerator;
import skittles.g7.strategy.PreferenceEvaluator;
import skittles.sim.*;

public class CompulsiveEater extends Player 
{
	
	private PreferenceEvaluator prefEval;
	private OfferEvaluator offerEval;
	private OfferGenerator offerGen;
	private int turnCounter;
	private boolean discovery;
	private int turnsEatenSame;
	private int lastEatInv;
	
	//===== EVERYTHING BELOW CAME FROM DumpPlayer ====
	private int[] aintInHand;
	private int intColorNum;
	double dblHappiness;
	String strClassName;
	int intPlayerIndex;
	
	private double[] adblTastes;
	private int intLastEatIndex;
	private int intLastEatNum;
	
//	public DumpPlayer( int[] aintInHand )
//	{
//		this.aintInHand = aintInHand;
//		intColorNum = aintInHand.length;
//		dblHappiness = 0;
//	}

	@Override
	public void eat( int[] aintTempEat )
	{
		while(discovery && intLastEatIndex < intColorNum - 1){
			intLastEatIndex++;
			if(aintInHand[intLastEatIndex] == 0){
				intLastEatIndex++;
				continue;
			}
			aintInHand[intLastEatIndex]--;
			aintTempEat[intLastEatIndex] = 1;
			intLastEatNum = 1;
			return;
		}
		discovery = false;
		
		int eatIndex = scanForLeastValuable();
		
		//TODO: Test threshold
		if(adblTastes[eatIndex] > .5 && turnsEatenSame > 3){
			aintTempEat[ eatIndex ] = aintInHand[ eatIndex ];
			aintInHand[ eatIndex ] = 0;
		}
		else{
			aintTempEat[ eatIndex ] = 1;
			aintInHand[ eatIndex ]--;
		}
		intLastEatIndex = eatIndex;
		intLastEatNum = aintTempEat[ eatIndex ] = aintInHand[ eatIndex ];
		
		if(eatIndex == intLastEatIndex)
			turnsEatenSame++;
		else
			turnsEatenSame = 1;
	}
	
	private int scanForLeastValuable(){
		double minTasteValue = 2;
		int minTasteIndex = 0;
		for(int i = 0; i < intColorNum; i++){
			if(aintInHand[i] == 0)
				continue;
			if(adblTastes[i] < minTasteValue){
				minTasteValue = adblTastes[i]; 
				minTasteIndex = i;
			}
		}
		return minTasteIndex;
	}
	
	@Override
	public void offer( Offer offTemp )
	{
		Offer ourOffer = offerGen.getOffer();
		offTemp.setOffer( ourOffer.getOffer(), ourOffer.getDesire() );
	}

	@Override
	public void syncInHand(int[] aintInHand) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void happier(double dblHappinessUp) 
	{
		double dblHappinessPerCandy = dblHappinessUp / Math.pow( intLastEatNum, 2 );
		if ( adblTastes[ intLastEatIndex ] == -1 )
		{
			adblTastes[ intLastEatIndex ] = dblHappinessPerCandy;
		}
		else
		{
			if ( adblTastes[ intLastEatIndex ] != dblHappinessPerCandy )
			{
				System.out.println( "Error: Inconsistent color happiness!" );
			}
		}
	}

	@Override
	public Offer pickOffer(Offer[] aoffCurrentOffers) 
	{
		return offerEval.getBestOffer(aoffCurrentOffers);
	}

	@Override
	public void offerExecuted(Offer offPicked) 
	{
		int[] aintOffer = offPicked.getOffer();
		int[] aintDesire = offPicked.getDesire();
		for ( int intColorIndex = 0; intColorIndex < intColorNum; intColorIndex ++ )
		{
			aintInHand[ intColorIndex ] += aintDesire[ intColorIndex ] - aintOffer[ intColorIndex ];
		}
	}

	@Override
	public void updateOfferExe(Offer[] aoffCurrentOffers) 
	{
		// dumpplayer doesn't care
	}

	@Override
	public void initialize(int intPlayerIndex, String strClassName,	int[] aintInHand) 
	{
		this.intPlayerIndex = intPlayerIndex;
		this.strClassName = strClassName;
		this.aintInHand = aintInHand;
		intColorNum = aintInHand.length;
		turnsEatenSame = 0;
		lastEatInv = 0;
		dblHappiness = 0;
		discovery = true;
		adblTastes = new double[ intColorNum ];
		for ( int intColorIndex = 0; intColorIndex < intColorNum; intColorIndex ++ )
		{
			adblTastes[ intColorIndex ] = -1;
		}
	}
	
	private boolean checkEnoughInHand( int[] aintTryToUse )
	{
		for ( int intColorIndex = 0; intColorIndex < intColorNum; intColorIndex ++ )
		{
			if ( aintTryToUse[ intColorIndex ] > aintInHand[ intColorIndex ] )
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public String getClassName() 
	{
		return "CompulsiveEater";
	}

	@Override
	public int getPlayerIndex() 
	{
		return intPlayerIndex;
	}
	
	public OfferGenerator getOfferGenerator() {
		return offerGen;
	}
	
	public OfferEvaluator getOfferEvaluator() {
		return offerEval;
	}
	
	public PreferenceEvaluator getPreferenceEavluator() {
		return prefEval;
	}
	
	public int getTurnCounter() {
		return turnCounter;
	}
	
	public double[] getPreferences() {
		return adblTastes;
	}
	
	public int[] getAIntInHand() {
		return aintInHand;
	}
}
