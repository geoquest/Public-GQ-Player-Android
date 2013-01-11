package com.qeevee.gq.tests;

import static com.qeevee.gq.tests.TestUtils.getFieldValue;
import android.os.CountDownTimer;
import edu.bonn.mobilegaming.geoquest.mission.DialogItem;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;

public class TestNPCTalkUtils {

    /**
     * This helper method emulates the Timer used in the real application. It
     * very specialized according to the implementation, e.g. the delta between
     * word appearance is set to 100ms as in the code.
     */
    public static void
            letCurrentDialogItemAppearCompletely(NPCTalk npcTalk,
        					 CountDownTimer timer) {
        timer = (CountDownTimer) getFieldValue(npcTalk,
        				       "myCountDownTimer");
        timer.onFinish();
        DialogItem dialogItem = (DialogItem) getFieldValue(npcTalk,
        						   "currItem");
        timer = (CountDownTimer) getFieldValue(npcTalk,
        				       "myCountDownTimer");
        for (int i = 0; i < dialogItem.getNumParts(); i++) {
            timer.onTick(100l * (dialogItem.getNumParts() - (i + 1)));
        }
        timer.onFinish();
    }

}