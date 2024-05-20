```
[alias]
    create-feature-branch = "!f() { \
        if git show-ref --verify --quiet refs/heads/feature/$1; then \
            echo 'Error: Branch feature/$1 already exists.'; \
            exit 1; \
        fi; \
        git fetch origin master:master && \
        git checkout -b feature/$1 master; \
    }; f"
```

git create-feature-branch JIRA-123
