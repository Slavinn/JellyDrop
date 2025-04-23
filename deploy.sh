#!/bin/bash

set -e

REPO_DIR="/opt/jellydrop"
JAR_NAME="jellydrop.jar"
SERVICE_NAME="jellydrop.service"


echo "[INFO] Starting deployment..." | tee -a deploy.log

cd "$REPO_DIR" || exit 1

echo "[INFO] Pulling latest code..." | tee -a deploy.log
git fetch origin && git reset --hard origin/main || exit 1

echo "[INFO] Building JAR..." | tee -a deploy.log
./mvnw clean package -DskipTests || exit 1

echo "[INFO] Moving JAR..." | tee -a deploy.log
cp target/*.jar "$REPO_DIR/$JAR_NAME"

echo "[INFO] Restarting service..." | tee -a deploy.log
sudo systemctl restart "$SERVICE_NAME"

echo "[SUCCESS] Deployed at $(date)" | tee -a deploy.log
