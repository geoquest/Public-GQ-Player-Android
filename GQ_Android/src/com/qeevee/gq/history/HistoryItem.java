package com.qeevee.gq.history;

import java.util.HashMap;
import java.util.Map;

import android.view.View;
import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;

/**
 * @author muegge
 * 
 */
public abstract class HistoryItem {

    private long id;

    protected Map<Class<? extends HistoryItemModifier>, HistoryItemModifier> modifiers;

    protected Class<? extends GeoQuestActivity> activityType;

    /**
     * Main constructor which should directly or indirectly be called by every
     * other constructor of this class.
     * 
     * Each HistoryItem adds itself to the history list.
     */
    public HistoryItem(GeoQuestActivity activity,
		       HistoryItemModifier modifier) {
	activityType = activity.getClass();
	modifiers = new HashMap<Class<? extends HistoryItemModifier>, HistoryItemModifier>();
	modifiers.put(modifier.getClass(),
		      modifier);
	History.getInstance().add(this);
    }

    public HistoryItem(GeoQuestActivity activity) {
	this(activity, Actor.DEFAULT);
    }

    public long getId() {
	return id;
    }

    void setId(long id) {
	this.id = id;
    }

    Class<? extends GeoQuestActivity> getActivityType() {
	return activityType;
    }

    public abstract View getView(View convertView);

    /**
     * Each history item can hold modifiers that describe some semantic aspects.
     * For example a TextItem might specify what kind of text it represents with
     * one of the possible modifiers specified in {@link TextType}.
     * 
     * To get an idea of what modifiers exist look at
     * {@link HistoryItemModifier} and its subtypes.
     * 
     * @param modifierClass
     *            e.g. Actor or TextType etc.
     * @return the value for the given sort of modifiers that holds for this
     *         history item.
     */
    public HistoryItemModifier
	    getModifier(Class<? extends HistoryItemModifier> modifierClass) {
	return modifiers.get(modifierClass);
    }

}
