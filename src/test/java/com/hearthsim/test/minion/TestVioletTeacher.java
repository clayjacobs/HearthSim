package com.hearthsim.test.minion;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.BoulderfistOgre;
import com.hearthsim.card.minion.concrete.RaidLeader;
import com.hearthsim.card.minion.concrete.VioletTeacher;
import com.hearthsim.card.spellcard.concrete.HolySmite;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestVioletTeacher {

	private HearthTreeNode board;
	private Deck deck;

	@Before
	public void setup() {
		board = new HearthTreeNode(new BoardModel());

		Minion minion0_0 = new BoulderfistOgre();
		Minion minion0_1 = new RaidLeader();
		Minion minion1_0 = new BoulderfistOgre();
		Minion minion1_1 = new RaidLeader();
		
		board.data_.placeCardHandCurrentPlayer(minion0_0);
		board.data_.placeCardHandCurrentPlayer(minion0_1);
				
		board.data_.placeCardHandWaitingPlayer(minion1_0);
		board.data_.placeCardHandWaitingPlayer(minion1_1);

		Card cards[] = new Card[10];
		for (int index = 0; index < 10; ++index) {
			cards[index] = new TheCoin();
		}
	
		deck = new Deck(cards);

		Card fb = new VioletTeacher();
		board.data_.placeCardHandCurrentPlayer(fb);

		board.data_.getCurrentPlayer().setMana((byte)8);
		board.data_.getWaitingPlayer().setMana((byte)8);
		
		board.data_.getCurrentPlayer().setMaxMana((byte)8);
		board.data_.getWaitingPlayer().setMaxMana((byte)8);
		
		HearthTreeNode tmpBoard = new HearthTreeNode(board.data_.flipPlayers());
		try {
			tmpBoard.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayerHero(), tmpBoard, deck, null);
			tmpBoard.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayerHero(), tmpBoard, deck, null);
		} catch (HSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board = new HearthTreeNode(tmpBoard.data_.flipPlayers());
		try {
			board.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, board.data_.getCurrentPlayerHero(), board, deck, null);
			board.data_.getCurrentPlayerCardHand(0).useOn(PlayerSide.CURRENT_PLAYER, board.data_.getCurrentPlayerHero(), board, deck, null);
		} catch (HSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.data_.resetMana();
		board.data_.resetMinions();
		
	}
	
	

	@Test
	public void test0() throws HSException {
		
		//null case
		Minion target = board.data_.getCharacter(PlayerSide.WAITING_PLAYER, 0);
		Card theCard = board.data_.getCurrentPlayerCardHand(0);
		HearthTreeNode ret = theCard.useOn(PlayerSide.WAITING_PLAYER, target, board, deck, null);
		
		assertTrue(ret == null);
		assertEquals(board.data_.getNumCards_hand(), 1);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getNumMinions(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getNumMinions(), 2);
		assertEquals(board.data_.getCurrentPlayer().getMana(), 8);
		assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
		assertEquals(board.data_.getCurrentPlayerHero().getHealth(), 30);
		assertEquals(board.data_.getWaitingPlayerHero().getHealth(), 30);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 7);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 7);

		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 7);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 7);
	}
	
	@Test
	public void test1() throws HSException {
		
		//null case
		Minion target = board.data_.getCharacter(PlayerSide.CURRENT_PLAYER, 2);
		Card theCard = board.data_.getCurrentPlayerCardHand(0);
		HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, target, board, deck, null);
		
		assertFalse(ret == null);
		assertEquals(board.data_.getNumCards_hand(), 0);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getNumMinions(), 3);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getNumMinions(), 2);
		assertEquals(board.data_.getCurrentPlayer().getMana(), 4);
		assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
		assertEquals(board.data_.getCurrentPlayerHero().getHealth(), 30);
		assertEquals(board.data_.getWaitingPlayerHero().getHealth(), 30);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 7);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(2).getHealth(), 5);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 7);

		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 7);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(2).getTotalAttack(), 4);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 7);
		
		
		
		//----------------------------------------------------------
		// Use a spell now... p0 should get a Violet Apprentice
		Card cardToUse = new HolySmite();
		board.data_.placeCardHandCurrentPlayer(cardToUse);
		target = board.data_.getCharacter(PlayerSide.WAITING_PLAYER, 1);
		ret = cardToUse.useOn(PlayerSide.WAITING_PLAYER, target, ret, deck, null);

		assertEquals(board.data_.getNumCards_hand(), 0);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getNumMinions(), 4);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getNumMinions(), 1);
		assertEquals(board.data_.getCurrentPlayer().getMana(), 3);
		assertEquals(board.data_.getWaitingPlayer().getMana(), 8);
		assertEquals(board.data_.getCurrentPlayerHero().getHealth(), 30);
		assertEquals(board.data_.getWaitingPlayerHero().getHealth(), 30);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getHealth(), 7);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(2).getHealth(), 5);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(3).getHealth(), 1);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getHealth(), 7);

		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 2);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(1).getTotalAttack(), 7);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(2).getTotalAttack(), 4);
		assertEquals(PlayerSide.CURRENT_PLAYER.getPlayer(board).getMinions().get(3).getTotalAttack(), 2);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(board).getMinions().get(0).getTotalAttack(), 6);
		
		//----------------------------------------------------------
		// flipped, p1 should not get a Violet Apprentice
		HearthTreeNode flp = new HearthTreeNode(board.data_.flipPlayers());
		Card cardToUse2 = new HolySmite();
		flp.data_.placeCardHandCurrentPlayer(cardToUse2);

		assertEquals(flp.data_.getNumCards_hand(), 1);

		target = flp.data_.getCharacter(PlayerSide.WAITING_PLAYER, 1);
		ret = cardToUse2.useOn(PlayerSide.WAITING_PLAYER, target, flp, deck, null);

		assertEquals(ret.data_.getNumCards_hand(), 0);
		assertEquals(ret.data_.getCurrentPlayer().getNumMinions(), 1);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getNumMinions(), 3);
		assertEquals(ret.data_.getCurrentPlayer().getMana(), 7);
		assertEquals(ret.data_.getWaitingPlayer().getMana(), 3);
		assertEquals(ret.data_.getCurrentPlayerHero().getHealth(), 30);
		assertEquals(ret.data_.getWaitingPlayerHero().getHealth(), 30);
		assertEquals(ret.data_.getCurrentPlayer().getMinions().get(0).getHealth(), 7);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(0).getHealth(), 7);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(1).getHealth(), 5);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(2).getHealth(), 1);

		assertEquals(ret.data_.getCurrentPlayer().getMinions().get(0).getTotalAttack(), 6);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(0).getTotalAttack(), 6);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(1).getTotalAttack(), 3);
		assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(2).getTotalAttack(), 1);
		
	}

}
