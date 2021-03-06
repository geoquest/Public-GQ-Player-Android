package com.qeevee.qg.tests.functional;

import junit.framework.AssertionFailedError;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.qeevee.gq.util.test.NetTest;
import com.qeevee.gq.util.test.Util;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestGameListActivity extends ActivityInstrumentationTestCase2 {
    private static final String TARGET_PACKAGE_ID = "edu.bonn.mobilegaming.geoquest";
    private static final String START_ACTIVITY = "edu.bonn.mobilegaming.geoquest.Start";
    private static final String GAME_LIST_ACTIVITY = "edu.bonn.mobilegaming.geoquest.GameListActvity";

    private static Class<?> launcherActivityClass;
    static {
	try {
	    launcherActivityClass = Class.forName(START_ACTIVITY);
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e);
	}
    }

    public TestGameListActivity() throws ClassNotFoundException {
	super(TARGET_PACKAGE_ID, launcherActivityClass);
    }

    private Solo solo;

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	if (!NetTest.weAreOnline()) {
	    getActivity().finish();
	    throw new AssertionFailedError(
		    "Not online. Stopping this test since it depends on network connection.");
	}
	solo = new Solo(getInstrumentation(), getActivity());

	// click on "Select game" button which should bring up RepoList (is
	// tested in Start)
	solo.waitForText(Util
		.getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
	solo.clickOnText(Util
		.getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
	
	// click on "Demos" to bring up GameListActivity on Demos repo:
	solo.waitForText("Demos");
	solo.clickOnText("Demos");
    }

    /**
     * Just checking that the menu entries are shown.
     */
    public void testMenuEntriesByText() {
	solo.waitForActivity(GAME_LIST_ACTIVITY);
	solo.sendKey(Solo.MENU);

	// menu shown now:
	assertFalse("GameList Activity should NOT show Reload Repositories Menu Item",
		   solo.searchText(Util
			   .getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.reloadGamesMenu)));
	assertTrue("GameList Activity should show Quit Menu Item",
		   solo.searchText(Util
			   .getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.quitMenu)));
	assertTrue("GameList Activity should show Preferences Menu Item",
		   solo.searchText(Util
			   .getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.prefsMenu)));
    }

    @Override
    public void tearDown() throws Exception {
	try {
	    solo.finalize();
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	getActivity().finish();
	super.tearDown();
    }
}
