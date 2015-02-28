# Soda
This is sorta like Bundler for Ruby.  Groovy has a neat dependency management tool called called Grape, but I didn't like how the dependency information was all jumbled up in with the source.  So I took the naming convention of node's package.json file and Bundlers bundle exec to create soda.

## Installation
Simply clone this repo.


## Usage
Go ahead and run ./soda (assuming groovy and grape are in your path) with no args to see the help.

To run executables:

1.  cd to where your groovy script is (the one you want the dependencies to be included with), lets call it carbination.groovy
2.  create a package.json with your dependencies
3.  run soda -e ./carbination.groovy 

*Doesn't matter where soda is as long as you can see and run it, but you must be in the same dir as your file (carbination.groovy) and your package.json

To run tests:
If instead you have tests that you want to run (tests that extend GroovyTestCase), run it with the -t option.
soda -t ./tests.groovy


## Examples
###Executable
1.  cd to test
2.  ../soda -e ./carbination.groovy

###Test
1.  cd to test
2.  ../soda -t ./carbinationTest.groovy


## package.json
This is where the dependencies are defined.  The file contains a single JSON object with a single attribute, "dependencies", which is an array of objects.  These objects all contain three attributes: group, id, and version.  You can either use maven, ivy, grape or a mix of these to specify these attributes.

Below are the three attributes along with all of the valid values.  The list for each attribute is ordered in terms of their preference.  So the first term (for example, group's first preferred term is 'group') is the most preferred.

**Group:** 
  group, groupId, org

**Id:**
  id, module, artifactId, name

**Version:**
  version, rev

For an example, look at test/package.json

## Issues
1.  Not tested and most likely won't work correctly on a Windows machine.
