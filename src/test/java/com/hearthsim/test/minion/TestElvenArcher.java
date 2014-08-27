package com.hearthsim.test.minion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.Abomination;
import com.hearthsim.card.minion.concrete.ElvenArcher;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.util.boardstate.BoardState;
import com.hearthsim.util.tree.HearthTreeNode;

public class TestElvenArcher {
	private HearthTreeNode board;
	private Deck deck;
	private static final byte mana = 2;
	private static final byte attack0 = 5;
	private static final byte health0 = 3;
	private static final byte health1 = 7;

	@Before
	public void setup() throws HSException {
		board = new HearthTreeNode(new BoardState());

		Minion minion0_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
		Minion minion0_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);
		Minion minion1_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
		Minion minion1_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);
		Minion minion1_2 = new Abomination();
		
		minion1_2.setHealth((byte)1);
		
		board.data_.placeMinion(0, minion0_0);
		board.data_.placeMinion(0, minion0_1);
		
		board.data_.placeMinion(1, minion1_0);
		board.data_.placeMinion(1, minion1_1);
		board.data_.placeMinion(1, minion1_2);
		
		Card cards[] = new Card[10];
		for (int index = 0; index < 10; ++index) {
			cards[index] = new TheCoin();
		}
	
		deck = new Deck(cards);

		Minion fb = new ElvenArcher();
		board.data_.placeCard_hand_p0(fb);

		board.data_.setMana_p0((byte)7);
		board.data_.setMana_p1((byte)4);
		
		board.data_.setMaxMana_p0((byte)7);
		board.data_.setMaxMana_p1((byte)4);
		
	}
	
	@Test
	public void test0() throws HSException {
		
		Minion target = board.data_.getCharacter(1, 0);
		Card theCard = board.data_.getCard_hand_p0(0);
		HearthTreeNode ret = theCard.useOn(1, target, board, deck, null);
		
		assertTrue(ret == null);
		assertEquals(board.data_.getNumCards_hand(), 1);
		assertEquals(board.data_.getNumMinions_p0(), 2);
		assertEquals(board.data_.getNumMinions_p1(), 3);
		assertEquals(board.data_.getHero_p0().getHealth(), 30);
		assertEquals(board.data_.getHero_p1().getHealth(), 30);
		assertEquals(board.data_.getMinion_p0(0).getHealth(), health0);
		assertEquals(board.data_.getMinion_p0(1).getHealth(), health1 - 1);
		assertEquals(board.data_.getMinion_p1(0).getHealth(), health0);
		assertEquals(board.data_.getMinion_p1(1).getHealth(), health1 - 1);
	}
	
	@Test
	public void test2() throws HSException {
		
		Minion target = board.data_.getCharacter(0, 1);
		Card theCard = board.data_.getCard_hand_p0(0);
		HearthTreeNode ret = theCard.useOn(0, target, board, deck, null);
		
		assertFalse(ret == null);
		assertEquals(board.data_.getNumCards_hand(), 0);
		assertEquals(board.data_.getNumMinions_p0(), 3);
		assertEquals(board.data_.getNumMinions_p1(), 3);
		assertEquals(board.data_.getMana_p0(), 6);
		assertEquals(board.data_.getMana_p1(), 4);
		assertEquals(board.data_.getHero_p0().getHealth(), 30);
		assertEquals(board.data_.getHero_p1().getHealth(), 30);
		assertEquals(board.data_.getMinion_p0(0).getHealth(), health0);
		assertEquals(board.data_.getMinion_p0(1).getHealth(), 1);
		assertEquals(board.data_.getMinion_p0(2).getHealth(), health1 - 1);
		assertEquals(board.data_.getMinion_p1(0).getHealth(), health0);
		assertEquals(board.data_.getMinion_p1(1).getHealth(), health1 - 1);
		
		assertEquals(board.data_.getMinion_p0(0).getTotalAttack(), attack0);
		assertEquals(board.data_.getMinion_p0(1).getTotalAttack(), 1);
		assertEquals(board.data_.getMinion_p0(2).getTotalAttack(), attack0);
		
		assertEquals(board.numChildren(), 7);
		
		{
			HearthTreeNode child = board.getChildren().get(0);
			assertEquals(child.data_.getNumCards_hand(), 0);
			assertEquals(child.data_.getNumMinions_p0(), 3);
			assertEquals(child.data_.getNumMinions_p1(), 3);
			assertEquals(child.data_.getMana_p0(), 6);
			assertEquals(child.data_.getMana_p1(), 4);
			assertEquals(child.data_.getHero_p0().getHealth(), 29);
			assertEquals(child.data_.getHero_p1().getHealth(), 30);
			assertEquals(child.data_.getMinion_p0(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p0(1).getHealth(), 1);
			assertEquals(child.data_.getMinion_p0(2).getHealth(), health1 - 1);
			assertEquals(child.data_.getMinion_p1(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p1(1).getHealth(), health1 - 1);
			
			assertEquals(child.data_.getMinion_p0(0).getTotalAttack(), attack0);
			assertEquals(child.data_.getMinion_p0(1).getTotalAttack(), 1);
			assertEquals(child.data_.getMinion_p0(2).getTotalAttack(), attack0);
		}		
		
		{
			HearthTreeNode child = board.getChildren().get(1);
			assertEquals(child.data_.getNumCards_hand(), 0);
			assertEquals(child.data_.getNumMinions_p0(), 3);
			assertEquals(child.data_.getNumMinions_p1(), 3);
			assertEquals(child.data_.getMana_p0(), 6);
			assertEquals(child.data_.getMana_p1(), 4);
			assertEquals(child.data_.getHero_p0().getHealth(), 30);
			assertEquals(child.data_.getHero_p1().getHealth(), 30);
			assertEquals(child.data_.getMinion_p0(0).getHealth(), health0 - 1);
			assertEquals(child.data_.getMinion_p0(1).getHealth(), 1);
			assertEquals(child.data_.getMinion_p0(2).getHealth(), health1 - 1);
			assertEquals(child.data_.getMinion_p1(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p1(1).getHealth(), health1 - 1);
			
			assertEquals(child.data_.getMinion_p0(0).getTotalAttack(), attack0);
			assertEquals(child.data_.getMinion_p0(1).getTotalAttack(), 1);
			assertEquals(child.data_.getMinion_p0(2).getTotalAttack(), attack0);
		}		

		{
			HearthTreeNode child = board.getChildren().get(2);
			assertEquals(child.data_.getNumCards_hand(), 0);
			assertEquals(child.data_.getNumMinions_p0(), 3);
			assertEquals(child.data_.getNumMinions_p1(), 3);
			assertEquals(child.data_.getMana_p0(), 6);
			assertEquals(child.data_.getMana_p1(), 4);
			assertEquals(child.data_.getHero_p0().getHealth(), 30);
			assertEquals(child.data_.getHero_p1().getHealth(), 30);
			assertEquals(child.data_.getMinion_p0(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p0(1).getHealth(), 1);
			assertEquals(child.data_.getMinion_p0(2).getHealth(), health1 - 1 - 1);
			assertEquals(child.data_.getMinion_p1(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p1(1).getHealth(), health1 - 1);
			
			assertEquals(child.data_.getMinion_p0(0).getTotalAttack(), attack0);
			assertEquals(child.data_.getMinion_p0(1).getTotalAttack(), 1);
			assertEquals(child.data_.getMinion_p0(2).getTotalAttack(), attack0);
		}		

		{
			HearthTreeNode child = board.getChildren().get(3);
			assertEquals(child.data_.getNumCards_hand(), 0);
			assertEquals(child.data_.getNumMinions_p0(), 3);
			assertEquals(child.data_.getNumMinions_p1(), 3);
			assertEquals(child.data_.getMana_p0(), 6);
			assertEquals(child.data_.getMana_p1(), 4);
			assertEquals(child.data_.getHero_p0().getHealth(), 30);
			assertEquals(child.data_.getHero_p1().getHealth(), 29);
			assertEquals(child.data_.getMinion_p0(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p0(1).getHealth(), 1);
			assertEquals(child.data_.getMinion_p0(2).getHealth(), health1 - 1);
			assertEquals(child.data_.getMinion_p1(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p1(1).getHealth(), health1 - 1);
			
			assertEquals(child.data_.getMinion_p0(0).getTotalAttack(), attack0);
			assertEquals(child.data_.getMinion_p0(1).getTotalAttack(), 1);
			assertEquals(child.data_.getMinion_p0(2).getTotalAttack(), attack0);
		}
		
		{
			HearthTreeNode child = board.getChildren().get(4);
			assertEquals(child.data_.getNumCards_hand(), 0);
			assertEquals(child.data_.getNumMinions_p0(), 3);
			assertEquals(child.data_.getNumMinions_p1(), 3);
			assertEquals(child.data_.getMana_p0(), 6);
			assertEquals(child.data_.getMana_p1(), 4);
			assertEquals(child.data_.getHero_p0().getHealth(), 30);
			assertEquals(child.data_.getHero_p1().getHealth(), 30);
			assertEquals(child.data_.getMinion_p0(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p0(1).getHealth(), 1);
			assertEquals(child.data_.getMinion_p0(2).getHealth(), health1 - 1);
			assertEquals(child.data_.getMinion_p1(0).getHealth(), health0 - 1);
			assertEquals(child.data_.getMinion_p1(1).getHealth(), health1 - 1);
			
			assertEquals(child.data_.getMinion_p0(0).getTotalAttack(), attack0);
			assertEquals(child.data_.getMinion_p0(1).getTotalAttack(), 1);
			assertEquals(child.data_.getMinion_p0(2).getTotalAttack(), attack0);
		}		

		{
			HearthTreeNode child = board.getChildren().get(5);
			assertEquals(child.data_.getNumCards_hand(), 0);
			assertEquals(child.data_.getNumMinions_p0(), 3);
			assertEquals(child.data_.getNumMinions_p1(), 3);
			assertEquals(child.data_.getMana_p0(), 6);
			assertEquals(child.data_.getMana_p1(), 4);
			assertEquals(child.data_.getHero_p0().getHealth(), 30);
			assertEquals(child.data_.getHero_p1().getHealth(), 30);
			assertEquals(child.data_.getMinion_p0(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p0(1).getHealth(), 1);
			assertEquals(child.data_.getMinion_p0(2).getHealth(), health1 - 1);
			assertEquals(child.data_.getMinion_p1(0).getHealth(), health0);
			assertEquals(child.data_.getMinion_p1(1).getHealth(), health1 - 1 - 1);
			
			assertEquals(child.data_.getMinion_p0(0).getTotalAttack(), attack0);
			assertEquals(child.data_.getMinion_p0(1).getTotalAttack(), 1);
			assertEquals(child.data_.getMinion_p0(2).getTotalAttack(), attack0);
		}		

		{
			HearthTreeNode child = board.getChildren().get(6);
			assertEquals(child.data_.getNumCards_hand(), 0);
			assertEquals(child.data_.getNumMinions_p0(), 2);
			assertEquals(child.data_.getNumMinions_p1(), 2);
			assertEquals(child.data_.getMana_p0(), 6);
			assertEquals(child.data_.getMana_p1(), 4);
			assertEquals(child.data_.getHero_p0().getHealth(), 28);
			assertEquals(child.data_.getHero_p1().getHealth(), 28);
			assertEquals(child.data_.getMinion_p0(0).getTotalHealth(), health0 - 2);
			assertEquals(child.data_.getMinion_p0(1).getTotalHealth(), health1 - 1 - 2);
			assertEquals(child.data_.getMinion_p1(0).getTotalHealth(), health0 - 2);
			assertEquals(child.data_.getMinion_p1(1).getTotalHealth(), health1 - 1 - 2);
			
			assertEquals(child.data_.getMinion_p0(0).getTotalAttack(), attack0);
			assertEquals(child.data_.getMinion_p0(1).getTotalAttack(), attack0);
		}		

	}
}