COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 BRANCH=$(git rev-parse --abbrev-ref HEAD) docker-compose build jakartaEE
