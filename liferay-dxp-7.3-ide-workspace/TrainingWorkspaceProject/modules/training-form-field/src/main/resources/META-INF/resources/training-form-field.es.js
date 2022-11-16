import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './training-form-field.soy';

/**
 * TrainingFormField Component
 */
class TrainingFormField extends Component {}

// Register component
Soy.register(TrainingFormField, templates, 'render');

if (!window.DDMTrainingFormField) {
	window.DDMTrainingFormField = {

	};
}

window.DDMTrainingFormField.render = TrainingFormField;

export default TrainingFormField;