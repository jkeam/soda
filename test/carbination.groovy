import org.apache.commons.collections.primitives.ArrayIntList
import org.mortbay.jetty.*;
import org.mortbay.jetty.servlet.*
import groovy.servlet.*

//tests the arrayIntList depedency
def intTest() {
  def ints = new ArrayIntList()
  ints.add(0, 42)
  assert ints.size() == 1
  assert ints.get(0) == 42

  println 'ArrayIntList dependency test finished with no errors.'
}

//tests the jetty dependency
def startServer(duration) {
  def server = new Server(8080)
  def context = new Context(server, "/", Context.SESSIONS);
  context.resourceBase = "."
  context.addServlet(TemplateServlet, "*.gsp")
  server.start()
  sleep duration
  server.stop()
  
  println 'Server test successful; server successfully started and stopped.'
}

println 'Test 1'
println '---------------------------'
intTest()
println ''
println 'Test 2'
println '---------------------------'
startServer(3000)
