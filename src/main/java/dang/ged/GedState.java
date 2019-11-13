package dang.ged;

import openplatform.database.dbean.DBGedState;
import openplatform.database.dbean.DBGedTemplate;
import openplatform.database.dbean.DBGedTemplateItem;

public class GedState extends DBGedState {

	private String gsId;
	private String gsName;
	private String gsValue;
	private int gsType; // 0=general column, 1=templateitem
	
	public GedState(DBGedState gs)
	{
		initBean(gs);
		
		gsId = id;
		
		if(getGeneralColumn() != null)
		{
			gsName = getGeneralColumn();
			gsType = 0;
		}
		else if(getGedTemplateItem() != null)
		{
			DBGedTemplateItem gtimask = new DBGedTemplateItem();
			gtimask.setId(gs.getGedTemplateItem());
			DBGedTemplateItem gti = DBGedTemplateItem.loadByKey(gtimask);
			
			if(gti != null)
			{
				DBGedTemplate tmask = new DBGedTemplate();
				tmask.setId(gti.getTemplateId());
				DBGedTemplate t = DBGedTemplate.loadByKey(tmask);
				if(t != null)
				{
					gsName = t.getName()+" / "+gti.getFieldname();
					gsType = 1;
				}
			}
		}
	}

	public int getGsType() {
		return gsType;
	}

	public void setGsType(int gsType) {
		this.gsType = gsType;
	}

	public String getGsId() {
		return gsId;
	}

	public void setGsId(String gsId) {
		this.gsId = gsId;
	}

	public String getGsName() {
		return gsName;
	}

	public void setGsName(String gsName) {
		this.gsName = gsName;
	}

	public String getGsValue() {
		return gsValue;
	}

	public void setGsValue(String gsValue) {
		this.gsValue = gsValue;
	}
	
	
}
