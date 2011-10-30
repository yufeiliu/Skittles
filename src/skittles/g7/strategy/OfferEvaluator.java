package skittles.g7.strategy;

import skittles.g7.CompulsiveEater;
import skittles.sim.Offer;

public interface OfferEvaluator {
	public void setPlayer(CompulsiveEater player);
	public Offer getBestOffer(Offer[] offers);
}
