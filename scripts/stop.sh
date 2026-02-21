#!/bin/bash

set -e

echo "Stopping the application..."
cd "$(dirname "$0")/../docker"

docker-compose down

echo "Application stopped successfully!"