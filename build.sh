#!/bin/bash
# ===============================================
# Build and Run Task Tracker CLI (with dependencies)
# ===============================================



# Stop on error
set -e

echo "🧹 Cleaning previous builds..."
mvn clean

echo "⚙️  Packaging project (including dependencies)..."
mvn package

echo "For more info, run the program with no args"
