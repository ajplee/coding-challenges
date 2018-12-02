import com.fasterxml.jackson.core.JsonParseException
import play.api.libs.json._

object BooleanExpressionParser {

  def fromJson(json: String): BooleanExpression = {
    try {
      val parsed = Json.parse(json)
      createBooleanExpression(parsed)
    }
    catch {
      case _: JsonParseException => throw BooleanExpressionParserException("Invalid Json")
    }
  }

  def createBooleanExpression(json: JsValue): BooleanExpression = {
    try {
      (json \ "type").get match {
        case JsString("True") => True
        case JsString("False") => False
        case JsString("Variable") => Variable((json \ "symbol").get.as[String])
        case JsString("Not") => Not(createBooleanExpression((json \ "e").get))
        case JsString("Or") => Or(createBooleanExpression((json \ "e1").get), createBooleanExpression((json \ "e2").get))
        case JsString("And") => And(createBooleanExpression((json \ "e1").get), createBooleanExpression((json \ "e2").get))
        case _ => throw BooleanExpressionParserException("Invalid Boolean Expression")
      }
    }
    catch {
      case e: NoSuchElementException => throw BooleanExpressionParserException("Invalid Arguments")
    }
  }

  def toJson(booleanExpression: BooleanExpression): String = {
    buildJson(booleanExpression).toString()
  }

  def buildJson(booleanExpression: BooleanExpression): JsObject = booleanExpression match {
    case True => JsObject(Seq(
      "type" -> JsString("True")
    ))
    case False => JsObject(Seq(
      "type" -> JsString("False")
    ))
    case Variable(symbol) => JsObject(Seq(
      "type" -> JsString("Variable"),
      "symbol" -> JsString(symbol)
    ))
    case Not(e) => JsObject(Seq(
      "type" -> JsString("Not"),
      "e" -> buildJson(e)
    ))
    case Or(e1, e2) => JsObject(Seq(
      "type" -> JsString("Or"),
      "e1" -> buildJson(e1),
      "e2" -> buildJson(e2)
    ))
    case And(e1, e2) => JsObject(Seq(
      "type" -> JsString("And"),
      "e1" -> buildJson(e1),
      "e2" -> buildJson(e2)
    ))
  }
}
