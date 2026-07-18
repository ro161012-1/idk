#!/bin/bash
# Build script for the FULL GAME ATTEMPT
# This tries to compile as much of Mindustry as possible for HTML5.

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
echo -e "${BLUE}║       Mindustry HTML5 FULL GAME Build Attempt                ║${NC}"
echo -e "${BLUE}╚══════════════════════════════════════════════════════════════╝${NC}"
echo ""
log_warn "This will likely FAIL due to GWT-incompatible code."
log_warn "Expected compilation time: 20-60 minutes"
log_warn "Required RAM: 8GB+"
echo ""

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

# Show what will be excluded
echo ""
log_info "Packages EXCLUDED from compilation:"
echo "  - mindustry.gen.* (generated code)"
echo "  - mindustry.mod.* (mod loading)"
echo "  - mindustry.ai.* (AI/threading)"
echo "  - mindustry.steam.* / discord.* (natives)"
echo "  - mindustry.desktop.* / server.* / android.* / ios.*"
echo "  - arc.backend.sdl.* (native backend)"
echo "  - TypeRegistry, Vars, ContentCreator* (reflection-heavy)"
echo ""

read -p "Continue with full game build attempt? (y/N) " -n 1 -r
echo ""
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    log_info "Cancelled."
    exit 0
fi

# Build the full game attempt
log_info "Building full game attempt..."
export GRADLE_OPTS="-Xms2g -Xmx8g"

./gradlew :html:compileFull --no-daemon -Dorg.gradle.jvmargs="-Xms2g -Xmx8g" 2>&1 | tee build-full.log

result=${PIPESTATUS[0]}

if [[ $result -eq 0 ]]; then
    log_success "Full game built successfully!"
    echo ""
    log_success "Output: html/build/war-full/mindustryfull/"
    log_success "Open: file://$(pwd)/html/build/war-full/mindustryfull/index.html"
else
    log_error "Build failed (expected). Check build-full.log"
    echo ""
    log_info "Common errors and fixes:"
    echo ""
    
    if grep -q "No source code is available for type" build-full.log; then
        log_error "Missing GWT source for classes"
        log_info "Fix: Add <source path=\"../core\"/> for packages, or create GWT emulation"
        grep "No source code is available for type" build-full.log | head -10
    fi
    
    if grep -q "could not find.*class" build-full.log; then
        log_error "Missing classes in GWT compilation"
        log_info "Fix: Check MindustryGwt.gwt.xml excludes/includes"
    fi
    
    if grep -q "java\.lang\.reflect\|java\.nio\|java\.util\.concurrent" build-full.log; then
        log_error "JDK classes not in GWT JRE emulation"
        log_info "Fix: Create html/src/main/java/java/... emulation classes"
    fi
    
    if grep -q "mindustry\.gen\." build-full.log; then
        log_error "Generated code still being referenced"
        log_info "Fix: Add more excludes or create stubs in html/src/"
    fi
    
    echo ""
    log_info "First 20 errors:"
    grep -A2 "ERROR\|FAILED" build-full.log | head -40
fi