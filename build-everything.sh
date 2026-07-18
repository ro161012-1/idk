#!/bin/bash
# Build script for the EVERYTHING port - attempts to compile LITERALLY EVERYTHING
# This includes all Mindustry code + full JDK emulation

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
echo -e "${BLUE}║       Mindustry HTML5 EVERYTHING PORT Build                  ║${NC}"
echo -e "${BLUE}╚══════════════════════════════════════════════════════════════╝${NC}"
echo ""
log_warn "This attempts to compile LITERALLY EVERYTHING:"
log_warn "  - All Mindustry core game code"
log_warn "  - All Arc framework code"
log_warn "  - Full JDK emulation (138+ classes)"
log_warn "  - All content, entities, graphics, UI, logic, networking"
log_warn ""
log_warn "Expected compilation time: 30-120 minutes"
log_warn "Required RAM: 12GB+ (uses 8G heap)"
log_warn ""

# Check Java
if ! command -v java &> /dev/null; then
    log_error "Java not found! Install Java 17+ first."
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

# Show what's included
echo ""
log_info "Source directories:"
echo "  - html/src/ (game launchers, platform, networking)"
echo "  - html/src/main/java/ (JDK emulation - 138 classes)"
echo "  - core/src/ (ALL Mindustry core code)"
echo "  - server/src/ (Server code)"
echo ""

log_info "JDK Emulation Classes Created:"
find html/src/main/java -name "*.java" | sort | sed 's|html/src/main/java/||' | sed 's|.java$||' | sed 's|/|.|g' | sed 's/^/  - /'
echo ""

log_info "GWT Module: Everything.gwt.xml (includes EVERYTHING except natives)"
echo ""

read -p "Start EVERYTHING build? This will take a while... (y/N) " -n 1 -r
echo ""
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    log_info "Cancelled."
    exit 0
fi

# Build the EVERYTHING port
log_info "Building EVERYTHING port..."
export GRADLE_OPTS="-Xms4g -Xmx12g"

./gradlew :html:compileFull --no-daemon -Dorg.gradle.jvmargs="-Xms4g -Xmx12g" 2>&1 | tee build-everything.log

result=${PIPESTATUS[0]}

if [[ $result -eq 0 ]]; then
    log_success "EVERYTHING PORT BUILT SUCCESSFULLY!"
    echo ""
    log_success "Output: html/build/war-full/mindustry/"
    log_success "Open: file://$(pwd)/html/build/war-full/mindustry/index.html"
    echo ""
    log_info "To run dev mode: ./gradlew :html:superDevFull"
    log_info "To create WAR: ./gradlew :html:warFull"
else
    log_error "Build failed (this is expected for such a massive port). Check build-everything.log"
    echo ""
    log_info "Common issues and fixes:"
    
    if grep -q "No source code is available for type" build-everything.log; then
        log_error "Missing GWT source for classes - add more emulation or excludes"
        grep "No source code is available for type" build-everything.log | head -20
    fi
    
    if grep -q "could not find.*class" build-everything.log; then
        log_error "Missing classes in GWT compilation"
    fi
    
    if grep -q "OutOfMemoryError\|GC overhead" build-everything.log; then
        log_error "Out of memory - increase GRADLE_OPTS heap"
    fi
    
    echo ""
    log_info "First 30 errors:"
    grep -A2 "ERROR\|FAILED" build-everything.log | head -60
fi