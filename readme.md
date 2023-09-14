# Docker Compose Setup for Fleet Management Application

This repository contains a Docker Compose configuration for running a Fleet Management Application with Angular, Fleet Service, Fleet Simulator, and MySQL.

## Prerequisites

- Docker and Docker Compose installed on your machine.

## Getting Started

1. Clone this repository to your local machine.

git clone <repository_url>
cd <repository_directory>

2. Build and start the application using Docker Compose.

docker-compose up

This command will try to start the following services:

- **angular-app** - Angular application running on port 4200.
- **fleet-service** - Fleet Service running on port 8080.
- **fleet-simulator** - Fleet Simulator running on port 8081.
- **fleetmanagement** - MySQL database for fleet management.

**Note**: Some of these containers will fail to start, so you may need to run them again through the Docker GUI.

Once the services are up and running, you can access the Angular application in your web browser by navigating to [http://localhost:4200](http://localhost:4200).
