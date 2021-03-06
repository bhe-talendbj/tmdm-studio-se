// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.repository.core.migrate;

import org.talend.mdm.repository.core.migrate.impl.ProcessMigrateObjectPathRule;

/**
 * DOC HHB class global comment. Detailled comment
 */
public class UpdateProcessObjectPathMT extends DefaultUpdateObjectPathMigrationTask {

    /**
     * DOC HHB UpdateViewObjectPathMT constructor comment.
     *
     * @param handler
     */
    public UpdateProcessObjectPathMT() {
        super(new MigrateObjectPathHandler(new ProcessMigrateObjectPathRule()));
    }

}
