#!/bin/bash

# NewsApp Test Runner Script
# This script runs all unit tests and opens the test report

echo "🧪 Running NewsApp Unit Tests..."
echo "================================"

# Unset JAVA_HOME to let Gradle find compatible Java
unset JAVA_HOME

echo "Gradle will auto-detect Java version"
echo ""

# Clean and run tests
./gradlew clean testDebugUnitTest --continue --warning-mode all

# Check if tests passed
TEST_RESULT=$?

if [ $TEST_RESULT -eq 0 ]; then
    echo ""
    echo "✅ All tests passed!"
    echo ""
    if [ -f "app/build/reports/tests/testDebugUnitTest/index.html" ]; then
        echo "📊 Opening test report..."
        open app/build/reports/tests/testDebugUnitTest/index.html
    fi
else
    echo ""
    echo "⚠️  Build completed with issues. Check the output above."
    echo ""
    if [ -f "app/build/reports/tests/testDebugUnitTest/index.html" ]; then
        echo "📊 Opening test report..."
        open app/build/reports/tests/testDebugUnitTest/index.html
    else
        echo "❌ Test report not generated. Build may have failed before tests ran."
    fi
fi

echo ""
echo "Test report location: app/build/reports/tests/testDebugUnitTest/index.html"
