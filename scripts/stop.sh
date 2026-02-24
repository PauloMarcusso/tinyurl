#!/bin/bash

set -e

echo "Stopping the application..."
cd "$(dirname "$0")/../docker"

docker-compose -f docker-compose.yml down -v --remove-orphans 2>/dev/null || true
docker-compose -f docker-compose.dev.yml down -v --remove-orphans 2>/dev/null || true
docker-compose -f docker-compose.prod.yml down -v --remove-orphans 2>/dev/null || true

docker rm -f $(docker ps -a -q --filter "name=tinyurl") 2>/dev/null || true

docker network prune -f 2>/dev/null || true

echo "Application stopped successfully!"