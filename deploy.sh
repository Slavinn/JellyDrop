#!/bin/bash

echo "[INFO] Starting deployment..." | tee -a deploy.log

cd /opt/jellydrop || exit 1

echo "[INFO] Pulling latest code..." | tee -a deploy.log
git pull origin main || exit 1

echo "[INFO] Building JAR..." | tee -a deploy.log
./mvnw clean package -DskipTests || exit 1

echo "[INFO] Restarting service..." | tee -a deploy.log
sudo systemctl restart jellydrop

echo "[SUCCESS] Deployed at $(date)" | tee -a deploy.log
