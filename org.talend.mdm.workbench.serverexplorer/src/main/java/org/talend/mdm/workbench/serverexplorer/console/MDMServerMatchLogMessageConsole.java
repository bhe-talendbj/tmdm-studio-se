// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.workbench.serverexplorer.console;

import org.talend.mdm.repository.model.mdmmetadata.MDMServerDef;
import org.talend.mdm.workbench.serverexplorer.i18n.Messages;
import org.talend.mdm.workbench.serverexplorer.plugin.MDMServerExplorerPlugin;

/**
 * created by liusongbo on 2013-10-17
 */
public class MDMServerMatchLogMessageConsole extends MDMServerMessageConsole {

    public MDMServerMatchLogMessageConsole(MDMServerDef serverDef) {
        super(serverDef);
    }

    @Override
    protected String getLogPath() {
        return "/datamanager/logviewer/match"; //$NON-NLS-1$
    }

    @Override
    protected String getConsoleTitle() {
        MDMServerDef serverDef = getServerDef();
        if(serverDef != null && serverDef.getName() != null) {
            return Messages.bind(Messages.MDMServerMessageConsole_MatchName, serverDef.getName());
        }
        
        return ""; //$NON-NLS-1$
    }

    @Override
    protected void removeFromCache(String serverName) {
        if(serverName != null) {
            MDMServerExplorerPlugin.getDefault().getServerMatchToConsole().remove(serverName);
        }
    }

}
