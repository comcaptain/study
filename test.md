[alias]
    pr = "!f() { \
        if [ $# -eq 0 ]; then \
            echo \"Usage: git pr <dev-branch-name>\"; \
            return 1; \
        fi; \
        DEV_BRANCH=$1; \
        REVIEW_BRANCH=review-$DEV_BRANCH; \
        echo \"Deleting branch $REVIEW_BRANCH if it exists...\"; \
        git branch -D $REVIEW_BRANCH 2>/dev/null || true; \
        echo \"Fetching latest changes...\"; \
        git fetch; \
        echo \"Creating review branch $REVIEW_BRANCH from latest master...\"; \
        git checkout -b $REVIEW_BRANCH origin/master; \
        echo \"Merging $DEV_BRANCH into $REVIEW_BRANCH with no fast forward...\"; \
        git merge --no-ff $DEV_BRANCH; \
        echo \"Merge complete.\"; \
    }; f"
