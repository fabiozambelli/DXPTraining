;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-training-form-field': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'training-form-field-form-field': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'training-form-field_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();