#!/usr/bin/env bash
# One-command builder for this repository.
#
# Usage:
#   ./build.sh [target] [extra Gradle args...]
#
# Examples:
#   ./build.sh                  # build the HTML/GWT output
#   ./build.sh html             # build the HTML/GWT output
#   ./build.sh html-demo        # build the minimal HTML5 demo
#   ./build.sh clean html       # clean, then build HTML
#   ./build.sh html --offline   # pass extra args through to Gradle

set -Eeuo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
BOLD='\033[1m'
NC='\033[0m'

info() { printf "${BLUE}[INFO]${NC} %s\n" "$*"; }
success() { printf "${GREEN}[OK]${NC} %s\n" "$*"; }
warn() { printf "${YELLOW}[WARN]${NC} %s\n" "$*"; }
fail() { printf "${RED}[ERROR]${NC} %s\n" "$*" >&2; exit 1; }

usage() {
    cat <<'EOF'
Build helper for Mindustry.

Usage:
  ./build.sh [target] [extra Gradle args...]

Default target: html

Targets:
  html          Build the HTML/GWT output (default)
  desktop       Build the playable desktop JAR
  server        Build the server JAR
  test          Run the test suite
  run           Run the desktop game from source
  pack-sprites  Generate/pack sprites
  android       Build Android debug APK (requires ANDROID_HOME/local.properties)
  html-demo     Build the minimal HTML5 demo
  teavm         Build the TeaVM output
  all           Build desktop + server and run tests
  clean         Clean Gradle outputs, then continue with the next target if given
  help          Show this help

Examples:
  ./build.sh
  ./build.sh html
  ./build.sh clean html
  ./build.sh html --stacktrace
  ./build.sh html --offline

Artifacts:
  Desktop JAR: desktop/build/libs/Mindustry.jar
  Server JAR:  server/build/libs/server-release.jar
  Android APK: android/build/outputs/apk/debug/
  HTML output: html/build/war/
  TeaVM output: teaVM/build/teavm/
EOF
}

require_java_17() {
    command -v java >/dev/null 2>&1 || fail "Java was not found. Install JDK 17 or newer first."

    local version_line version major
    version_line="$(java -version 2>&1 | head -n 1)"
    version="$(printf '%s\n' "$version_line" | sed -E 's/.*version "([^"]+)".*/\1/')"

    if [[ "$version" == 1.* ]]; then
        major="$(printf '%s' "$version" | cut -d. -f2)"
    else
        major="$(printf '%s' "$version" | cut -d. -f1)"
    fi

    [[ "$major" =~ ^[0-9]+$ ]] || fail "Could not determine Java version from: $version_line"
    (( major >= 17 )) || fail "JDK 17+ is required; found Java $version."

    success "Java $version detected"
}

select_gradle_wrapper() {
    if [[ -x ./gradlew ]]; then
        GRADLE=("./gradlew")
    elif [[ -f ./gradlew ]]; then
        chmod +x ./gradlew
        GRADLE=("./gradlew")
    elif [[ -f ./gradlew.bat ]]; then
        GRADLE=("./gradlew.bat")
    else
        fail "Gradle wrapper not found in $ROOT_DIR"
    fi
}

run_gradle() {
    local log_name="$1"
    shift

    mkdir -p build/logs
    local log_file="build/logs/${log_name}.log"

    info "Running: ${GRADLE[*]} $*"
    info "Log: $log_file"

    set +e
    "${GRADLE[@]}" "$@" --no-daemon 2>&1 | tee "$log_file"
    local status=${PIPESTATUS[0]}
    set -e

    if (( status != 0 )); then
        fail "Build failed. See $log_file"
    fi
}

print_outputs() {
    echo
    success "Finished successfully."

    [[ -f desktop/build/libs/Mindustry.jar ]] && printf "  Desktop JAR: %s\n" "desktop/build/libs/Mindustry.jar"
    [[ -f server/build/libs/server-release.jar ]] && printf "  Server JAR:  %s\n" "server/build/libs/server-release.jar"

    if compgen -G "android/build/outputs/apk/debug/*.apk" >/dev/null; then
        printf "  Android APK: %s\n" "android/build/outputs/apk/debug/"
    fi
    [[ -d html/build/war ]] && printf "  HTML output: %s\n" "html/build/war/"
    [[ -d html/build/war-demo ]] && printf "  HTML demo:   %s\n" "html/build/war-demo/"
    [[ -d teaVM/build/teavm ]] && printf "  TeaVM output:%s\n" " teaVM/build/teavm/"
}

if [[ ${1:-} == "help" || ${1:-} == "--help" || ${1:-} == "-h" ]]; then
    usage
    exit 0
fi

echo -e "${BOLD}Mindustry build helper${NC}"
require_java_17
select_gradle_wrapper

TARGET="${1:-html}"
if [[ $# -gt 0 ]]; then
    shift
fi

# Allow './build.sh clean desktop' as a convenient sequence.
if [[ "$TARGET" == "clean" ]]; then
    run_gradle clean clean

    # If another target follows, build it. If only Gradle flags follow, use the
    # default HTML target and pass those flags through.
    if [[ $# -eq 0 ]]; then
        print_outputs
        exit 0
    elif [[ "${1:-}" == -* ]]; then
        TARGET="html"
    else
        TARGET="$1"
        shift
    fi
fi

EXTRA_ARGS=("$@")

case "$TARGET" in
    desktop)
        run_gradle desktop-dist :desktop:dist "${EXTRA_ARGS[@]}"
        ;;
    server)
        run_gradle server-dist :server:dist "${EXTRA_ARGS[@]}"
        ;;
    test|tests)
        run_gradle tests :tests:test "${EXTRA_ARGS[@]}"
        ;;
    run)
        run_gradle desktop-run :desktop:run "${EXTRA_ARGS[@]}"
        ;;
    pack-sprites|sprites|pack)
        run_gradle pack-sprites :tools:pack "${EXTRA_ARGS[@]}"
        ;;
    android|apk)
        if [[ -z "${ANDROID_HOME:-}" && ! -f local.properties ]]; then
            fail "Android build requires ANDROID_HOME or local.properties with sdk.dir."
        fi
        run_gradle android-debug :android:assembleDebug "${EXTRA_ARGS[@]}"
        ;;
    html|gwt)
        run_gradle html :html:compileGwt "${EXTRA_ARGS[@]}"
        ;;
    html-demo|demo)
        run_gradle html-demo :html:compileDemo "${EXTRA_ARGS[@]}"
        ;;
    teavm|wasm)
        run_gradle teavm :teaVM:teavmBuild "${EXTRA_ARGS[@]}"
        ;;
    all)
        run_gradle all :desktop:dist :server:dist :tests:test "${EXTRA_ARGS[@]}"
        ;;
    *)
        usage
        fail "Unknown target: $TARGET"
        ;;
esac

print_outputs
