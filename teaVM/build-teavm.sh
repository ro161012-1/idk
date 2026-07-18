#!/bin/bash
# Mindustry TeaVM Build Script Wrapper
# Delegates to the main build-teavm.sh in the repository root

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$ROOT_DIR"
exec ./build-teavm.sh "$@"
