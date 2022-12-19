package com.liferay.training.foo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.training.foo.model.Foo;
import org.osgi.service.component.annotations.Component;

/*
 * Component purpose : control how model entity fields are indexed in search engine documents.
 * 
 * Model entities are searchable when their data fields are indexed by the search engine. 
 * Search and indexing code relies on Search APIs and SPIs: code inside a SPI module is used to customize 
 * existing behavior, while API modules contain behavior you want to use.
 */
@Component(
		immediate = true,
		property = "indexer.class.name=com.liferay.training.foo.model.Foo",
		service = ModelDocumentContributor.class
)
public class FooModelDocumentContributor implements ModelDocumentContributor<Foo> {

	/*
	 * The contribute method is called each time the add and update methods in the entity’s service layer are called.
	 */
	@Override
	public void contribute(Document document, Foo foo) {

		// Sometimes it is useful to have both a full text (text) and a keyword (keyword) 
		// version of the same field: one for full text search and the other for aggregations and sorting.
		
		document.addText(Field.DESCRIPTION, foo.getField1());
		document.addText(Field.NAME, foo.getField1());
		document.addKeyword("field1", foo.getField1());		
	}

}
