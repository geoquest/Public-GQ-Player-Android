package com.qeevee.gq.tests.mission;

import static com.qeevee.gq.tests.util.TestNPCTalkUtils.letCurrentDialogItemAppearCompletely;
import static com.qeevee.gq.tests.util.TestUtils.getFieldValue;
import static com.qeevee.gq.tests.util.TestUtils.prepareMission;
import static com.qeevee.gq.tests.util.TestUtils.startGameForTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.qeevee.gq.history.History;
import com.qeevee.gq.history.TextItem;
import com.qeevee.gq.history.TransitionItem;
import com.qeevee.gq.tests.robolectric.GQTestRunner;
import com.qeevee.ui.ZoomImageView;

import edu.bonn.mobilegaming.geoquest.Start;
import edu.bonn.mobilegaming.geoquest.Variables;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;
import edu.bonn.mobilegaming.geoquest.ui.NPCTalkUIDefault;

@RunWith(GQTestRunner.class)
public class NPCTalkMissionTests {
    NPCTalk npcTalkM;
    NPCTalkUIDefault ui;
    ZoomImageView imageView;
    TextView talkView;
    Button proceedBT;
    CountDownTimer timer;
    private Start start;
    private NPCTalk npcTalk;

    public void initTestMission(String missionID) {
	npcTalkM = (NPCTalk) prepareMission("NPCTalk",
					    missionID,
					    startGameForTest("npctalk/NPCTalkTest"));
	npcTalkM.onCreate(null);
	ui = (NPCTalkUIDefault) getFieldValue(npcTalkM,
					      "ui");
	imageView = (ZoomImageView) getFieldValue(ui,
						  "charImage");
	talkView = (TextView) getFieldValue(ui,
					    "dialogText");
	proceedBT = (Button) getFieldValue(ui,
					   "button");
	timer = (CountDownTimer) getFieldValue(npcTalkM,
					       "myCountDownTimer");
    }

    @After
    public void cleanUp() {
	// get rid of all variables that have been set, e.g. for checking
	// actions.
	Variables.clean();
	History.getInstance().clear();
    }

    // === TESTS FOLLOW =============================================

    @Test
    public void goThrough() {
	History h = History.getInstance();
	assertEquals("History should be empty before mission is loaded",
		     0,
		     h.numberOfItems());
	initTestMission("Only_Text");
	assertEquals("History should be empty directly after mission is loaded",
		     0,
		     h.numberOfItems());
	letCurrentDialogItemAppearCompletely(npcTalkM);
	assertEquals("Talk view should contain last dialog item text",
		     "This NPCTalk mission offers just three only text dialog items.\n",
		     talkView.getText().toString());
	assertEquals("History should contain the first item after it has been shown.",
		     1,
		     h.numberOfItems());
	assertEquals("First element in history should be a text item.",
		     h.getItem(h.numberOfItems() - 1).getClass(),
		     TextItem.class);
	proceedBT.performClick();
	letCurrentDialogItemAppearCompletely(npcTalkM);
	assertTrue("Talk view should end with second dialog item text",
		   talkView.getText().toString()
			   .endsWith("This is the second dialog item.\n"));
	assertEquals("History should contain both items now.",
		     2,
		     h.numberOfItems());
	assertEquals("First element in history should be a text item.",
		     h.getItem(h.numberOfItems() - 1).getClass(),
		     TextItem.class);
	proceedBT.performClick();
	letCurrentDialogItemAppearCompletely(npcTalkM);
	assertTrue("Talk view should end with third and last dialog item text",
		   talkView.getText()
			   .toString()
			   .endsWith("This is the third and last dialog item.\n"));
	assertEquals("History should contain three items now.",
		     3,
		     h.numberOfItems());
	assertEquals("First element in history should be a text item.",
		     h.getItem(h.numberOfItems() - 1).getClass(),
		     TextItem.class);
	proceedBT.performClick();
	assertEquals("History should contain four items now.",
		     4,
		     h.numberOfItems());
	assertEquals("Last element in history should be a transitional item.",
		     h.getItem(h.numberOfItems() - 1).getClass(),
		     TransitionItem.class);
    }

    @Test
    public void testBeforeStartEvent() {
	// GIVEN:
	start = startGameForTest("npctalk/NPCTalkTest");

	// WHEN:
	npcTalk = (NPCTalk) prepareMission("NPCTalk",
					   "With_Defaults",
					   start);

	// THEN:
	shouldHave_NOT_TriggeredOnStartEvent();
    }

    @Test
    public void testStartEvent() {
	// GIVEN:
	start = startGameForTest("npctalk/NPCTalkTest");
	npcTalk = (NPCTalk) prepareMission("NPCTalk",
					   "With_Defaults",
					   start);

	// WHEN:
	npcTalk.onCreate(null);

	// THEN:
	shouldHaveTriggeredOnStartEvent();
    }

    @Test
    public void historyTransitionToNPCTalk() {
	// GIVEN:
	startGameForTest("HistoryTests/TransitionStartScreen2NPC");

	// WHEN:

	// THEN:
	fail();
    }

    @Test
    public void historyInit() {
	// GIVEN:
	start = startGameForTest("npctalk/NPCTalkTest");

	// WHEN:

	// THEN:
	fail();
    }

    // === HELPER METHODS FOLLOW =============================================

    private void shouldHaveTriggeredOnStartEvent() {
	assertEquals(1.0,
		     Variables.getValue("onStart"));
    }

    private void shouldHave_NOT_TriggeredOnStartEvent() {
	assertFalse(Variables.isDefined("onStart"));
    }

}
