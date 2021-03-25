# Contributing

For every encountered bug or suggested improvement please create a new issue on GitHub. In case a branch containing proposed changes is already available please make a pull request and link to it in the aforementioned issue.

## Versioning scheme

Markuply follows the [Semantic Versioning](https://semver.org/).

Each version conforms to the following convention: major.minor.patch.

* major is incremented in case of backwards incompatible changes
* minor is incremented in case of backwards compatible changes introducing new features
* patch is incremented in case of backwards compatible bugfixes

## Branches and releases

Every release must create a corresponding tag.

Major and minor versions are released from the `main` branch.
For each such case a new documentation branch is to be created of the tag. This branch acts as the main one for bugfixes and documentation enhancements. Branch naming convention: `release/x.y` eg. `release/1.1`.

For every code change a new feature branch should be created out of the `main` branch. After code review it will be merged back to `main`.

The only exception being the bugfixes which should stem out of an appropriate release branch. Every bugfix should be merged back to the release branch and to the `main` one if applicable.

Bugfix releases are done from the release branches.
