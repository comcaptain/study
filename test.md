```
[alias]
    create-feature-branch = "!f() { \
        branch_name=feature/abc-$1; \
        if git show-ref --verify --quiet refs/heads/$branch_name; then \
            echo \"Error: Branch $branch_name already exists.\"; \
            exit 1; \
        fi; \
        echo \"Checking out master branch...\"; \
        git checkout master && \
        echo \"Pulling latest changes from master...\"; \
        git pull origin master && \
        echo \"Creating new branch $branch_name from master...\"; \
        git checkout -b $branch_name master && \
        echo \"Switched to new branch $branch_name.\"; \
    }; f"
```

git create-feature-branch JIRA-123
