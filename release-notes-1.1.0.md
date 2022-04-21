!not-ready-for-release!

#### Version Number
${version-number}

#### New Features
396454: Due to an [issue](https://stackoverflow.com/a/46850645/12177456) with GSON incorrectly converting integers to doubles when they are part of a `List<Object>`, the `2.x/JSON.mustache` template has been modified to use a custom type converter when converting a `List<Object>`.

#### Known Issues
