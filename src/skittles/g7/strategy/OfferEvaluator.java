package skittles.g7.strategy;

import skittles.g7.CompulsiveEater;
import skittles.sim.Offer;

public interface OfferEvaluator {
	public void setPlayer(CompulsiveEater player);
	/*
	 * Finds max of all offers of: taste * (((currInventory + get)^2 - currInventory^2) - (currInventory^2 - (currInventory + get)^2))
	 */
	public Offer getBestOffer(Offer[] offers);
}
