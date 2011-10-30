package skittles.g7.strategy;

import skittles.g7.CompulsiveEater;
import skittles.sim.Offer;

public interface OfferGenerator {
	public void setPlayer(CompulsiveEater player);
	public void setCurrentOffers(Offer[] offers);
	public Offer getOffer();
}
