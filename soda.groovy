#!/usr/bin/env groovy

import java.text.*
import groovy.json.JsonSlurper
import groovy.grape.Grape

def showdate(args) {
  def help = "Define your dependencies in a file named package.json and your invoked file will have those dependencies included."
  def cli = new CliBuilder(usage: 'soda.groovy')
  cli.with {
    h longOpt: 'help', 'Show usage'
    f longOpt: 'file', args:1, argName:'filename', 'Select file to run'
  }

  def options = cli.parse(args)
  if (!options || options.h) {
    println help
    cli.usage()
  }
  else if(options.f) {
    process(options.f)
  }
  else {
    println help
    cli.usage()
  }

}

def process(script) {
  def dir = System.getProperty("user.dir")
  try {
    def contents = ""
    new File(dir + '/package.json').eachLine { line ->
      contents += line
    }
    
    def result = new JsonSlurper().parseText contents
    result.dependencies.each { it ->
      Grape.grab([group:it.group, module:it.id, version:it.version])
    }

    //call script
    evaluate(new File(script)) 

  } catch(Exception e) {
    println "Error opening file, please make sure package.json exists and is well-formed."
    println e
  }
}

//main
showdate(args)

