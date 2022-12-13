package handless.foo.internal.resource.v1_0;

import handless.foo.resource.v1_0.FooResource;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.Field;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import com.liferay.training.foo.model.Foo;
import com.liferay.training.foo.service.FooService;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @author lena
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/foo.properties",
	scope = ServiceScope.PROTOTYPE, service = FooResource.class
)
public class FooResourceImpl extends BaseFooResourceImpl implements EntityModelResource {
	

	@Override
	public Page<handless.foo.dto.v1_0.Foo> getFoosPage(String search, Filter filter, Pagination pagination, Sort[] sorts) throws Exception {
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
					document -> _toFoo(
							_fooService.getFoo(
									GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
					sorts);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public handless.foo.dto.v1_0.Foo getFoo(@NotNull String fooId) throws Exception {
	  Foo _foo = _fooService.getFoo(Long.parseLong(fooId));
	  return _toFoo(_foo);
	}
	
	@Override
	public handless.foo.dto.v1_0.Foo postFoo(handless.foo.dto.v1_0.Foo foo) throws Exception {
			  
	  Foo _foo = _fooService.addFoo(Long.parseLong(foo.getGroupId()), foo.getName(), _getServiceContext());

	  return _toFoo(_foo);
	}

	
	protected handless.foo.dto.v1_0.Foo _toFoo(Foo _foo) throws Exception {
		return new handless.foo.dto.v1_0.Foo() {{
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

}