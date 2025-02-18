## Scala Todo CLI

A simple command line application for keeping track of todos.

### Usage

To build an executable install the plugin, build the Fat jar file, and give permissions to the shell script wrapper.

```shell
sbt reload
sbt assembly
chmod +x todo
```

Then you can run `./todo` from the project directory. eg. `./todo add "Wash Car"`

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.
