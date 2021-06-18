/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.web.extension;

import org.openmrs.module.Extension;

import java.util.Map;

public abstract class AdministrationSectionExt extends Extension {
    public AdministrationSectionExt() {
    }

    public MEDIA_TYPE getMediaType() {
        return MEDIA_TYPE.html;
    }

    public abstract String getTitle();

    public String getRequiredPrivilege() {
        return "";
    }

    public abstract Map<String, String> getLinks();
}
