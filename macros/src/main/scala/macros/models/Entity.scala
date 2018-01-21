package macros.models

import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.reflect.macros.whitebox


/**
  * Entity macro which create specific apply & unapply for table entity removing @Transient fields
  *
  * @author igorzg on 21.01.18.
  * @since 1.0
  */


private object Entity {
  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    import Flag._

    def extractCaseClassesParts(classDef: ClassDef) = classDef match {
      case q"case class $className(..$fields) extends ..$parents { ..$body }" =>
        (className, fields, parents, body)
    }

    def modifiedDeclaration(classDecl: ClassDef): Tree = {
      val (className, fields, parents, body) = extractCaseClassesParts(classDecl)
      val params = fields.asInstanceOf[List[ValDef]] map { p => p.duplicate }

      q"""case class $className ( ..$params ) extends ..$parents {
          ..$body
        }
      """
    }

    val p = c.enclosingPosition

    val inputs = annottees.map(_.tree).toList

    val result: Tree = {
      // Tree manipulation code
      inputs match {
        case (classDef: ClassDef) :: Nil => modifiedDeclaration(classDef)
        case _ => c.abort(c.enclosingPosition, "Invalid annottee")
      }
    }

    // if no errors, return the original syntax tree
    c.Expr[Any](result)
  }
}

class Entity extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Entity.impl
}
