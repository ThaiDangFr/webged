package dang.ged;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import dang.cms.CmsLanguage;

import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBGedState;
import openplatform.database.dbean.DBGedTemplate;
import openplatform.database.dbean.DBGedTemplateItem;
import openplatform.tools.Debug;
import openplatform.tools.OrdHashMap;


public class AdminGedState extends DBGedState
{
	private CmsLanguage cmsLang = new CmsLanguage();
	private int type; // 0=general 1=template
	
	public String doItemUp()
	throws Exception
	{
		if(id == null) return null;
		
		DBGedState mask = new DBGedState();
		
		mask.setNext(id);
		DBGedState parent = DBGedState.loadByKey(mask);
		
		mask.clear();
		mask.setId(id);
		DBGedState sel = DBGedState.loadByKey(mask);
		
		DBGedState parentofparent = null;
		if(parent != null)
		{
			mask.clear();
			mask.setNext(parent.getId());
			parentofparent = DBGedState.loadByKey(mask);
		}
		


		// MOVING
		
		if(parent != null)
		{
			parent.setNext(sel.getNext());
			parent.store();
		}
		
		if(sel != null && parent != null)
		{
			sel.setNext(parent.getId());
			sel.store();
		}
		
		if(parentofparent != null && sel != null)
		{
			parentofparent.setNext(sel.getId());
			parentofparent.store();
		}

		
		return null;
	}
	
	public String doItemDown()
	throws Exception
	{
		if(id == null) return null;
			
		DBGedState mask = new DBGedState();
		
		mask.setNext(id);
		DBGedState parent = DBGedState.loadByKey(mask);
		
		mask.clear();
		mask.setId(id);
		DBGedState sel = DBGedState.loadByKey(mask);
		
		DBGedState son = null;
		if(sel != null)
		{
			mask.clear();
			mask.setId(sel.getNext());
			son = DBGedState.loadByKey(mask);
		}
		
		// MOVING
		
		if(sel != null && son != null)
		{
			sel.setNext(son.getNext());
			sel.store();
		}
		
		if(son != null && sel != null)
		{
			son.setNext(sel.getId());
			son.store();
		}
		
		if(parent != null && son != null)
		{
			parent.setNext(son.getId());
			parent.store();
		}

		
		
		return null;
	}
	
	public String doDeleteItem()
	throws Exception
	{
		if(id == null) return null;
		
			DBGedState mask = new DBGedState();
			mask.setNext(id);
			DBGedState parent = DBGedState.loadByKey(mask);
			
			mask.clear();
			mask.setId(id);
			DBGedState todel = DBGedState.loadByKey(mask);
			
			String nxt = null;
			if(todel.getNext() != null)
			{
				mask.clear();
				mask.setId(todel.getNext());
				DBGedState son = DBGedState.loadByKey(mask);
				nxt = son.getId();
			}
			
			if(todel != null)
			{
				todel.delete();
				if(parent != null)
				{
					parent.setNext(nxt);
					parent.store();
				}
			}
			
		
		
		return null;
	}
		
	
	/**
	 * Add an item
	 * @return
	 * @throws Exception
	 */
	public String doAddItem()
	throws Exception
	{
		SQLConstraint sqlc = new SQLConstraint();
		sqlc.setCustomWhere(DBGedState.NEXT+SQLConstraint.IS_NULL);
		DBGedState lastgs = DBGedState.loadByKey(null, sqlc);
		
		
		id = null;
		if(type == 0) gedTemplateItem = null;
		else if(type == 1) generalColumn = null;
		
		Debug.println(this, Debug.DEBUG, "*** "+toString()+ " type="+type);
		
		store();
		
		if(lastgs != null)
		{
			lastgs.setNext(id);
			lastgs.store();
		}
		
		
		
		return null;
	}


	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public HashMap getGeneralOptions()
	{
		//return DBGedState._GENERALCOLUMN_MAP;
		
		HashMap map = new HashMap();
		Iterator it = DBGedState._GENERALCOLUMN_MAP.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String)it.next();
			String val = cmsLang.translate((String)DBGedState._GENERALCOLUMN_MAP.get(key));
			map.put(key, val);
		}
		return map;
	}
	
	
	public HashMap getTemplateItemOptions()
	{
		HashMap map = new OrdHashMap();
		DBGedTemplateItem[] gti = DBGedTemplateItem.load(null);
		if(gti == null) return map;
		
		DBGedTemplate tmask = new DBGedTemplate();
		
		int len = gti.length;
		for(int i=0; i<len; i++)
		{
			String tid = gti[i].getTemplateId();
			
			if(tid == null) continue;
			
			tmask.clear();
			tmask.setId(tid);
			DBGedTemplate t = DBGedTemplate.loadByKey(tmask);
			
			if(t != null)
			{
				String name = t.getName() + " / " + gti[i].getFieldname();
				map.put(gti[i].getId(), name);
			}
		}
		
		return map;
	}
	
	
	/**
	 * GedState arraylist
	 * Be carefull : this method is expensive in CPU
	 * @return
	 */
	public ArrayList getGedStateList()
	{
		ArrayList array = new ArrayList();
		
		SQLConstraint sqlc = new SQLConstraint();
		sqlc.setCustomWhere(DBGedState.NEXT+SQLConstraint.IS_NULL);
		
		DBGedState last = DBGedState.loadByKey(null, sqlc);
		
		if(last == null) return array;
		
		array.add(new GedState(last));
		
		String id = last.getId();
		DBGedState gsmask = new DBGedState();
		while(id != null)
		{
			//Debug.println(this, Debug.DEBUG, id);
			
			gsmask.clear();
			gsmask.setNext(id);
			DBGedState previous = DBGedState.loadByKey(gsmask);
			
			if(previous == null) id = null;
			else
			{
				id = previous.getId();
				array.add(0,new GedState(previous));
			}
		}

		return array;
	}
}
