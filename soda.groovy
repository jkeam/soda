#!/usr/bin/env groovy

import java.text.*
import groovy.json.JsonSlurper
import groovy.grape.Grape

def prompt(args) {
  def help = "Define your dependencies in a file named package.json and your invoked file will have those dependencies included."
  def cli = new CliBuilder(usage: 'soda.groovy')
  cli.with {
    h longOpt: 'help', 'Show usage'
    e longOpt: 'exec', args:1, argName:'executable', 'Select executable to run'
    t longOpt: 'test', args:1, argName:'testfile', 'Select testfile to run'
  }

  def options = cli.parse(args)
  if (!options || options.h) {
    println help
    cli.usage()
  }
  else if (options.e) {
    process(options.e)
  }
  else if (options.t) {
     processTest(options.t)
  }
  else {
    println help
    cli.usage()
  }

}

def load() {
  def dir = System.getProperty("user.dir")
  try {
    def contents = ""
    new File(dir + '/package.json').eachLine { line ->
      contents += line
    }
    
    def result = new JsonSlurper().parseText contents
    result.dependencies.each { it ->
      def group = it.group
      if (group == null) {
        group = it.groupId
        if (group == null) {
          group = it.org
        }
      }

      def id = it.id
      if (id == null) {
        id = it.module
        if (id == null) {
          id = it.artifactId
          if (id == null) {
            id = it.name
          }
        }
      }
     
      def version = it.version
      if (version == null) {
        version == it.rev
      }
      Grape.grab([group:group, module:id, version:version])
    }


  } catch(Exception e) {
    println "Error opening file, please make sure package.json exists and is well-formed."
    println e
  }
}

def processTest(script) {
  load()
  def loader = new GroovyClassLoader(getClass().getClassLoader())
  def testClass = loader.parseClass(new File(script))
  println testClass
  junit.textui.TestRunner.run(testClass)
}

def process(script) {
  load()
  evaluate(new File(script)) 
}

//main
prompt(args)

