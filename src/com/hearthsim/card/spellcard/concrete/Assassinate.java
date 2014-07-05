package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.util.BoardState;

public class Assassinate extends SpellCard {

	/**
	 * Constructor
	 * 
	 * @param hasBeenUsed Whether the card has already been used or not
	 */
	public Assassinate(boolean hasBeenUsed) {
		super("Assassinate", (byte)5, hasBeenUsed);
	}

	/**
	 * Constructor
	 * 
	 * Defaults to hasBeenUsed = false
	 */
	public Assassinate() {
		this(false);
	}

	/**
	 * 
	 * Use the card on the given target
	 * 
	 * This card destroys an enemy minion.
	 * 
	 * @param thisCardIndex The index (position) of the card in the hand
	 * @param playerIndex The index of the target player.  0 if targeting yourself or your own minions, 1 if targeting the enemy
	 * @param minionIndex The index of the target minion.
	 * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
	 * 
	 * @return The boardState is manipulated and returned
	 */
	@Override
	public BoardState useOn(int thisCardIndex, int playerIndex, int minionIndex, BoardState boardState, Deck deck) {
		if (playerIndex == 0 || minionIndex == 0) {
			return null;
		}
		
		boardState.getMinion_p1(minionIndex - 1).setHealth((byte)0);
		boardState.removeMinion_p1(minionIndex - 1);
		return super.useOn(thisCardIndex, playerIndex, minionIndex, boardState, deck);
	}
}
