package handless.foo.internal.graphql.query.v1_0;

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

import handless.foo.dto.v1_0.Foo;
import handless.foo.resource.v1_0.FooResource;

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

	public static void setFooResourceComponentServiceObjects(
		ComponentServiceObjects<FooResource>
			fooResourceComponentServiceObjects) {

		_fooResourceComponentServiceObjects =
			fooResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {foos(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the list of foos. Results can be paginated, filtered, searched, and sorted."
	)
	public FooPage foos(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_fooResourceComponentServiceObjects, this::_populateResourceContext,
			fooResource -> new FooPage(
				fooResource.getFoosPage(
					search, _filterBiFunction.apply(fooResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(fooResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {foo(fooId: ___){name, id, groupId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the item via its ID.")
	public Foo foo(@GraphQLName("fooId") String fooId) throws Exception {
		return _applyComponentServiceObjects(
			_fooResourceComponentServiceObjects, this::_populateResourceContext,
			fooResource -> fooResource.getFoo(fooId));
	}

	@GraphQLName("FooPage")
	public class FooPage {

		public FooPage(Page fooPage) {
			actions = fooPage.getActions();

			items = fooPage.getItems();
			lastPage = fooPage.getLastPage();
			page = fooPage.getPage();
			pageSize = fooPage.getPageSize();
			totalCount = fooPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Foo> items;

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

	private void _populateResourceContext(FooResource fooResource)
		throws Exception {

		fooResource.setContextAcceptLanguage(_acceptLanguage);
		fooResource.setContextCompany(_company);
		fooResource.setContextHttpServletRequest(_httpServletRequest);
		fooResource.setContextHttpServletResponse(_httpServletResponse);
		fooResource.setContextUriInfo(_uriInfo);
		fooResource.setContextUser(_user);
		fooResource.setGroupLocalService(_groupLocalService);
		fooResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<FooResource>
		_fooResourceComponentServiceObjects;

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