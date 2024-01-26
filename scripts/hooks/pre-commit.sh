#!/bin/sh

# Store staged changes into a variable
stagedFiles=$(git diff --staged --name-only)

# Run spotless code formatting
echo "Running spotlessApply. Formatting code..."
./gradlew spotlessApply

# store the last exit code in a variable
RESULT=$?

# The above creates unstaged changes, which will need to be added to staging again.
# NB: Only files that were initially staged at step 1 will be committed, not every
# file that could possibly have been altered by spotless.
for file in $stagedFiles; do
  if test -f "$file"; then
    git add $file
  fi
done

exit $RESULT