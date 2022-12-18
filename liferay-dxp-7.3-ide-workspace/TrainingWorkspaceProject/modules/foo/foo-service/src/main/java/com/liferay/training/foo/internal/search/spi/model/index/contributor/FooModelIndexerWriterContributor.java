package com.liferay.training.foo.internal.search.spi.model.index.contributor;

import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.FooLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.search.Document;

@Component(
		immediate = true,
		property = "indexer.class.name=com.liferay.training.foo.model.Foo",
		service = ModelIndexerWriterContributor.class
)
public class FooModelIndexerWriterContributor implements ModelIndexerWriterContributor<Foo> {

	@Override
	public void customize(BatchIndexingActionable batchIndexingActionable,
			ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod((Foo foo) -> {
			Document document = modelIndexerWriterDocumentHelper.getDocument(foo);

			batchIndexingActionable.addDocuments(document);
		});		
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		
		return _dynamicQueryBatchIndexingActionableFactory.getBatchIndexingActionable(fooLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Foo foo) {

		return foo.getCompanyId();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(Foo foo) {
		return IndexerWriterMode.UPDATE;
	}
	
	@Reference
	private FooLocalService fooLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory _dynamicQueryBatchIndexingActionableFactory;
}
