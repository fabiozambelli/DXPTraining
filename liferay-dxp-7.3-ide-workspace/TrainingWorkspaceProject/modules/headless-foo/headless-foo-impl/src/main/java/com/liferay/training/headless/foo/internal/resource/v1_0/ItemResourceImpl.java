package com.liferay.training.headless.foo.internal.resource.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.FooService;
import com.liferay.training.headless.foo.dto.v1_0.Item;
import com.liferay.training.headless.foo.internal.odata.entity.v1_0.ItemEntityModel;
import com.liferay.training.headless.foo.resource.v1_0.ItemResource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author lena
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/item.properties",
	scope = ServiceScope.PROTOTYPE, service = ItemResource.class
)
public class ItemResourceImpl extends BaseItemResourceImpl implements EntityModelResource {
	
	private ItemEntityModel _itemEntityModel = new ItemEntityModel();
	
	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) throws Exception {
		return _itemEntityModel;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Page<Item> getItemsPage(String search, Filter filter, Pagination pagination, Sort[] sorts) throws Exception {
		try {

			return SearchUtil.search(
					booleanQuery -> {
						// does nothing, we just need the UnsafeConsumer<BooleanQuery, Exception> method
					},
					filter, Foo.class, search, pagination,
					queryConfig -> queryConfig.setSelectedFieldNames(
							Field.ENTRY_CLASS_PK),
					searchContext -> searchContext.setCompanyId(
							contextCompany.getCompanyId()),
					document -> _toItem(
							_fooService.getFoo(
									GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
					sorts);

		} catch (Exception e) {
			_log.error("Error listing items: " + e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Item getItem(@NotNull String itemId) throws Exception {
	  Foo _foo = _fooService.getFoo(Long.parseLong(itemId));
	  return _toItem(_foo);
	}
	
	@Override
	public Item postItem(Item item) throws Exception {
			  
	  Foo _foo = _fooService.addFoo(Long.parseLong(item.getGroupId()), item.getName(), _getServiceContext());

	  return _toItem(_foo);
	}

	
	protected Item _toItem(Foo _foo) throws Exception {
		return new Item() {{
			name = _foo.getField1();
			id = Long.toString(_foo.getFooId());
			groupId = Long.toString(_foo.getGroupId());
		}};
	}
	
	protected ServiceContext _getServiceContext() {

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setCompanyId(contextCompany.getCompanyId());

		long userId = PrincipalThreadLocal.getUserId();
		serviceContext.setUserId(userId);

		return serviceContext;
	}
	
	@Reference
	private FooService _fooService;
	
	private static final Logger _log = LoggerFactory.getLogger(ItemResourceImpl.class);
}