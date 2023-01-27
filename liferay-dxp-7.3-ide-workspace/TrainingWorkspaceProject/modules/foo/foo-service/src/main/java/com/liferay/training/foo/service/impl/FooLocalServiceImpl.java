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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.search.document.Document;
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

import java.util.ArrayList;
import java.util.Date;
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
		foo.setUserId(serviceContext.getUserId());
		
		foo.setStatus(WorkflowConstants.STATUS_DRAFT);
		foo.setStatusByUserId(serviceContext.getUserId());
		foo.setStatusByUserName(userLocalService.getUser(serviceContext.getUserId()).getScreenName());
		foo.setStatusDate(serviceContext.getModifiedDate(null));
		
		foo =  super.addFoo(foo);
		
		resourceLocalService.addResources(
				foo.getCompanyId(), foo.getGroupId(), foo.getUserId(),
				Foo.class.getName(), foo.getFooId(), false,
				serviceContext.isAddGroupPermissions(), serviceContext.isAddGuestPermissions());
		
		updateAsset(foo, serviceContext);
		
		WorkflowHandlerRegistryUtil.startWorkflowInstance(foo.getCompanyId(), 
				foo.getGroupId(), foo.getUserId(), Foo.class.getName(), 
	            foo.getPrimaryKey(), foo, serviceContext);
		
		return foo;
	}
	
	@Indexable(type = IndexableType.REINDEX)
	public Foo updateFoo(long fooId, long groupId, String field1, ServiceContext serviceContext) throws PortalException {
		
		Foo foo = getFoo(fooId);
		foo.setGroupId(groupId);
		foo.setField1(field1);
		
		foo =  super.updateFoo(foo);
		
		updateAsset(foo, serviceContext);
		
		return foo;
	}
	
	private void updateAsset(Foo foo, ServiceContext serviceContext) throws PortalException {
		
		assetEntryLocalService.updateEntry(serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				foo.getCreateDate(), foo.getModifiedDate(), Foo.class.getName(),
				foo.getFooId(), foo.getUserUuid(), 0, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), true, true, foo.getCreateDate(), null, null, null,
				ContentTypes.TEXT_HTML, foo.getField1(), StringPool.BLANK,
				null, null, null, 0, 0, serviceContext.getAssetPriority());
	}
	
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Foo deleteFoo(long fooId) throws PortalException {
		Foo foo = fetchFoo(fooId);

		if (foo != null) {
			return fooLocalService.deleteFoo(foo);
		}

		return null;
	}

	public Foo deleteFoo(Foo foo) {
		try {
			resourceLocalService.deleteResource(
					foo.getCompanyId(), Foo.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, foo.getFooId());
			
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
					foo.getCompanyId(), foo.getGroupId(),
				    Foo.class.getName(), foo.getFooId());
			
		} catch (PortalException e) {
			_log.warn("Error deleting persisted foo permissions: " + e.getMessage(), e);
		}

		// call the super action method to try the delete.
		return super.deleteFoo(foo);
	}
	
	@Indexable(type = IndexableType.REINDEX)
	public Foo updateStatus(long userId, long fooId, int status,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		
		
		Foo foo = getFoo(fooId);
		
		foo.setStatus(status);
		User user = userLocalService.getUser(userId);
		foo.setStatusByUserId(userId);
		foo.setStatusByUserName(user.getFullName());
		
		foo.setStatusDate(new Date());

		fooPersistence.update(foo);
		
		
		if (status == WorkflowConstants.STATUS_APPROVED) {

			assetEntryLocalService.updateVisible(Foo.class.getName(), fooId, true);

		} else {

			assetEntryLocalService.updateVisible(Foo.class.getName(), fooId, false);
		}
		
		return foo;
	}

	public List<Foo> getFoosByGroupId(long groupId) {
		
		return fooPersistence.findByGroupIdStatus(groupId,WorkflowConstants.STATUS_APPROVED);
	}
	
	public List<Foo> getFoosByGroupId(long groupId, int start, int end) {
		return fooPersistence.findByGroupIdStatus(groupId,WorkflowConstants.STATUS_APPROVED, start, end);
	}
	
	public List<Foo> getFoosByGroupId(long groupId, int start, int end, OrderByComparator<Foo> orderByComparator) {
		return fooPersistence.findByGroupIdStatus(groupId,WorkflowConstants.STATUS_APPROVED, start, end, orderByComparator);
	}
	
	// configure debug log level on com.liferay.portal.search.elasticsearch7.internal.ElasticsearchIndexSearcher 
	public List<Foo> searchFoo(long companyId, long groupId, String keywords, String orderFieldName, boolean orderReverse){
		
		// Build the Search Query
		
		MatchQuery matchQuery = queries.match("field1", keywords);  // Full Text Query : usually used for querying full text fields like the content field 

		TermsQuery termsQuery = queries.terms("field1");  // Term queries : look for exact matching on keyword fields and indexed terms.
		
		BooleanQuery booleanQuery = queries.booleanQuery();  // Compound Queries : wrap other queries

		// booleanQuery.addMustQueryClauses(queries.match("status", 0));

		
		// Build the Search Request
		
		SearchRequestBuilder searchRequestBuilder =
			searchRequestBuilderFactory.builder();
		
		searchRequestBuilder.emptySearchEnabled(true);
		
		String[] _entryClassNames = {Foo.class.getName()};
		
		Sort sort = SortFactoryUtil.create(orderFieldName, orderReverse);
		if (orderFieldName.equals(Field.CREATE_DATE)) {
			sort.setType(Sort.LONG_TYPE);
		}
		
		searchRequestBuilder.withSearchContext(
				searchContext -> {
					searchContext.setCompanyId(companyId);
					searchContext.setKeywords(keywords);
					searchContext.setEntryClassNames(_entryClassNames);
					searchContext.setSorts(sort);
				});

		searchRequestBuilder.postFilterQuery(queries.match("status", WorkflowConstants.STATUS_APPROVED));
		
		SearchRequest searchRequest = searchRequestBuilder.query(
				booleanQuery
		).build();
		
		// Execute the Search Request
		
		SearchResponse searchResponse = searcher.search(searchRequest);

		_log.debug(searchResponse.getRequestString());
		_log.debug(searchResponse.getResponseString());
		
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