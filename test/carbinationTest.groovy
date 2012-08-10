import org.apache.commons.collections.primitives.ArrayIntList

class CarbinationTest extends GroovyTestCase {

  void testRedDrink() {
    def ints = new ArrayIntList()
    def fizzy = true
    assert fizzy : "Red drink should be fizzy"
  }

}
