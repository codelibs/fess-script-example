# Fess Script Example Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.codelibs.fess/fess-script-example/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.codelibs.fess/fess-script-example)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A simple example plugin demonstrating how to create custom script engines for [Fess](https://fess.codelibs.org/), the open-source enterprise search server.

## What Is a Script Engine?

A Fess script engine turns a **template** plus a **parameter map** into a value. Fess uses script
engines wherever an administrator can enter a small script or expression — for example to compute a
document boost, derive a field value during crawling, or run logic in a scheduled job. The built-in
`groovy` engine evaluates full Groovy scripts; this example shows the minimal shape of a custom one.

## Overview

This plugin provides a minimal implementation of a custom script engine for Fess. The `ExampleEngine`
serves as a template and starting point for developers who want to create their own script engines
with custom template processing logic.

### Key Features

- **Real, Minimal Evaluation**: Performs simple `${key}` placeholder substitution from the parameter map
- **Idiomatic Structure**: Demonstrates the standard structure of a Fess script engine
- **Self-Registration**: Registers itself with the script engine factory via the DI container
- **Focused Tests**: Meaningful tests covering substitution, missing keys, null/blank input, and factory lookup

## Architecture

The plugin extends Fess's `AbstractScriptEngine` class and implements:

- **Template Evaluation** (`evaluate`): Substitutes `${key}` placeholders with values from the parameter map.
  A blank template returns `null`; a missing or `null` value leaves the placeholder untouched.
- **Engine Identification** (`getName`): Provides the unique name (`"example"`) used to register and look up the engine
- **DI Integration**: `fess_se++.xml` registers the engine into Fess's `scriptEngineFactory` at startup
  via a `register` postConstruct (the `++` suffix means the fragment is additively merged into the core `fess_se.xml`)

## Installation

### Prerequisites

- Fess 15.7.0 or later
- Java 21 or later

### Download

You can download the plugin JAR from [Maven Central](https://repo1.maven.org/maven2/org/codelibs/fess/fess-script-example/).

### Plugin Installation

1. Download the latest `fess-script-example-{version}.jar` from the releases
2. Copy the JAR file to your Fess plugin directory (`$FESS_HOME/app/WEB-INF/plugin/`)
3. Restart Fess server
4. The "example" script engine will be available for use

For detailed installation instructions, see the [Fess Plugin Guide](https://fess.codelibs.org/15.7/admin/plugin-guide.html).

## Usage

There is **no extra configuration to "use" the engine** — the plugin self-registers via
`fess_se++.xml` when Fess starts. Once installed, the engine is available by its registered name,
`example`, anywhere Fess lets you pick a script type, including:

- **Data store crawling** — field-mapping scripts that compute index field values
- **Document boost** — boost expressions evaluated per document during crawling
- **Scheduled jobs** — jobs whose *Script Type* is set to an engine name
- **Path mappings / replacements** — value transformations that accept a script type

In those places, select or enter `example` as the script type and provide a template such as
`Hello ${name}`. Internally Fess resolves the engine through the factory:

```java
ComponentUtil.getScriptEngineFactory().getScriptEngine("example").evaluate(template, paramMap);
```

This example engine substitutes `${key}` placeholders with values from the parameter map, which
makes it a useful starting point for learning the API and for building richer engines.

## Development

### Building from Source

```bash
git clone https://github.com/codelibs/fess-script-example.git
cd fess-script-example
mvn clean package
```

### Running Tests

```bash
mvn test
```

The test suite includes focused test cases covering:
- Placeholder substitution (single, multiple, and non-string values)
- Missing-key and null-value behavior (placeholder left untouched)
- Blank/null template handling (returns `null`)
- Engine name and factory registration/lookup by name

### Code Quality

```bash
# Format code
mvn formatter:format

# Check license headers
mvn license:check

# Generate Javadoc
mvn javadoc:javadoc
```

## Project Structure

```
src/
├── main/java/
│   └── org/codelibs/fess/script/example/
│       └── ExampleEngine.java          # Script engine implementation (${key} substitution)
├── main/resources/
│   └── fess_se++.xml                    # DI fragment that registers the engine (additive merge)
└── test/
    ├── java/
    │   └── org/codelibs/fess/script/example/
    │       ├── ExampleEngineTest.java   # Engine tests + factory lookup
    │       └── UnitScriptTestCase.java  # Minimal UTFlute test base for the plugin
    └── resources/
        └── test_app.xml                 # Test DI container (includes scriptEngineFactory)
```

## Creating Your Own Script Engine

This project serves as a template for creating custom script engines. To create your own:

1. Fork this repository
2. Rename the `ExampleEngine` class
3. Implement your custom template processing logic in the `evaluate` method
4. Update the `getName` method to return your engine's unique identifier
5. Update the DI configuration in `fess_se++.xml`
6. Add appropriate tests

## Dependencies

- **Fess Framework**: Core search server functionality (provided scope)
- **LastaFlute**: Web application framework with DI container
- **DBFlute**: Database access framework
- **OpenSearch**: Search engine integration (provided scope)
- **UTFlute**: Testing framework for LastaFlute applications

## Contributing

We welcome contributions! Please feel free to submit issues, feature requests, or pull requests.

### Development Guidelines

- Follow the existing code style and formatting
- Add comprehensive tests for new functionality
- Update documentation as needed
- Ensure all tests pass before submitting PRs

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Support

- **Documentation**: [Fess Official Documentation](https://fess.codelibs.org/)
- **Issues**: [GitHub Issues](https://github.com/codelibs/fess-script-example/issues)
- **Community**: [Fess Community Forum](https://discuss.codelibs.org/)

## Related Projects

- [Fess](https://github.com/codelibs/fess) - The main Fess search server
- [Fess Plugins](https://github.com/codelibs?q=fess-) - Other Fess plugins

---

Developed by [CodeLibs Project](https://github.com/codelibs)
