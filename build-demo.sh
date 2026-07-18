#!/bin/bash
# Build script for the MINIMAL DEMO (actually works!)
# This compiles only the demo code, not the full Mindustry game.

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

log_info() { echo -e "${BLUE}[INFO]${NC} $1"; }
log_success() { echo -e "${GREEN}[SUCCESS]${NC} $1"; }
log_warn() { echo -e "${YELLOW}[WARN]${NC} $1"; }
log_error() { echo -e "${RED}[ERROR]${NC} $1"; }

echo -e "${BLUE}╔══════════════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║       Mindustry HTML5 Minimal Demo Build                     ║${NC}"
echo -e "${BLUE}╚══════════════════════════════════════════════════════════════╝${NC}"
echo ""

# Check Java
if ! command -v java &> /dev/null; then
    log_error "Java not found! Install Java 17+ first."
    log_info "  Ubuntu/Debian: sudo apt install openjdk-17-jdk"
    log_info "  Or download: https://adoptium.net/temurin/releases/"
    exit 1
fi

java_version=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | cut -d'.' -f1)
if [[ "$java_version" -lt 17 ]]; then
    log_error "Java 17+ required, found $java_version"
    exit 1
fi
log_success "Java $java_version found"

# Check gradlew
if [[ ! -f "./gradlew" ]]; then
    log_error "gradlew not found"
    exit 1
fi
chmod +x ./gradlew

# Build the demo
log_info "Building minimal demo (this should work)..."
export GRADLE_OPTS="-Xms1g -Xmx4g"

./gradlew :html:compileDemo --no-daemon -Dorg.gradle.jvmargs="-Xms1g -Xmx4g" 2>&1 | tee build-demo.log

if [[ ${PIPESTATUS[0]} -eq 0 ]]; then
    log_success "Demo built successfully!"
    echo ""
    log_success "Output: html/build/war-demo/mindustrydemo/"
    log_success "Open: file://$(pwd)/html/build/war-demo/mindustrydemo/index.html"
    echo ""
    log_info "To run dev mode: ./gradlew :html:superDevDemo"
    log_info "To create WAR: ./gradlew :html:warDemo"
else
    log_error "Build failed. Check build-demo.log"
    grep -A3 -B3 "ERROR\|FAILED\|error:" build-demo.log | head -60
    exit 1
fi