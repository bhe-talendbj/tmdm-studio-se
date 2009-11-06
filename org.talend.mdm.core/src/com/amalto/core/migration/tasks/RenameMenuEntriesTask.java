package com.amalto.core.migration.tasks;

import javax.naming.InitialContext;

import com.amalto.core.ejb.ObjectPOJO;
import com.amalto.core.migration.AbstractMigrationTask;
import com.amalto.core.objects.configurationinfo.localutil.ConfigurationHelper;
import com.amalto.core.objects.configurationinfo.localutil.CoreUpgrades;
import com.amalto.core.objects.menu.ejb.MenuPOJO;
import com.amalto.core.objects.menu.ejb.local.MenuCtrlLocal;
import com.amalto.core.objects.menu.ejb.local.MenuCtrlLocalHome;

public class RenameMenuEntriesTask extends AbstractMigrationTask{

	@Override
	protected Boolean execute() {
		
		//Update Menu POJOS
		org.apache.log4j.Logger.getLogger(RenameMenuEntriesTask.class).info("Updating Menus");
		try {
			
			String[] ids = ConfigurationHelper.getServer().getAllDocumentsUniqueID(null, "amaltoOBJECTSMenu");
			if (ids != null) {
				
				MenuCtrlLocal menuCtrl = ((MenuCtrlLocalHome)new InitialContext().lookup(MenuCtrlLocalHome.JNDI_NAME)).create();
				
				for (int i = 0; i < ids.length; i++) {
					String xml = ConfigurationHelper.getServer().getDocumentAsString(null, "amaltoOBJECTSMenu", ids[i]);
					xml = xml.replaceAll("java:com.amalto.core.ejb.MenuEntryPOJO", "java:com.amalto.core.objects.menu.ejb.MenuEntryPOJO");
					MenuPOJO menu = ObjectPOJO.unmarshal(MenuPOJO.class, xml);
					menuCtrl.putMenu(menu);
					org.apache.log4j.Logger.getLogger(CoreUpgrades.class).info("Processed '"+menu.getName()+"'");
				}
			}
			
			
		} catch (Exception e) {
			String err = "Unable to Rename Menu Entries.";
			org.apache.log4j.Logger.getLogger(RenameMenuEntriesTask.class).error(err, e);
			return false;
		}
		org.apache.log4j.Logger.getLogger(RenameMenuEntriesTask.class).info("Done Updating Menus");
		
		return true;
	}

}