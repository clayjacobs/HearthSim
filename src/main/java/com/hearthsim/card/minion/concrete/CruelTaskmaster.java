package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.event.attack.AttackAction;
import com.hearthsim.event.deathrattle.DeathrattleAction;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.factory.BoardStateFactoryBase;
import com.hearthsim.util.tree.HearthTreeNode;

public class CruelTaskmaster extends Minion {

	private static final String NAME = "Cruel Taskmaster";
	private static final byte MANA_COST = 2;
	private static final byte ATTACK = 2;
	private static final byte HEALTH = 2;
	
	private static final boolean TAUNT = false;
	private static final boolean DIVINE_SHIELD = false;
	private static final boolean WINDFURY = false;
	private static final boolean CHARGE = false;
	
	private static final boolean STEALTHED = false;
	private static final boolean HERO_TARGETABLE = true;
	private static final boolean SUMMONED = false;
	private static final boolean TRANSFORMED = false;
	private static final byte SPELL_DAMAGE = 0;
	
	public CruelTaskmaster() {
		this(
				MANA_COST,
				ATTACK,
				HEALTH,
				ATTACK,
				(byte)0,
				(byte)0,
				HEALTH,
				HEALTH,
				(byte)0,
				SPELL_DAMAGE,
				TAUNT,
				DIVINE_SHIELD,
				WINDFURY,
				CHARGE,
				false,
				false,
				false,
				false,
				STEALTHED,
				HERO_TARGETABLE,
				SUMMONED,
				TRANSFORMED,
				false,
				false,
				null,
				null,
				true,
				false
			);
	}
	
	public CruelTaskmaster(	
			byte mana,
			byte attack,
			byte health,
			byte baseAttack,
			byte extraAttackUntilTurnEnd,
			byte auraAttack,
			byte baseHealth,
			byte maxHealth,
			byte auraHealth,
			byte spellDamage,
			boolean taunt,
			boolean divineShield,
			boolean windFury,
			boolean charge,
			boolean hasAttacked,
			boolean hasWindFuryAttacked,
			boolean frozen,
			boolean silenced,
			boolean stealthed,
			boolean hero_targetable,
			boolean summoned,
			boolean transformed,
			boolean destroyOnTurnStart,
			boolean destroyOnTurnEnd,
			DeathrattleAction deathrattleAction,
			AttackAction attackAction,
			boolean isInHand,
			boolean hasBeenUsed) {
		
		super(
			NAME,
			mana,
			attack,
			health,
			baseAttack,
			extraAttackUntilTurnEnd,
			auraAttack,
			baseHealth,
			maxHealth,
			auraHealth,
			spellDamage,
			taunt,
			divineShield,
			windFury,
			charge,
			hasAttacked,
			hasWindFuryAttacked,
			frozen,
			silenced,
			stealthed,
			hero_targetable,
			summoned,
			transformed,
			destroyOnTurnStart,
			destroyOnTurnEnd,
			deathrattleAction,
			attackAction,
			isInHand,
			hasBeenUsed);
	}
	
	@Override
	public Object deepCopy() {
		return new CruelTaskmaster(
				this.mana_,
				this.attack_,
				this.health_,
				this.baseAttack_,
				this.extraAttackUntilTurnEnd_,
				this.auraAttack_,
				this.baseHealth_,
				this.maxHealth_,
				this.auraHealth_,
				this.spellDamage_,
				this.taunt_,
				this.divineShield_,
				this.windFury_,
				this.charge_,
				this.hasAttacked_,
				this.hasWindFuryAttacked_,
				this.frozen_,
				this.silenced_,
				this.stealthed_,
				this.heroTargetable_,
				this.summoned_,
				this.transformed_,
				this.destroyOnTurnStart_,
				this.destroyOnTurnEnd_,
				this.deathrattleAction_,
				this.attackAction_,
				this.isInHand_,
				this.hasBeenUsed);
	}
	
	/**
	 * 
	 * Override for battlecry
	 * 
	 * Battlecry: Deal 1 damage to a minion and give it +2 Attack
	 * 
	 *
     *
     * @param side
     * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
     *
     * @return The boardState is manipulated and returned
	 */
	@Override
	public HearthTreeNode use_core(
			PlayerSide side,
			Minion targetMinion,
			HearthTreeNode boardState,
			Deck deckPlayer0,
			Deck deckPlayer1,
			boolean singleRealizationOnly)
		throws HSException
	{
		//A generic card does nothing except for consuming mana
		HearthTreeNode toRet = super.use_core(side, targetMinion, boardState, deckPlayer0, deckPlayer1, singleRealizationOnly);
		
		if (toRet != null) {
			int thisMinionIndex = PlayerSide.CURRENT_PLAYER.getPlayer(toRet).getMinions().indexOf(this);
			for (int index = 0; index < PlayerSide.CURRENT_PLAYER.getPlayer(toRet).getNumMinions(); ++index) {
				if (index != thisMinionIndex) {
					HearthTreeNode newState = toRet.addChild(new HearthTreeNode((BoardModel)toRet.data_.deepCopy()));
					Minion battlecryTarget = PlayerSide.CURRENT_PLAYER.getPlayer(newState).getMinions().get(index);
					battlecryTarget.setAttack((byte)(PlayerSide.CURRENT_PLAYER.getPlayer(newState).getMinions().get(index).getAttack() + 2));
					newState = battlecryTarget.takeDamage((byte)1, PlayerSide.CURRENT_PLAYER, PlayerSide.CURRENT_PLAYER, newState, deckPlayer0, deckPlayer1, false, true);
					newState = BoardStateFactoryBase.handleDeadMinions(newState, deckPlayer0, deckPlayer1);
				}
			}
			for (int index = 0; index < PlayerSide.WAITING_PLAYER.getPlayer(toRet).getNumMinions(); ++index) {
				HearthTreeNode newState = toRet.addChild(new HearthTreeNode((BoardModel)toRet.data_.deepCopy()));
				Minion battlecryTarget = PlayerSide.WAITING_PLAYER.getPlayer(newState).getMinions().get(index);
				battlecryTarget.setAttack((byte)(PlayerSide.WAITING_PLAYER.getPlayer(newState).getMinions().get(index).getAttack() + 2));
				newState = battlecryTarget.takeDamage((byte)1, PlayerSide.CURRENT_PLAYER, PlayerSide.WAITING_PLAYER, newState, deckPlayer0, deckPlayer1, false, true);
				newState = BoardStateFactoryBase.handleDeadMinions(newState, deckPlayer0, deckPlayer1);
			}
			return toRet;
		} else {
			return null;
		}
	}
}
