package com.liferay.training.foo.internal.search;

import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.kernel.search.Field;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import com.liferay.training.foo.model.Foo;

/*
 * The search framework must know about your entity and how to handle it during a search request. 
 * To register model entities with Liferay’s search framework, SearchRegistrars use the search 
 * framework’s registry to define certain things about your model entity’s ModelSearchDefinition: 
 * the default fields used to retrieve documents from the search engine, and the optional search 
 * services registered for your entity 
 */
@Component(immediate = true, service = {FooSearchRegistrar.class})
public class FooSearchRegistrar {

	/*
	 * On activation of the Component, a chain of search and indexing classes is registered for the Foo entity.
	 * In addition, set the default selected field names used to retrieve results documents from the search engine. 
	 * Then set the contributors used to build a model search definition.
	 */
	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = modelSearchRegistrarHelper.register(
				Foo.class, bundleContext,
				modelSearchDefinition -> {
					modelSearchDefinition.setDefaultSelectedFieldNames(
							Field.COMPANY_ID,
							Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
							Field.GROUP_ID, Field.SCOPE_GROUP_ID,
							Field.UID, Field.NAME, Field.DESCRIPTION, "field1");
					//modelSearchDefinition.setDefaultSelectedLocalizedFieldNames(
					//		Field.CONTENT, Field.TITLE);
					modelSearchDefinition.setModelIndexWriteContributor(
							modelIndexWriterContributor);
					modelSearchDefinition.setModelSummaryContributor(
							modelSummaryContributor);
					//modelSearchDefinition.setModelVisibilityContributor(
					//		modelVisibilityContributor);
				});
	}
	
	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}
	
	@Reference(
			target = "(indexer.class.name=com.liferay.training.foo.model.Foo)"
	)
	protected ModelIndexerWriterContributor<Foo> modelIndexWriterContributor;
	
	@Reference
	protected ModelSearchRegistrarHelper modelSearchRegistrarHelper;
	
	@Reference(
			target = "(indexer.class.name=com.liferay.training.foo.model.Foo)"
	)
	protected ModelSummaryContributor modelSummaryContributor;
	
	private ServiceRegistration<?> _serviceRegistration;
}
