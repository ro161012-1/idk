#!/bin/bash
# Mindustry TeaVM Build Script
# Compiles Mindustry to WebAssembly using TeaVM

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
echo -e "${BLUE}║       Mindustry TeaVM (WebAssembly) Build                    ║${NC}"
echo -e "${BLUE}╚══════════════════════════════════════════════════════════════╝${NC}"
echo ""
log_info "TeaVM compiles Java bytecode directly to WebAssembly"
log_info "Supports: Reflection, Threading, SIMD, Exceptions, Generated Code"
log_info ""

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

# Add teaVM module to settings.gradle if not present
if ! grep -q "include 'teaVM'" settings.gradle; then
    log_info "Adding teaVM module to settings.gradle..."
    sed -i "/include 'desktop', 'core', 'server', 'ios', 'annotations', 'tools', 'tests', 'html'/a include 'teaVM'" settings.gradle
fi

echo ""
echo "Select build mode:"
echo "  1) Full production build (WASM, minified, optimized, threads, SIMD)"
echo "  2) Development build (with source maps, not minified)"
echo "  3) Development server (live reload on port 8080)"
echo "  4) Convert shaders only (GLSL 330 -> WebGL 2)"
echo "  5) Clean build"
echo "  6) Build with assets copy"
echo ""
read -p "Choice [1-6]: " choice

case $choice in
    1)
        log_info "Building production release..."
        export GRADLE_OPTS="-Xms4g -Xmx16g"
        ./gradlew :teaVM:teavmBuild --no-daemon -Dorg.gradle.jvmargs="-Xms4g -Xmx16g" 2>&1 | tee build-teavm.log
        ;;
    2)
        log_info "Building development version..."
        export GRADLE_OPTS="-Xms4g -Xmx16g"
        ./gradlew :teaVM:teavmBuild -PteavmDebug=true --no-daemon -Dorg.gradle.jvmargs="-Xms4g -Xmx16g" 2>&1 | tee build-teavm.log
        ;;
    3)
        log_info "Starting development server on http://localhost:8080"
        export GRADLE_OPTS="-Xms4g -Xmx16g"
        ./gradlew :teaVM:runDev --no-daemon -Dorg.gradle.jvmargs="-Xms4g -Xmx16g"
        ;;
    4)
        log_info "Converting shaders (GLSL 330 -> WebGL 2)..."
        ./gradlew :teaVM:convertShaders --no-daemon 2>&1 | tee build-shaders.log
        ;;
    5)
        log_info "Cleaning..."
        ./gradlew :teaVM:clean --no-daemon
        rm -rf teaVM/build/teavm
        log_success "Clean complete"
        ;;
    6)
        log_info "Building with assets copy..."
        export GRADLE_OPTS="-Xms4g -Xmx16g"
        ./gradlew :teaVM:buildAll --no-daemon -Dorg.gradle.jvmargs="-Xms4g -Xmx16g" 2>&1 | tee build-teavm.log
        ;;
    *)
        log_error "Invalid choice"
        exit 1
        ;;
esac

result=${PIPESTATUS[0]}

if [[ $result -eq 0 ]]; then
    log_success "Build completed successfully!"
    echo ""
    if [[ -d "teaVM/build/teavm" ]]; then
        log_success "Output: teaVM/build/teavm/"
        log_success "Open: file://$(pwd)/teaVM/build/teavm/index.html"
        echo ""
        log_info "Files generated:"
        ls -la teaVM/build/teavm/
        
        # Check WASM size
        if [[ -f "teaVM/build/teavm/mindustry.wasm" ]]; then
            wasm_size=$(du -h teaVM/build/teavm/mindustry.wasm | cut -f1)
            log_info "WASM size: $wasm_size"
        fi
        if [[ -f "teaVM/build/teavm/mindustry.js" ]]; then
            js_size=$(du -h teaVM/build/teavm/mindustry.js | cut -f1)
            log_info "JS size: $js_size"
        fi
    fi
else
    log_error "Build failed. Check build-teavm.log"
    echo ""
    log_info "Common issues:"
    if grep -q "NoClassDefFoundError\|ClassNotFoundException" build-teavm.log; then
        log_error "Missing classes - check reflection.json includes"
        grep -i "classnotfound\|noclassdeffound" build-teavm.log | head -10
    fi
    if grep -q "OutOfMemoryError\|GC overhead" build-teavm.log; then
        log_error "Out of memory - increase GRADLE_OPTS"
    fi
    if grep -q "Shader\|GLSL\|WebGL" build-teavm.log; then
        log_error "Shader compilation error - check shader conversion"
    fi
    if grep -q "arc.backends.teavm" build-teavm.log; then
        log_error "Arc TeaVM backend not found - check dependencies"
    fi
    echo ""
    log_info "First 30 errors:"
    grep -A2 "ERROR\|FAILED\|Exception" build-teavm.log | head -60
fi