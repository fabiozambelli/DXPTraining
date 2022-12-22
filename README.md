- ## TrainingWorkspaceProject
- modules
	- announcements-jsp-override
	- customize-jsp-porlet-filter
	- expando
	- foo
		> search powered by Elasticsearch APIs
		> headless REST APIs 
	- gradebook
	- helloworld
	- message-bus-listener
	- mvc-command-override
	- my-training-form-field
	- my-user-local-service-override
	- portlet-module-portlet
	- post-login-event-listener
	- strust-action-override
	- training-gogo-command
	- user-indexer-post-processor
	- user-post-update-model-listener
- themes
	- livingstone-fjord-theme
	- training-fragments
	- training-layouttpl
	- training-theme
	
## Usage
./TrainingWorkspaceProject/build/docker$ docker compose up --build -d
./TrainingWorkspaceProject/modules/<module>$ ../../gradlew dockerDeploy

