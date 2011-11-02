package skittles.g6.strategy;

import java.util.ArrayList;
import java.util.Collections;

import skittles.g6.CompulsiveEater;
//import skittles.g6.strategy.PreferenceEvaluatorImpl.Pair;
import skittles.sim.Offer;

public class OfferGeneratorImplementer implements OfferGenerator{

	CompulsiveEater myCompulsiveEater;
	ArrayList<Offer[]> offersHistory;
	int intColorNum;
	int turn;
	final int TEMP_OFFER_SIZE = 2;
	
	public OfferGeneratorImplementer(){
		offersHistory = new ArrayList<Offer[]>();
		turn = 0;
	}
	
	public OfferGeneratorImplementer(int intColorNum){
		offersHistory = new ArrayList<Offer[]>();
		this.intColorNum = intColorNum;
		turn = 0;
	}
	
	public void setPlayer(CompulsiveEater player) {
		myCompulsiveEater = player;
	}

	public void setCurrentOffers(Offer[] offers) {
		Offer[] offerCopy = new Offer[offers.length];
		for (int i=0; i<offers.length; i++){
			offerCopy[i] = offers[i];
		}
		offersHistory.add(offerCopy);
		//offersHistory.add(offers);
	}

	public Offer getOffer() {
		Offer newOffer = new Offer(myCompulsiveEater.getPlayerIndex(), intColorNum);
		int[] intOffer = new int[intColorNum];
		int[] intDesire = new int[intColorNum];
		//TODO: smaller size in beginning for fewer players, because of law of large numbers
			if (turn==0){
				//first check what you ate
				int lastEatIndex = myCompulsiveEater.getIntLastEatIndex();
				//Ask for 2 of the last one you ate if its good (>0.5 for now)
				if (myCompulsiveEater.getPreferences()[lastEatIndex] >= 0.5){
					
					
					
					intDesire[lastEatIndex] = TEMP_OFFER_SIZE;
					
					/* offer 2 skittles of the highest amount you have, unless the highest amount 
					   skittle is the same as that of the lastEatIndex */
					//on turn 0 you only know one color
					int high = getSkittleOfHighestAmount();
					if (high == lastEatIndex)
						high = getSkittleOf2ndHighestAmount();
					intOffer[high] = TEMP_OFFER_SIZE;
					
				}
				else {//if(myCompulsiveEater.getPreferences()[lastEatIndex] < 0){
					intOffer[lastEatIndex] = TEMP_OFFER_SIZE;
					
					/* offer 2 skittles of the last one you ate b/c you don't like it
					 * ask for 2 skittles of the lowest amount you have, or 
					 * 2 skittles of 2ndLowestAmount if lowest amount is of lastEatIndex 
					 */
					int low = getSkittleOfLowestAmount();
					if (low == lastEatIndex)
						low = getSkittleOf2ndLowestAmount();
					intDesire[low] = TEMP_OFFER_SIZE;
				}
			}
			else{
				intDesire[getHighestPreference()] = TEMP_OFFER_SIZE;
				intOffer[getLowestPreference()] = TEMP_OFFER_SIZE;
			}
		newOffer.setOffer(intOffer, intDesire);

		/*if (isOfferCold(newOffer)){
			//generateNewOffer
			//perhaps change to 1 and 1
		}*/

		turn++;
		return newOffer;
	}

	private int getHighestPreference(){
		myCompulsiveEater.getPreferences();
		double[] aDblTastesCopy =  myCompulsiveEater.getPreferences().clone();		
		ArrayList<Pair<Double, Integer>> preferencesAndColors = new ArrayList<Pair<Double,Integer>>();
		for (int i=0; i<aDblTastesCopy.length; i++){
			preferencesAndColors.add(new Pair<Double,Integer>(aDblTastesCopy[i], i));
		}
		Collections.sort(preferencesAndColors);
		return preferencesAndColors.get(0).getBack();
		
	}
	
	private int getLowestPreference(){
		double[] aDblTastesCopy =  myCompulsiveEater.getPreferences().clone();		
		ArrayList<Pair<Double, Integer>> preferencesAndColors = new ArrayList<Pair<Double,Integer>>();
		
		for (int i=0; i<aDblTastesCopy.length; i++){
			preferencesAndColors.add(new Pair<Double,Integer>(aDblTastesCopy[i], i));
		}
		Collections.sort(preferencesAndColors);
		return preferencesAndColors.get(preferencesAndColors.size()-1).getBack();
	}
	
	/*
	 * Below are methods based on the amount of skittles you have.
	 */
	
	public int getSkittleOfHighestAmount(){
		int[] aintInHand = myCompulsiveEater.getAIntInHand();
		int maxValue = 0;
		for (int i=0; i<aintInHand.length; i++){
			if (aintInHand[i] > aintInHand[maxValue])
				maxValue = i;
		}
		return maxValue;
	}
	
	public int getSkittleOfLowestAmount(){
		int[] aintInHand = myCompulsiveEater.getAIntInHand();
		int minValue = 0;
		for (int i=0; i<aintInHand.length; i++){
			if (aintInHand[i] < aintInHand[minValue])
				minValue = i;
		}
		return minValue;
	}
	
	public int getSkittleOf2ndHighestAmount(){
		int[] aintInHand = myCompulsiveEater.getAIntInHand();
		int high = getSkittleOfHighestAmount();
		int maxValue = 0;
		for (int i=0; i<aintInHand.length; i++){
			if (i == high){}
			else{
				if (aintInHand[i] > aintInHand[maxValue])
					maxValue = i;
			}
		}
		return maxValue;
	}
	
	public int getSkittleOf2ndLowestAmount(){
		int[] aintInHand = myCompulsiveEater.getAIntInHand();
		int low = getSkittleOfHighestAmount();
		int minValue = 0;
		for (int i=0; i<aintInHand.length; i++){
			if (i == low){}
			else{
				if (aintInHand[i] < aintInHand[minValue])
					minValue = i;
			}
		}
		return minValue;
	}
	
	/**
	 * check if Offer is cold by checking against previous two offers
	 * if previous two offers are the same as your current offer, then
	 * the offer is cold
	 * @param anOffer
	 * @return
	 */
	
	// TODO: check for null offers

/*	public boolean isOfferCold(Offer anOffer){
		int myPlayerIndex = myCompulsiveEater.getPlayerIndex();
		if (turn > 1){
			if ( compareOffers(offersHistory.get(turn)[myPlayerIndex], anOffer)
					|| compareOffers(offersHistory.get(turn-1)[myPlayerIndex], anOffer) ){
				return true;
			}
		}
		return false;
	}*/
	
	public boolean compareOffers(Offer offer1, Offer offer2){
		for (int i=0; i<intColorNum; i++){
			if (offer1.getOffer()[i] != offer2.getOffer()[i])
				return false;
		}
		return true;
	}
	
	private class Pair<T extends Comparable<T>, W> implements Comparable<Pair<T, W>> {

		private T front;
		private W back;
		
		public Pair(T aFront, W aBack) {
			this.front = aFront;
			this.back = aBack;
		}
		
		@SuppressWarnings("unused")
		public T getFront() { return front; }
		public W getBack() { return back; }
		
		//only compare on the front element
		public int compareTo(Pair<T, W> arg0) {
			return -1 * this.front.compareTo(arg0.front);
		}
		
	}
}

