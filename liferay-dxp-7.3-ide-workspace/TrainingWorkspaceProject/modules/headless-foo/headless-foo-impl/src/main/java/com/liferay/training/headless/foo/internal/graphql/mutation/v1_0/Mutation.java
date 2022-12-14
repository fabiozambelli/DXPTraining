package com.liferay.training.headless.foo.internal.graphql.mutation.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.training.headless.foo.dto.v1_0.Item;
import com.liferay.training.headless.foo.resource.v1_0.ItemResource;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author lena
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setItemResourceComponentServiceObjects(
		ComponentServiceObjects<ItemResource>
			itemResourceComponentServiceObjects) {

		_itemResourceComponentServiceObjects =
			itemResourceComponentServiceObjects;
	}

	@GraphQLField(description = "Create a new item.")
	public Item createItem(@GraphQLName("item") Item item) throws Exception {
		return _applyComponentServiceObjects(
			_itemResourceComponentServiceObjects,
			this::_populateResourceContext,
			itemResource -> itemResource.postItem(item));
	}

	@GraphQLField
	public Response createItemBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_itemResourceComponentServiceObjects,
			this::_populateResourceContext,
			itemResource -> itemResource.postItemBatch(callbackURL, object));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}