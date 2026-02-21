#!/bin/bash
# scripts/dev.sh - Starts the development environment using Docker Compose.

set -e

echo "Building Docker image..."
cd "$(dirname "$0")/.."

docker build -f docker/Dockerfile -t tinyurl:latest .

echo "Docker image built successfully!"
echo ""
echo "Image name: tinyurl:latest"
docker images tinyurl:latest

