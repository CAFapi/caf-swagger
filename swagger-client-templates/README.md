# CAF Swagger Java client template overrides

This repository contains modified versions of the standard Swagger Java client templates to enforce our logging standards.

Note: 

- Due to a regress in Swagger 3.0, all templates have to be provided and not just the overrides. Only `libraries/okhttp-gson
/ApliClient.mustache` has been modified in the 3.x directory. [(Issue tracker)](https://github.com/swagger-api/swagger-codegen/issues/9893)

- Due to an [issue](https://stackoverflow.com/a/46850645/12177456) with GSON incorrectly converting integers to doubles when they are part of a `List<Object>`, the `2.x/JSON.mustache` template has been modified to use a custom type converter when converting a `List<Object>`.
