package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class Swipe extends SpellCard {

	
	/**
	 * Constructor
	 * 
	 * @param hasBeenUsed Whether the card has already been used or not
	 */
	public Swipe(boolean hasBeenUsed) {
		super("Swipe", (byte)4, hasBeenUsed);
	}

	/**
	 * Constructor
	 * 
	 * Defaults to hasBeenUsed = false
	 */
	public Swipe() {
		this(false);
	}

	@Override
	public Object deepCopy() {
		return new Swipe(this.hasBeenUsed);
	}
	
	/**
	 * 
	 * Use the card on the given target
	 * 
	 * Deals 4 damage to an enemy and 1 damage to all other enemies
	 * 
	 *
     *
     * @param side
     * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
     *
     * @return The boardState is manipulated and returned
	 */
	@Override
	protected HearthTreeNode use_core(
			PlayerSide side,
			Minion targetMinion,
			HearthTreeNode boardState,
			Deck deckPlayer0,
			Deck deckPlayer1,
			boolean singleRealizationOnly)
		throws HSException
	{
		if (isCurrentPlayer(side)) {
			return null;
		}
		
		HearthTreeNode toRet = super.use_core(side, targetMinion, boardState, deckPlayer0, deckPlayer1, singleRealizationOnly);
		toRet = targetMinion.takeDamage((byte)4, PlayerSide.CURRENT_PLAYER, side, toRet, deckPlayer0, deckPlayer1, true, false);
		if (isNotHero(targetMinion))
			toRet = toRet.data_.getWaitingPlayerHero().takeDamage((byte)1, PlayerSide.CURRENT_PLAYER, side, boardState, deckPlayer0, deckPlayer1, true, false);
		for (Minion minion : PlayerSide.WAITING_PLAYER.getPlayer(toRet).getMinions()) {
			if (minion != targetMinion)
				toRet = minion.takeDamage((byte)1, PlayerSide.CURRENT_PLAYER, side, toRet, deckPlayer0, deckPlayer1, true, false);
		}
				
		return toRet;
	}
}
