/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.StringProperty;
import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.AdministrationSectionExt;
import org.openmrs.module.webservices.helper.ModuleFactoryWrapper;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingReadableResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ObjectNotFoundException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.wrapper.openmrs1_8.ModuleAdministrationLinks1_8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Resource(name = RestConstants.VERSION_1 + "/administrationlinks", supportedClass = ModuleAdministrationLinks1_8.class,
		supportedOpenmrsVersions = { "1.8.*", "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.0.*", "2.1.*", "2.2.*", "2.3.*",
				"2.4.*", "2.5.*" })
public class AdministrationLinksResource1_8 extends BaseDelegatingReadableResource<ModuleAdministrationLinks1_8> {

	private static final String ADMIN_LIST_POINT_ID = "org.openmrs.admin.list";

	private static final String MODULE_TITLE = "moduleTitle";

	private static final String LINKS = "administrationLinks";

	private ModuleFactoryWrapper moduleFactoryWrapper = new ModuleFactoryWrapper();

	public void setModuleFactoryWrapper(ModuleFactoryWrapper moduleFactoryWrapper) {
		this.moduleFactoryWrapper = moduleFactoryWrapper;
	}

	@Override
	public ModuleAdministrationLinks1_8 newDelegate() {
		return null;
	}

	@Override
	public ModuleAdministrationLinks1_8 getByUniqueId(String moduleId) {
		// Assumes that moduleId is an id of a module that user wants to get admin links for.
		// AdministrationLinksResource has no id by itself as it's not an OpenMRS data object.

		ModuleAdministrationLinks1_8 administrationLinks = getAdministrationLinksForModule(moduleId);
		if (administrationLinks == null) {
			throw new ObjectNotFoundException();
		}

		return administrationLinks;
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display");
			description.addProperty(MODULE_TITLE);
			description.addProperty(LINKS);
			description.addSelfLink();
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display");
			description.addProperty(MODULE_TITLE);
			description.addProperty(LINKS);
			description.addSelfLink();
			return description;
		} else if (rep instanceof RefRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display");
			description.addProperty(MODULE_TITLE);
			description.addProperty(LINKS);
			description.addSelfLink();
			return description;
		}
		return null;
	}

	@PropertyGetter("uuid")
	public static String getUuid(ModuleAdministrationLinks1_8 instance) {
		return instance.getModuleId();
	}

	@PropertyGetter("display")
	public static String getDisplay(ModuleAdministrationLinks1_8 instance) {
		return instance.getModuleTitle();
	}

	@Override
	public Model getGETModel(Representation rep) {
		ModelImpl model = (ModelImpl) super.getGETModel(rep);
		if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
			model
					.property(MODULE_TITLE, new StringProperty())
					.property(LINKS, new ArrayProperty(new ObjectProperty()));
		}

		return model;
	}

	@Override
	public NeedsPaging<ModuleAdministrationLinks1_8> doGetAll(RequestContext context) throws ResponseException {
		return new NeedsPaging<ModuleAdministrationLinks1_8>(getAllAdministrationLinks(), context);
	}

	private ModuleAdministrationLinks1_8 getAdministrationLinksForModule(String moduleId) {
		List<Extension> adminListsExtensions = moduleFactoryWrapper.getExtensions(ADMIN_LIST_POINT_ID);
		for (Extension adminListExtension : adminListsExtensions) {
			if (adminListExtension instanceof AdministrationSectionExt && adminListExtension.getModuleId()
					.equals(moduleId)) {
				return getModuleWithAdminLinks(adminListExtension);
			}
		}

		return null;
	}

	private List<ModuleAdministrationLinks1_8> getAllAdministrationLinks() {
		List<ModuleAdministrationLinks1_8> modulesWithLinksList = new ArrayList<ModuleAdministrationLinks1_8>();

		List<Extension> adminListsExtensions = moduleFactoryWrapper.getExtensions(ADMIN_LIST_POINT_ID);

		for (Extension adminListExtension : adminListsExtensions) {
			if (adminListExtension instanceof AdministrationSectionExt) {
				modulesWithLinksList.add(getModuleWithAdminLinks(adminListExtension));
			}
		}

		return modulesWithLinksList;
	}

	private ModuleAdministrationLinks1_8 getModuleWithAdminLinks(Extension adminListExtension) {
		AdministrationSectionExt adminSectionExtension = (AdministrationSectionExt) adminListExtension;
		List<ModuleAdministrationLinks1_8.AdministrationLink> links = getAdminLinksForModule(adminSectionExtension);
		return new ModuleAdministrationLinks1_8(adminSectionExtension.getModuleId(), adminSectionExtension.getTitle(),
				links);
	}

	private List<ModuleAdministrationLinks1_8.AdministrationLink> getAdminLinksForModule(
			AdministrationSectionExt adminSectionExtension) {
		// AdministrationSectionExt.getLinks should already verify if user has privileges to receive links
		Map<String, String> extensionLinksMap = adminSectionExtension.getLinks();

		List<ModuleAdministrationLinks1_8.AdministrationLink> linksForModule = new ArrayList<ModuleAdministrationLinks1_8.AdministrationLink>();

		for (Map.Entry<String, String> extensionLink : extensionLinksMap.entrySet()) {
			String link = extensionLink.getKey();
			String title = extensionLink.getValue();

			linksForModule.add(new ModuleAdministrationLinks1_8.AdministrationLink(link, title));
		}

		return linksForModule;
	}
}
