package skittles.sim;

import java.util.ArrayList;

public class Offer 
{
	private int[] aintOffer;
	private int[] aintDesire;
	private int intOfferedByIndex;
	private boolean blnOfferLive;
	private int intColorNum;
	private int intPickedByIndex = -1;
	
	private boolean hasBeenSet = false; 
	
	public Offer( int intOfferedByIndex, int intColorNum )
	{
		this.intColorNum = intColorNum;
		this.intOfferedByIndex = intOfferedByIndex;
		aintOffer = new int[ intColorNum ];
		aintDesire = new int[ intColorNum ];
		blnOfferLive = true;
	}
	
	public void setOffer( int[] aintOffer, int[] aintDesire )
	{
		if ( hasBeenSet == false )
		{	
			int intOfferCount = 0;
			int intDesireCount = 0;
			for ( int intColorIndex = 0; intColorIndex < intColorNum; intColorIndex ++ )
			{
				intOfferCount += aintOffer[ intColorIndex ];
				intDesireCount += aintDesire[ intColorIndex ];
			}
			if ( intOfferCount != intDesireCount )
			{
				System.out.println( "Player #" + intOfferedByIndex + "'s offer is invalid" );
				System.out.print("offer: ");
				for (int i=0; i<intColorNum; i++){
					System.out.print(aintOffer[i] + ", ");
				}
				System.out.println();
				System.out.print("desire: ");
				for (int j=0; j<intColorNum; j++){
					System.out.print(aintDesire[j] + ", ");
				}
			}
			else
			{
				this.aintOffer = aintOffer;
				this.aintDesire = aintDesire;
			}
			hasBeenSet = true;
		}
	}
	
	public int[] getOffer()
	{
		return aintOffer;
	}
	
	public int[] getDesire()
	{
		return aintDesire;
	}
	
	public int getOfferedByIndex()
	{
		return intOfferedByIndex;
	}
	
	public boolean getOfferLive()
	{
		return blnOfferLive;
	}
	
	protected void setOfferLive( boolean blnOfferLive )
	{
		this.blnOfferLive = blnOfferLive;
	}
	
	public int getPickedByIndex()
	{
		return intPickedByIndex;
	}
	
	protected void setPickedByIndex( int intPickedByIndex )
	{
		this.intPickedByIndex = intPickedByIndex;
	}
	
	public String toString()
	{
		String strPickedBy = intPickedByIndex == -1 ? "" : " ::: (" + intPickedByIndex + ")";
		String strOffer = "[ ";
		String strDesire = "[ ";
		for ( int intColorIndex = 0; intColorIndex < intColorNum; intColorIndex ++ )
		{
			strOffer += aintOffer[ intColorIndex ] + ", ";
			strDesire += aintDesire[ intColorIndex ] + ", ";
		}
		strOffer = strOffer.substring( 0, strOffer.length() - 2 ) + " ]";
		strDesire = strDesire.substring( 0, strDesire.length() - 2 ) + " ]";
		String strReturn = "Offer: (" + intOfferedByIndex + ") ::: " + strOffer + " <--> " + strDesire + strPickedBy;
		return strReturn;
	}
}
