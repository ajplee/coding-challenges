import com.fasterxml.jackson.core.JsonParseException
import org.scalatest.{FunSuite, Matchers}

class BooleanExpressionParserTest extends FunSuite with Matchers {

  def testHelper(booleanExpression: BooleanExpression, json: String) = {
    assert(booleanExpression == BooleanExpressionParser.fromJson(json))
    assert(json == BooleanExpressionParser.toJson(booleanExpression))
  }

  test ("parseTrue") {
    val json = """{"type":"True"}"""
    val booleanExpression = True
    testHelper(booleanExpression, json)
  }

  test ("parseFalse") {
    val json = """{"type":"False"}"""
    val booleanExpression = False
    testHelper(booleanExpression, json)
  }

  test ("parseVariable") {
    val json = """{"type":"Variable","symbol":"test"}"""
    val booleanExpression = Variable("test")
    testHelper(booleanExpression, json)
  }

  test ("parseNot") {
    val json = """{"type":"Not","e":{"type":"True"}}"""
    val booleanExpression = Not(True)
    testHelper(booleanExpression, json)
  }

  test ("parseOr") {
    val json = """{"type":"Or","e1":{"type":"True"},"e2":{"type":"False"}}"""
    val booleanExpression = Or(True, False)
    testHelper(booleanExpression, json)
  }

  test ("parseAnd") {
    val json = """{"type":"And","e1":{"type":"True"},"e2":{"type":"False"}}"""
    val booleanExpression = And(True, False)
    testHelper(booleanExpression, json)
  }

  test ("parseComplex") {
    val json = """{"type":"Or","e1":{"type":"Not","e":{"type":"True"}},"e2":{"type":"False"}}"""
    val booleanExpression = Or(Not(True), False)
    testHelper(booleanExpression, json)
  }

  test ("invalidInputs") {
    val invalidInput = "test"
    val invalidType = """{"type":"test"}"""
    val missingParam = """{"type":"And","e1"{"type":"True"}}"""
    val empty = null
    an [BooleanExpressionParserException] should be thrownBy BooleanExpressionParser.fromJson(invalidInput)
    an [BooleanExpressionParserException] should be thrownBy BooleanExpressionParser.fromJson(invalidType)
    an [BooleanExpressionParserException] should be thrownBy BooleanExpressionParser.fromJson(missingParam)
    an [NullPointerException] should be thrownBy BooleanExpressionParser.fromJson(empty)

  }

  test ("testNotTransform") {
    val booleanExpression = Not(Not(True))
    assert(Transforms.simplify(booleanExpression) == True)
  }

  test ("testAndTransform") {
    val booleanExpression = And(Or(True, Variable("a")), Variable("b"))
    assert(Transforms.simplify(booleanExpression) == Variable("b"))
  }

  test ("testComplex") {
    val booleanExpression: BooleanExpression = And(Not(Or(Variable("a"), True)), And(Variable("a"), True))
    assert(Transforms.simplify(booleanExpression) == False)
  }
}
