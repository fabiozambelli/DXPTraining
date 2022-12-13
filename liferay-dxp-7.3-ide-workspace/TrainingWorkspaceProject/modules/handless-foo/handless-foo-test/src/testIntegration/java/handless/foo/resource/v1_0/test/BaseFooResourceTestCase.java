package handless.foo.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import handless.foo.client.dto.v1_0.Foo;
import handless.foo.client.http.HttpInvoker;
import handless.foo.client.pagination.Page;
import handless.foo.client.pagination.Pagination;
import handless.foo.client.resource.v1_0.FooResource;
import handless.foo.client.serdes.v1_0.FooSerDes;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author lena
 * @generated
 */
@Generated("")
public abstract class BaseFooResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_fooResource.setContextCompany(testCompany);

		FooResource.Builder builder = FooResource.builder();

		fooResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Foo foo1 = randomFoo();

		String json = objectMapper.writeValueAsString(foo1);

		Foo foo2 = FooSerDes.toDTO(json);

		Assert.assertTrue(equals(foo1, foo2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Foo foo = randomFoo();

		String json1 = objectMapper.writeValueAsString(foo);
		String json2 = FooSerDes.toJSON(foo);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Foo foo = randomFoo();

		foo.setGroupId(regex);
		foo.setId(regex);
		foo.setName(regex);

		String json = FooSerDes.toJSON(foo);

		Assert.assertFalse(json.contains(regex));

		foo = FooSerDes.toDTO(json);

		Assert.assertEquals(regex, foo.getGroupId());
		Assert.assertEquals(regex, foo.getId());
		Assert.assertEquals(regex, foo.getName());
	}

	@Test
	public void testGetFoosPage() throws Exception {
		Page<Foo> page = fooResource.getFoosPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Foo foo1 = testGetFoosPage_addFoo(randomFoo());

		Foo foo2 = testGetFoosPage_addFoo(randomFoo());

		page = fooResource.getFoosPage(null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(foo1, (List<Foo>)page.getItems());
		assertContains(foo2, (List<Foo>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFoosPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Foo foo1 = randomFoo();

		foo1 = testGetFoosPage_addFoo(foo1);

		for (EntityField entityField : entityFields) {
			Page<Foo> page = fooResource.getFoosPage(
				null, getFilterString(entityField, "between", foo1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(foo1), (List<Foo>)page.getItems());
		}
	}

	@Test
	public void testGetFoosPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Foo foo1 = testGetFoosPage_addFoo(randomFoo());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Foo foo2 = testGetFoosPage_addFoo(randomFoo());

		for (EntityField entityField : entityFields) {
			Page<Foo> page = fooResource.getFoosPage(
				null, getFilterString(entityField, "eq", foo1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(foo1), (List<Foo>)page.getItems());
		}
	}

	@Test
	public void testGetFoosPageWithPagination() throws Exception {
		Page<Foo> totalPage = fooResource.getFoosPage(null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Foo foo1 = testGetFoosPage_addFoo(randomFoo());

		Foo foo2 = testGetFoosPage_addFoo(randomFoo());

		Foo foo3 = testGetFoosPage_addFoo(randomFoo());

		Page<Foo> page1 = fooResource.getFoosPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<Foo> foos1 = (List<Foo>)page1.getItems();

		Assert.assertEquals(foos1.toString(), totalCount + 2, foos1.size());

		Page<Foo> page2 = fooResource.getFoosPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Foo> foos2 = (List<Foo>)page2.getItems();

		Assert.assertEquals(foos2.toString(), 1, foos2.size());

		Page<Foo> page3 = fooResource.getFoosPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(foo1, (List<Foo>)page3.getItems());
		assertContains(foo2, (List<Foo>)page3.getItems());
		assertContains(foo3, (List<Foo>)page3.getItems());
	}

	@Test
	public void testGetFoosPageWithSortDateTime() throws Exception {
		testGetFoosPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, foo1, foo2) -> {
				BeanUtils.setProperty(
					foo1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetFoosPageWithSortInteger() throws Exception {
		testGetFoosPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, foo1, foo2) -> {
				BeanUtils.setProperty(foo1, entityField.getName(), 0);
				BeanUtils.setProperty(foo2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetFoosPageWithSortString() throws Exception {
		testGetFoosPageWithSort(
			EntityField.Type.STRING,
			(entityField, foo1, foo2) -> {
				Class<?> clazz = foo1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						foo1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						foo2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						foo1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						foo2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						foo1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						foo2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetFoosPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Foo, Foo, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Foo foo1 = randomFoo();
		Foo foo2 = randomFoo();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, foo1, foo2);
		}

		foo1 = testGetFoosPage_addFoo(foo1);

		foo2 = testGetFoosPage_addFoo(foo2);

		for (EntityField entityField : entityFields) {
			Page<Foo> ascPage = fooResource.getFoosPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(foo1, foo2), (List<Foo>)ascPage.getItems());

			Page<Foo> descPage = fooResource.getFoosPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(foo2, foo1), (List<Foo>)descPage.getItems());
		}
	}

	protected Foo testGetFoosPage_addFoo(Foo foo) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFoosPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"foos",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject foosJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/foos");

		long totalCount = foosJSONObject.getLong("totalCount");

		Foo foo1 = testGraphQLFoo_addFoo();
		Foo foo2 = testGraphQLFoo_addFoo();

		foosJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/foos");

		Assert.assertEquals(
			totalCount + 2, foosJSONObject.getLong("totalCount"));

		assertContains(
			foo1,
			Arrays.asList(FooSerDes.toDTOs(foosJSONObject.getString("items"))));
		assertContains(
			foo2,
			Arrays.asList(FooSerDes.toDTOs(foosJSONObject.getString("items"))));
	}

	@Test
	public void testPostFoo() throws Exception {
		Foo randomFoo = randomFoo();

		Foo postFoo = testPostFoo_addFoo(randomFoo);

		assertEquals(randomFoo, postFoo);
		assertValid(postFoo);
	}

	protected Foo testPostFoo_addFoo(Foo foo) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetFoo() throws Exception {
		Foo postFoo = testGetFoo_addFoo();

		Foo getFoo = fooResource.getFoo(postFoo.getId());

		assertEquals(postFoo, getFoo);
		assertValid(getFoo);
	}

	protected Foo testGetFoo_addFoo() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetFoo() throws Exception {
		Foo foo = testGraphQLFoo_addFoo();

		Assert.assertTrue(
			equals(
				foo,
				FooSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"foo",
								new HashMap<String, Object>() {
									{
										put("fooId", "\"" + foo.getId() + "\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/foo"))));
	}

	@Test
	public void testGraphQLGetFooNotFound() throws Exception {
		String irrelevantFooId = "\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"foo",
						new HashMap<String, Object>() {
							{
								put("fooId", irrelevantFooId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Foo testGraphQLFoo_addFoo() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(Foo foo, List<Foo> foos) {
		boolean contains = false;

		for (Foo item : foos) {
			if (equals(foo, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(foos + " does not contain " + foo, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Foo foo1, Foo foo2) {
		Assert.assertTrue(foo1 + " does not equal " + foo2, equals(foo1, foo2));
	}

	protected void assertEquals(List<Foo> foos1, List<Foo> foos2) {
		Assert.assertEquals(foos1.size(), foos2.size());

		for (int i = 0; i < foos1.size(); i++) {
			Foo foo1 = foos1.get(i);
			Foo foo2 = foos2.get(i);

			assertEquals(foo1, foo2);
		}
	}

	protected void assertEqualsIgnoringOrder(List<Foo> foos1, List<Foo> foos2) {
		Assert.assertEquals(foos1.size(), foos2.size());

		for (Foo foo1 : foos1) {
			boolean contains = false;

			for (Foo foo2 : foos2) {
				if (equals(foo1, foo2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(foos2 + " does not contain " + foo1, contains);
		}
	}

	protected void assertValid(Foo foo) throws Exception {
		boolean valid = true;

		if (foo.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("groupId", additionalAssertFieldName)) {
				if (foo.getGroupId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (foo.getName() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<Foo> page) {
		boolean valid = false;

		java.util.Collection<Foo> foos = page.getItems();

		int size = foos.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(handless.foo.dto.v1_0.Foo.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Foo foo1, Foo foo2) {
		if (foo1 == foo2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("groupId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(foo1.getGroupId(), foo2.getGroupId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(foo1.getId(), foo2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(foo1.getName(), foo2.getName())) {
					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_fooResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_fooResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, Foo foo) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("groupId")) {
			sb.append("'");
			sb.append(String.valueOf(foo.getGroupId()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			sb.append("'");
			sb.append(String.valueOf(foo.getId()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(foo.getName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected Foo randomFoo() throws Exception {
		return new Foo() {
			{
				groupId = StringUtil.toLowerCase(RandomTestUtil.randomString());
				id = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Foo randomIrrelevantFoo() throws Exception {
		Foo randomIrrelevantFoo = randomFoo();

		return randomIrrelevantFoo;
	}

	protected Foo randomPatchFoo() throws Exception {
		return randomFoo();
	}

	protected FooResource fooResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseFooResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private handless.foo.resource.v1_0.FooResource _fooResource;

}