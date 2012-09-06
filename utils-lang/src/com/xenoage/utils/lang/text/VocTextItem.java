package com.xenoage.utils.lang.text;

import static com.xenoage.utils.lang.VocByString.voc;

import com.xenoage.utils.lang.Lang;
import com.xenoage.utils.lang.VocID;


/**
 * One piece of text identified by a vocabulary.
 * 
 * @author Andreas Wenger
 */
public class VocTextItem
	implements TextItem
{

	private final VocID vocID;
	
	
	public VocTextItem(VocID vocID)
	{
		this.vocID = vocID;
	}
	
	
	public VocTextItem(String projectID, String vocID)
	{
		this.vocID = voc(projectID, vocID);
	}

  
  @Override public String getText()
  {
  	return Lang.get(vocID);
  }
	
	
}
