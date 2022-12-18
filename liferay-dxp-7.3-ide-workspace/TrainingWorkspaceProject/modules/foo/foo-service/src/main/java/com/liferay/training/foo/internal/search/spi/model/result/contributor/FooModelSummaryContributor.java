package com.liferay.training.foo.internal.search.spi.model.result.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = "indexer.class.name=com.liferay.training.foo.model.Foo",
		service = ModelSummaryContributor.class
)
public class FooModelSummaryContributor implements ModelSummaryContributor {

	@Override
	public Summary getSummary(Document document, Locale locale, String snippet) {

		Summary summary = new Summary(
				document.get(Field.NAME),
				document.get(Field.DESCRIPTION));

		summary.setMaxContentLength(200);
		
		return summary;
	}

}
