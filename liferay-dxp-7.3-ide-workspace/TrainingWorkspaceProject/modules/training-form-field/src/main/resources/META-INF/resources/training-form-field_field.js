AUI.add(
	'training-form-field-form-field',
	function(A) {
		var TrainingFormFieldField = A.Component.create(
			{
				ATTRS: {
					type: {
						value: 'training-form-field-form-field'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'training-form-field-form-field',

				prototype: {
				}
			}
		);

		Liferay.namespace('DDM.Field').TrainingFormField = TrainingFormFieldField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);