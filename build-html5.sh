#!/bin/bash
# Mindustry HTML5 Port Build Script
# Run this script to attempt building the GWT port

set -e

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}╔══════════════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║       Mindustry HTML5 Port Build Script                      ║${NC}"
echo -e "${BLUE}╚══════════════════════════════════════════════════════════════╝${NC}"
echo ""

# Configuration
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

JAVA_VERSION_REQUIRED=17
GRADLE_MEMORY="-Xms2g -Xmx8g"

# Helper functions
log_info() { echo -e "${BLUE}[INFO]${NC} $1"; }
log_success() { echo -e "${GREEN}[SUCCESS]${NC} $1"; }
log_warn() { echo -e "${YELLOW}[WARN]${NC} $1"; }
log_error() { echo -e "${RED}[ERROR]${NC} $1"; }

# Check Java
check_java() {
    log_info "Checking Java installation..."
    
    if ! command -v java &> /dev/null; then
        log_error "Java not found!"
        install_java
        return 1
    fi
    
    local java_version=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [[ "$java_version" -lt "$JAVA_VERSION_REQUIRED" ]]; then
        log_error "Java $JAVA_VERSION_REQUIRED+ required, found $java_version"
        install_java
        return 1
    fi
    
    log_success "Java $java_version found"
    return 0
}

# Install Java (attempts multiple methods)
install_java() {
    log_info "Attempting to install Java $JAVA_VERSION_REQUIRED..."
    
    # Try apt (Debian/Ubuntu)
    if command -v apt-get &> /dev/null; then
        log_info "Trying apt-get..."
        if apt-get update -y 2>/dev/null && apt-get install -y openjdk-${JAVA_VERSION_REQUIRED}-jdk-headless 2>/dev/null; then
            log_success "Java installed via apt"
            return 0
        fi
    fi
    
    # Try downloading Temurin
    log_info "Trying to download Eclipse Temurin..."
    local jdk_url="https://github.com/adoptium/temurin${JAVA_VERSION_REQUIRED}-binaries/releases/download/jdk-${JAVA_VERSION_REQUIRED}.0.11%2B9/OpenJDK${JAVA_VERSION_REQUIRED}U-jdk_x64_linux_hotspot_${JAVA_VERSION_REQUIRED}.0.11_9.tar.gz"
    local tmp_jdk="/tmp/jdk-${JAVA_VERSION_REQUIRED}.tar.gz"
    local jdk_dir="/tmp/jdk-${JAVA_VERSION_REQUIRED}"
    
    if wget -q "$jdk_url" -O "$tmp_jdk" 2>/dev/null || curl -LfsS "$jdk_url" -o "$tmp_jdk" 2>/dev/null; then
        tar -xzf "$tmp_jdk" -C /tmp/
        export JAVA_HOME="$jdk_dir"
        export PATH="$JAVA_HOME/bin:$PATH"
        log_success "Java downloaded and configured"
        return 0
    fi
    
    log_error "Could not install Java automatically."
    log_info "Please install Java $JAVA_VERSION_REQUIRED+ manually:"
    log_info "  - Ubuntu/Debian: sudo apt install openjdk-${JAVA_VERSION_REQUIRED}-jdk"
    log_info "  - Fedora: sudo dnf install java-${JAVA_VERSION_REQUIRED}-openjdk-devel"
    log_info "  - Arch: sudo pacman -S jdk${JAVA_VERSION_REQUIRED}-openjdk"
    log_info "  - Or download from: https://adoptium.net/temurin/releases/"
    return 1
}

# Check Gradle wrapper
check_gradle() {
    log_info "Checking Gradle wrapper..."
    if [[ ! -f "./gradlew" ]]; then
        log_error "gradlew not found in project root"
        return 1
    fi
    chmod +x ./gradlew
    log_success "Gradle wrapper found"
    return 0
}

# Clean build
clean_build() {
    log_info "Cleaning previous builds..."
    ./gradlew clean :html:clean --no-daemon 2>&1 | tail -20
    log_success "Clean complete"
}

# Build HTML5 module
build_html5() {
    local task="${1:-compileGwt}"
    log_info "Building HTML5 module (task: $task)..."
    log_warn "This will take 10-30 minutes and require 8GB+ RAM"
    
    # Increase Gradle memory
    export GRADLE_OPTS="$GRADLE_MEMORY"
    
    # Run build with detailed output
    ./gradlew :html:$task \
        --no-daemon \
        --info \
        --stacktrace \
        -Dorg.gradle.jvmargs="$GRADLE_MEMORY" \
        2>&1 | tee build-html5.log
    
    local result=${PIPESTATUS[0]}
    
    if [[ $result -eq 0 ]]; then
        log_success "Build completed successfully!"
        show_output_location
    else
        log_error "Build failed. Check build-html5.log for details."
        analyze_errors
        return 1
    fi
}

