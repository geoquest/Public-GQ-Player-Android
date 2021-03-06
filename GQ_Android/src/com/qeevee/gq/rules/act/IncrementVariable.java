package com.qeevee.gq.rules.act;

import android.util.Log;
import edu.bonn.mobilegaming.geoquest.Variables;

public class IncrementVariable extends Action {

	@Override
	protected boolean checkInitialization() {
		boolean initOK = true;
		initOK &= params.containsKey("var");
		initOK &= (Variables.getValue(params.get("var")) instanceof Double);
		// TODO:
		// Problematic
		// since
		// this
		// is
		// a
		// static
		// check
		// at
		// loadtime
		// of
		// mission
		// xml
		// element.
		return initOK;
	}

	@Override
	public void execute() {
		String varName = params.get("var");
		Object value = Variables.getValue(varName);
		if (value instanceof Double)
			Variables.setValue(varName, (Double) value + 1.0d);
		else
			Log.w(this.getClass().getName(), "Tried to increment non number variable. Ignored.");
	}

}
