.DEFAULT_GOAL:=help

COMPOSE_LOCAL := -f docker-compose.yml
COMPOSE_PRODUCTION := -f docker-compose.prod.yml

compose_v2_not_supported = $(shell command docker compose 2> /dev/null)
ifeq (,$(compose_v2_not_supported))
  DOCKER_COMPOSE_COMMAND = docker-compose
else
  DOCKER_COMPOSE_COMMAND = docker compose
endif

# ------------------------

run-local:        ## Generate LOCAL level Backend Infra at ONCE!
	@make javaBuild
	@make local

# ------------------------

javaBuild:          ## build the spring boot app
	./gradlew clean build

local:           ## build local level docker containers
	$(DOCKER_COMPOSE_COMMAND) ${COMPOSE_LOCAL} up -d --build

down:           ## remove all container
	$(DOCKER_COMPOSE_COMMAND) ${COMPOSE_ALL_FILE} down -v

help:           ## Show this help.
	@echo "Make Application Docker Images and Containers using Docker-Compose files in 'docker' Dir."
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m (default: help)\n\nTargets:\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-12s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)
