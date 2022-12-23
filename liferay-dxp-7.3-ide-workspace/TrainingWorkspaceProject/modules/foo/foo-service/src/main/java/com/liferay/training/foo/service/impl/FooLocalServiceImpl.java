/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.training.foo.service.impl;

import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactory;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.base.FooLocalServiceBaseImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "model.class.name=com.liferay.training.foo.model.Foo", service = AopService.class)
public class FooLocalServiceImpl extends FooLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public Foo addFoo(long groupId, String field1, ServiceContext serviceContext) throws PortalException {
		
		long fooId = counterLocalService.increment(Foo.class.getName());
		
		Foo foo = createFoo(fooId);
		foo.setGroupId(groupId);
		foo.setField1(field1);
		
		return super.addFoo(foo);
	}
	
	public Foo updateFoo(long fooId, long groupId, String field1, ServiceContext serviceContext) throws PortalException {
		
		Foo foo = getFoo(fooId);
		foo.setGroupId(groupId);
		foo.setField1(field1);
		
		return super.updateFoo(foo);
	}
	
	public List<Foo> getFoosByGroupId(long groupId) {
		
		return fooPersistence.findByGroupId(groupId);
	}
	
	public List<Foo> getFoosByGroupId(long groupId, int start, int end) {
		return fooPersistence.findByGroupId(groupId, start, end);
	}
	
	public List<Foo> getFoosByGroupId(long groupId, int start, int end, OrderByComparator<Foo> orderByComparator) {
		return fooPersistence.findByGroupId(groupId, start, end, orderByComparator);
	}
	
	// configure debug log level on com.liferay.portal.search.elasticsearch7.internal.ElasticsearchIndexSearcher 
	public List<Foo> searchFoo(long companyId, long groupId, String keywords, String orderFieldName, boolean orderReverse){
		
		// Build the Search Query
		
		// MatchQuery matchQuery = queries.match("field1", keywords);
		
		// TermsQuery termsQuery = queries.terms("field1");

		BooleanQuery booleanQuery = queries.booleanQuery();

		// booleanQuery.addMustQueryClauses(termsQuery, matchQuery);

		
		// Build the Search Request
		
		SearchRequestBuilder searchRequestBuilder =
			searchRequestBuilderFactory.builder();
		
		searchRequestBuilder.emptySearchEnabled(true);
		
		String[] _entryClassNames = {Foo.class.getName()};
		
		Sort sort = SortFactoryUtil.create(orderFieldName, orderReverse);

		searchRequestBuilder.withSearchContext(
				searchContext -> {
					searchContext.setCompanyId(companyId);
					searchContext.setKeywords(keywords);
					searchContext.setEntryClassNames(_entryClassNames);
					searchContext.setSorts(sort);
				});

		SearchRequest searchRequest = searchRequestBuilder.query(
				booleanQuery
		).build();

		// Execute the Search Request
		
		SearchResponse searchResponse = searcher.search(searchRequest);

		// Process the Search Response
		
		SearchHits searchHits = searchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();
		
		List<Foo> resultsList = new ArrayList<>(searchHitsList.size());

		searchHitsList.forEach(
				searchHit -> {
					float hitScore = searchHit.getScore();

					Document doc = searchHit.getDocument();

					String entryClassPk = doc.getString(Field.ENTRY_CLASS_PK);

					_log.debug(doc.toString());

					resultsList.add(fooPersistence.fetchByPrimaryKey(Long.parseLong(entryClassPk)));
				});
			
		return resultsList;
	}
	
	@Override
	public Foo addFoo(Foo foo) {
		throw new UnsupportedOperationException("Not supported.");
	}
	
	@Override
	public Foo updateFoo(Foo foo) {
		throw new UnsupportedOperationException("Not supported.");
	}
	
	@Override
	public Foo getFoo(long fooId) throws PortalException {
		return super.getFoo(fooId);
	}
	
	private static final Logger _log = LoggerFactory.getLogger(FooLocalServiceImpl.class);
	
	@Reference
	protected Queries queries;

	@Reference
	protected Searcher searcher;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

}