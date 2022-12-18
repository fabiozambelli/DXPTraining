package com.liferay.training.foo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.training.foo.model.Foo;
import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = "indexer.class.name=com.liferay.training.foo.model.Foo",
		service = ModelDocumentContributor.class
)
public class FooModelDocumentContributor implements ModelDocumentContributor<Foo> {

	@Override
	public void contribute(Document document, Foo foo) {

		document.addText(Field.DESCRIPTION, foo.getField1());
		document.addText(Field.NAME, foo.getField1());
		document.addKeyword("field1", foo.getField1());		
	}

}
