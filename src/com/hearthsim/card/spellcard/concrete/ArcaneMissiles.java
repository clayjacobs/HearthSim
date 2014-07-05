package com.hearthsim.card.spellcard.concrete;

import java.util.Iterator;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.util.BoardState;

public class ArcaneMissiles extends SpellCard {
	
	/**
	 * Constructor
	 * 
	 * @param hasBeenUsed Whether the card has already been used or not
	 */
	public ArcaneMissiles(boolean hasBeenUsed) {
		super("Arcane Missiles", (byte)1, hasBeenUsed);
	}

	/**
	 * Constructor
	 * 
	 * Defaults to hasBeenUsed = false
	 */
	public ArcaneMissiles() {
		this(false);
	}

	/**
	 * 
	 * Use the card on the given target
	 * 
	 * Deals 1 damage to three random enemy characters.  The characters can repeat.
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
		if (playerIndex == 0) {
			return null;
		}
		
		if (minionIndex > 0) {
			return null;
		}
		
		int numMissiles = 3;
		
		int numTargets = boardState.getNumMinions_p1() + 1;
		int index = 0;
		while(index < numMissiles) {
			int targetIndex = (int)(numTargets * Math.random());
			if (targetIndex == 0 && boardState.getHero_p1().getHealth() > 0) {
				boardState.getHero_p1().setHealth((byte)(boardState.getHero_p1().getHealth() - 1));
				++index;
			} else if (targetIndex > 0 && boardState.getMinion_p1(targetIndex-1).getHealth() > 0) {
				boardState.getMinion_p1(targetIndex-1).setHealth((byte)(boardState.getMinion_p1(targetIndex-1).getHealth() - 1));
				++index;
			}
			if (boardState.getHero_p1().getHealth() <= 0) {
				break;
			}
		}

		return super.useOn(thisCardIndex, playerIndex, minionIndex, boardState, deck);
	}
}
