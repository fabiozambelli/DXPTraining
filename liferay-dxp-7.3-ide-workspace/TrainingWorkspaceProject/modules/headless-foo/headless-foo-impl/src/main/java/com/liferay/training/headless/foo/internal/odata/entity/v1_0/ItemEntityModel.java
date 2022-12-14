package com.liferay.training.headless.foo.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ItemEntityModel implements EntityModel {

	public ItemEntityModel() {
		_entityFieldsMap = Stream.of(
				
				// sorting/filtering on name is okay too
				new StringEntityField(
						"name", locale -> Field.getSortableFieldName(Field.NAME))
		).collect(
				Collectors.toMap(EntityField::getName, Function.identity())
		);
	}
	
	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		// TODO Auto-generated method stub
		return _entityFieldsMap;
	}
	
	private final Map<String, EntityField> _entityFieldsMap;
}
