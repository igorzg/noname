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

    val p = c.enclosingPosition

    val inputs = annottees.map(_.tree).toList
    val classDef: ClassDef = inputs match {
      case (classDef: ClassDef) :: Nil => classDef
      case _ => c.abort(p, "Invalid annottee")
    }
    val (mods: Modifiers, tpname, tparams, ctorMods, paramss, earlydefns, parents, self, body) = classDef match {
      case q"$mods class $tpname[..$tparams] $ctorMods(..$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$body }" =>
        (mods, tpname, tparams, ctorMods, paramss, earlydefns, parents, self, body)
    }

    val tupleParams: List[ValDef] = paramss.map(item => item.asInstanceOf[ValDef]).toList

    val applyMap = tupleParams.zipWithIndex.map {
      case (e, i) => ValDef.apply(e.mods, TermName("arg._" + (i + 1)), e.tpt, e.rhs)
    }
    val result: Tree = {
      // Tree manipulation code
      val tree =
        q"""
         @..${mods.annotations}
         case class $tpname[..$tparams] $ctorMods(...$paramss)  extends { ..$earlydefns } with ..$parents {
            ..$body
         }

        $mods object ${TermName(tpname.toString)} extends { ..$earlydefns } with ..$parents {
           def unapply(arg: $tpname): Option[( ..${tupleParams.map(_.tpt)} )] = {
               Some(( ..${tupleParams.map(i => q"""arg.${i.name}""")} ))
           }
           def apply(arg: ( ${tupleParams.map(_.tpt)} )): $tpname = {
               ${TermName(tpname.toString)}(..${applyMap.map(_.name)})
           }
        }
       """
      println(tree)
      tree
    }

    // if no errors, return the original syntax tree
    c.Expr[Any](result)
  }
}

class Entity extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro Entity.impl
}
