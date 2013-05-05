package com.qeevee.gq.rules.act;

import android.content.Context;

import com.qeevee.gq.res.ResourceManager;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.Variables;

public class Score extends Action {

	static final String SCORE_VARIABLE = Variables.SYSTEM_PREFIX + "score";

	private Context ctx = GeoQuestApp.getContext();

	@Override
	protected boolean checkInitialization() {
		boolean initOK = true;
		initOK &= params.containsKey("value");
		return initOK;
	}

	@Override
	public void execute() {
		if (!Variables.isDefined(SCORE_VARIABLE)) {
			Variables.setValue(SCORE_VARIABLE, 0);
		}
		int deltaScore = Integer.parseInt(params.get("value"));
		int resultingScore = addToScore(deltaScore);
		if (resultingScore == 0) {
			GeoQuestApp.showMessage(ctx.getText(R.string.scoreZero));
		} else if (deltaScore > 0) {
			// GeoQuestApp.playAudio(ResourceManager.POSITIVE_SOUND, false);
			GeoQuestApp.showMessage(ctx.getText(R.string.scoreIncreasedBy)
					+ " " + resultingScore);
		} else if (deltaScore < 0) {
			// GeoQuestApp.playAudio(ResourceManager.NEGATIVE_SOUND, false);
			GeoQuestApp.showMessage(ctx.getText(R.string.scoreDecreasedBy)
					+ " " + resultingScore);
		}
	}

	private int addToScore(int score) {
		int resultScore = (Integer) Variables.getValue(SCORE_VARIABLE) + score;
		if (resultScore < 0)
			resultScore = 0;
		Variables.setValue(SCORE_VARIABLE, resultScore);
		return resultScore;
	}
}
