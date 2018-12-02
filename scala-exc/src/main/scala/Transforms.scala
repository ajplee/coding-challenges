object Transforms {

  def simplify(booleanExpression: BooleanExpression): BooleanExpression = {
    booleanExpression match {
      case True => True
      case False => False
      case Variable(name) => Variable(name)
      case Not(e) => simplifyNot(Not(simplify(e)))
      case Or(e1, e2) => simplifyOr(Or(simplify(e1), simplify(e2)))
      case And(e1, e2) => simplifyAnd(And(simplify(e1), simplify(e2)))
    }
  }

  def simplifyAnd(and: And) : BooleanExpression = {
    and match {
      case And(True, e) => e
      case And(e, True) => e
      case And(True, True) => True
      case And(False, e) => False
      case And(e, False) => False
      case And(e1, e2) => And(e1, e2)
    }
  }

  def simplifyNot(not: Not) : BooleanExpression = {
    not match {
      case Not(True) => False
      case Not(False) => True
      case Not(e) => Not(e)
    }
  }

  def simplifyOr(or: Or) : BooleanExpression = {
    or match {
      case Or(True, e) => True
      case Or(e, True) => True
      case Or(False, e) => e
      case Or(e, False) => e
      case Or(e1, e2) => Or(e1, e2)
    }
  }
}
