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
case class Entity() extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Entity.impl
}

object Entity {
  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    def extractCaseClassesParts(classDecl: ClassDef) = classDecl match {
      case q"case class $className(..$fields) extends ..$parents { ..$body }" =>
        (className, fields, parents, body)
    }


    def modifiedDeclaration(classDecl: ClassDef) = {
      val (className, fields, parents, body) = extractCaseClassesParts(classDecl)

      val params = fields.asInstanceOf[List[ValDef]] map { p => p.duplicate }

      c.Expr[Any](
        q"""
        case class $className ( ..$params ) extends ..$parents {
          ..$body
        }
      """
      )
    }

    annottees map (_.tree) toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee")
    }
  }
}