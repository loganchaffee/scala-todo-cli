## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

To build and executable install the plugin, build the fat jar file, and give permissions to the shell script wrapper.

```shell
sbt reload
sbt assembly
chmod +x todo
```

Then you can run `./todo`

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
# scala-todo-cli
