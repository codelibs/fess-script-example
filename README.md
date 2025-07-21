# Fess Script Example Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.codelibs.fess/fess-script-example/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.codelibs.fess/fess-script-example)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A simple example plugin demonstrating how to create custom script engines for [Fess](https://fess.codelibs.org/), the open-source enterprise search server.

## Overview

This plugin provides a minimal implementation of a custom script engine for Fess. The `ExampleEngine` serves as a template and starting point for developers who want to create their own script engines with custom template processing logic.

### Key Features

- **Simple Implementation**: Demonstrates the basic structure of a Fess script engine
- **Template Pass-through**: Returns template strings unchanged (useful for testing and learning)
- **Full Integration**: Properly integrated with Fess's dependency injection container
- **Comprehensive Tests**: Includes extensive test cases covering edge cases and various scenarios

## Architecture

The plugin extends Fess's `AbstractScriptEngine` class and implements:

- **Template Evaluation**: Process template strings with parameter maps
- **Engine Identification**: Provides a unique name ("example") for the script engine
- **DI Integration**: Configured via LastaDi container for seamless Fess integration

## Installation

### Prerequisites

- Fess 15.0.0 or later
- Java 21 or later

### Download

You can download the plugin JAR from [Maven Central](https://repo1.maven.org/maven2/org/codelibs/fess/fess-script-example/).

### Plugin Installation

1. Download the latest `fess-script-example-{version}.jar` from the releases
2. Copy the JAR file to your Fess plugin directory (`$FESS_HOME/app/WEB-INF/plugin/`)
3. Restart Fess server
4. The "example" script engine will be available for use

For detailed installation instructions, see the [Fess Plugin Guide](https://fess.codelibs.org/15.0/admin/plugin-guide.html).

## Usage

Once installed, you can use the "example" script engine in your Fess configuration:

```xml
<component name="exampleScriptEngine" class="org.codelibs.fess.script.example.ExampleEngine"/>
```

The engine will process templates by returning them unchanged, making it useful for:
- Testing script engine integration
- Learning how to implement custom script engines
- As a starting point for more complex implementations

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

The test suite includes 19 comprehensive test cases covering:
- Basic functionality
- Edge cases (null/empty inputs)
- Various data types and special characters
- Multi-line templates and large content
- Instance independence and data integrity

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
│       └── ExampleEngine.java          # Main script engine implementation
├── main/resources/
│   └── fess_se++.xml                    # DI container configuration
└── test/java/
    └── org/codelibs/fess/script/example/
        └── ExampleEngineTest.java       # Comprehensive test suite
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