# Show where output is
show_output_location() {
    echo ""
    log_success "Output locations:"
    if [[ -d "html/build/war/mindustry" ]]; then
        echo -e "  ${GREEN}Development:${NC} html/build/war/mindustry/"
        echo -e "  ${GREEN}Open:${NC} file://$(pwd)/html/build/war/mindustry/index.html"
    fi
    if [[ -f "html/build/libs/mindustry.war" ]]; then
        echo -e "  ${GREEN}WAR:${NC} html/build/libs/mindustry.war"
    fi
}

# Analyze common build errors
analyze_errors() {
    log_info "Analyzing build errors..."
    
    if grep -q "could not find.*class" build-html5.log; then
        log_error "Missing classes - GWT cannot find required types"
        log_info "Fix: Add GWT emulation or exclude incompatible packages in MindustryGwt.gwt.xml"
    fi
    
    if grep -q "reflection" build-html5.log; then
        log_error "Reflection used - not supported in GWT"
        log_info "Fix: Configure reflection in GWT module or rewrite code"
    fi
    
    if grep -q "java\.nio\|java\.util\.concurrent\|javax\." build-html5.log; then
        log_error "JDK classes not in GWT JRE emulation"
        log_info "Fix: Create GWT emulation library in html/src/main/java/"
    fi
    
    if grep -q "OutOfMemoryError\|GC overhead" build-html5.log; then
        log_error "Out of memory"
        log_info "Fix: Increase GRADLE_OPTS memory (current: $GRADLE_MEMORY)"
    fi
    
    if grep -q "mindustry\.gen\." build-html5.log; then
        log_error "Generated code (mindustry.gen) incompatible"
        log_info "Fix: Exclude in MindustryGwt.gwt.xml: <exclude name=\"**/gen/**\"/>"
    fi
    
    if grep -q "mindustry\.mod\." build-html5.log; then
        log_error "Mod system incompatible"
        log_info "Fix: Exclude in MindustryGwt.gwt.xml: <exclude name=\"**/mod/**\"/>"
    fi
    
    # Show first 50 lines of error
    echo ""
    log_info "First errors from build log:"
    grep -A5 -B5 "ERROR\|FAILED\|error:" build-html5.log | head -80
}

# Development mode
dev_mode() {
    log_info "Starting SuperDevMode (development server)..."
    log_info "Open http://localhost:9876 in browser, click 'Dev Mode On', then http://localhost:8888"
    
    export GRADLE_OPTS="$GRADLE_MEMORY"
    ./gradlew :html:superDev --no-daemon -Dorg.gradle.jvmargs="$GRADLE_MEMORY"
}

# Production build
prod_build() {
    log_info "Building production release..."
    build_html5 "compileGwt"
    
    if [[ $? -eq 0 ]]; then
        log_info "Creating WAR for deployment..."
        ./gradlew :html:war --no-daemon -Dorg.gradle.jvmargs="$GRADLE_MEMORY"
        log_success "WAR created at html/build/libs/mindustry.war"
    fi
}

# Full port attempt (excludes known problematic modules)
attempt_full_port() {
    log_warn "═══════════════════════════════════════════════════════════════"
    log_warn "ATTEMPTING FULL PORT - THIS WILL LIKELY FAIL"
    log_warn "═══════════════════════════════════════════════════════════════"
    log_warn "Known incompatible modules that MUST be excluded:"
    log_warn "  - mindustry.gen.* (generated code, reflection)"
    log_warn "  - mindustry.mod.* (class loading, scripts, reflection)"
    log_warn "  - mindustry.ai.* (threading, complex logic)"
    log_warn "  - mindustry.net.* (TCP sockets - use WebSocket only)"
    log_warn "  - mindustry.steam.* / mindustry.discord.* (natives)"
    log_warn "  - arc.backend.sdl.* (native SDL backend)"
    log_warn ""
    log_warn "You need to edit html/src/mindustry/html/MindustryGwt.gwt.xml"
    log_warn "to exclude these packages before building."
    log_warn "═══════════════════════════════════════════════════════════════"
    echo ""
    
    read -p "Continue anyway? (y/N) " -n 1 -r
    echo ""
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        log_info "Cancelled. Edit MindustryGwt.gwt.xml first."
        return 1
    fi
    
    build_html5 "compileGwt"
}

# Main menu
main() {
    echo ""
    echo "Select build mode:"
    echo "  1) Check environment only"
    echo "  2) Development mode (SuperDevMode - live reload)"
    echo "  3) Production build (compileGwt)"
    echo "  4) Production + WAR"
    echo "  5) Attempt full port (will fail without exclusions)"
    echo "  6) Clean build"
    echo "  7) Analyze last build errors"
    echo ""
    read -p "Choice [1-7]: " choice
    
    case $choice in
        1)
            check_java && check_gradle && log_success "Environment ready!"
            ;;
        2)
            check_java && check_gradle && dev_mode
            ;;
        3)
            check_java && check_gradle && build_html5 "compileGwt"
            ;;
        4)
            check_java && check_gradle && prod_build
            ;;
        5)
            check_java && check_gradle && attempt_full_port
            ;;
        6)
            check_java && check_gradle && clean_build
            ;;
        7)
            analyze_errors
            ;;
        *)
            log_error "Invalid choice"
            exit 1
            ;;
    esac
}

# Run main if not sourced
if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    main "$@"
fi