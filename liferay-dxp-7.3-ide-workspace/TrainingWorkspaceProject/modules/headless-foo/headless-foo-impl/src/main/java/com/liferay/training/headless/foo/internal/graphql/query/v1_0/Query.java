package com.liferay.training.headless.foo.internal.graphql.query.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.training.headless.foo.dto.v1_0.Item;
import com.liferay.training.headless.foo.resource.v1_0.ItemResource;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author lena
 * @generated
 */
@Generated("")
public class Query {

	public static void setItemResourceComponentServiceObjects(
		ComponentServiceObjects<ItemResource>
			itemResourceComponentServiceObjects) {

		_itemResourceComponentServiceObjects =
			itemResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {items(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the list of items. Results can be paginated, filtered, searched, and sorted."
	)
	public ItemPage items(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_itemResourceComponentServiceObjects,
			this::_populateResourceContext,
			itemResource -> new ItemPage(
				itemResource.getItemsPage(
					search, _filterBiFunction.apply(itemResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(itemResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {item(itemId: ___){name, id, groupId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the item via its ID.")
	public Item item(@GraphQLName("itemId") String itemId) throws Exception {
		return _applyComponentServiceObjects(
			_itemResourceComponentServiceObjects,
			this::_populateResourceContext,
			itemResource -> itemResource.getItem(itemId));
	}

	@GraphQLName("ItemPage")
	public class ItemPage {

		public ItemPage(Page itemPage) {
			actions = itemPage.getActions();

			items = itemPage.getItems();
			lastPage = itemPage.getLastPage();
			page = itemPage.getPage();
			pageSize = itemPage.getPageSize();
			totalCount = itemPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Item> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(ItemResource itemResource)
		throws Exception {

		itemResource.setContextAcceptLanguage(_acceptLanguage);
		itemResource.setContextCompany(_company);
		itemResource.setContextHttpServletRequest(_httpServletRequest);
		itemResource.setContextHttpServletResponse(_httpServletResponse);
		itemResource.setContextUriInfo(_uriInfo);
		itemResource.setContextUser(_user);
		itemResource.setGroupLocalService(_groupLocalService);
		itemResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<ItemResource>
		_itemResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}