package macros.models

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.blackbox
import annotations.IgnoreOnSlackQuery

/**
  * Entity macro which create specific apply & unapply for table entity removing @Transient fields
  *
  * @author igorzg on 21.01.18.
  * @since 1.0
  */
private object Entity {

  def impl(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val p = c.enclosingPosition

    val inputs = annottees.map(_.tree).toList
    val classDef: ClassDef = inputs match {
      case (classDef: ClassDef) :: Nil => classDef
      case _ => c.abort(p, "Annotation can be apply only on class")
    }

    val (mods: Modifiers,
    tpname: TypeName,
    tparams,
    ctorMods,
    paramss: List[ValDef],
    earlydefns,
    parents,
    self,
    body) = classDef match {
      case q"$mods class $tpname[..$tparams] $ctorMods(..$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$body }" =>
        (mods,
          tpname,
          tparams,
          ctorMods,
          paramss.map(item => item.asInstanceOf[ValDef]).toList,
          earlydefns,
          parents,
          self,
          body)
    }

    val filterParams = paramss.filter(item => {
      val isTransient = item.mods.annotations.find {
        case tq"$tpname" => tpname.equalsStructure(q"new ${TypeName(IgnoreOnSlackQuery.toString())}")
      }
      isTransient.isEmpty
    })
    val applyArgs = filterParams.zipWithIndex.map {
      case (e, i) => Select(Ident(TermName("arg")), TermName("_"+(i + 1)))
    }
    val result: Tree =
      q"""
          @..${mods.annotations}
         case class $tpname[..$tparams] $ctorMods(..$paramss)  extends { ..$earlydefns } with ..$parents{}

         $mods object ${tpname.toTermName} extends { ..$earlydefns } with ..$parents {
            $self => ..$body

            def tableApply(arg: (..${filterParams.map(_.tpt)})): $tpname = {
               ${tpname.toTermName}(..$applyArgs)
            }

            def tableUnapply(user: $tpname): Option[(..${filterParams.map(_.tpt)})] = {
               Some((..${filterParams.map(i => q"user.${i.name}")}))
            }
        }
       """
    // if no errors, return the original syntax tree
    c.Expr[Any](result)
  }
}

@compileTimeOnly(
  "Cannot initialize Entity, @Entity is annotation for compiler usage only on case class")
class Entity extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Entity.impl
}
