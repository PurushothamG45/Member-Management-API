# Sample Git workflow & example commits

This file documents a recommended git workflow and sample commands you can run locally
to create a similar history to what was requested in the assignment.

Example commands:

```bash
git init
git add .
git commit -m "chore: initial project skeleton with Gradle and dependencies"

git checkout -b feature/flyway-schema
# add flyway SQLs
git add src/main/resources/db/migration
git commit -m "feat(db): add Flyway migrations for role/user/member"

git checkout -b feature/security-jwt
# add security, jwt util, filters
git add src/main/java/com/example/surest/security
git commit -m "feat(auth): add JWT auth, filter, and login endpoint"

git checkout -b feature/members-crud
# add member controller, service, mapper
git add src/main/java/com/example/surest/controller src/main/java/com/example/surest/service src/main/java/com/example/surest/mapper
git commit -m "feat(members): add CRUD endpoints, service and MapStruct mapper"

git checkout main
git merge --no-ff feature/flyway-schema -m "merge: add DB schema via Flyway"
git merge --no-ff feature/security-jwt -m "merge: add JWT security"
git merge --no-ff feature/members-crud -m "merge: implement members CRUD and mapping"

# create a tag
git tag -a v0.1 -m "Initial working version"
```

Example `git log --oneline --graph` output (sample):
```
* abcdef1 (HEAD -> main, tag: v0.1) merge: implement members CRUD and mapping
* 2345abc merge: add JWT security
* 9876def merge: add DB schema via Flyway
* 1234567 chore: initial project skeleton with Gradle and dependencies
```
