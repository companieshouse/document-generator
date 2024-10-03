#!/bin/bash


# Start script for document-generator

PORT=8080

exec java -jar -Dserver.port="${PORT}" "document-generator.jar"
