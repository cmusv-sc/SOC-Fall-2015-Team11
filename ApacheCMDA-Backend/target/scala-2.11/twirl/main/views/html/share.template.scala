
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._

import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._

/**/
object share extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(message: String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.19*/("""

"""),_display_(/*3.2*/main("Welcome to Play")/*3.25*/ {_display_(Seq[Any](format.raw/*3.27*/("""

    """),format.raw/*5.5*/("""<form action=""""),_display_(/*5.20*/routes/*5.26*/.ShareController.addSharePost()),format.raw/*5.57*/("""" method="post">
        Sharer email:
        <input type="text" name="sharerEmail">
        <br>
        Post id:
        <input type="text" name="postId">
        <br>
        <button>add share</button>
    </form>

""")))}),format.raw/*15.2*/("""
"""))}
  }

  def render(message:String): play.twirl.api.HtmlFormat.Appendable = apply(message)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (message) => apply(message)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Mon Nov 16 01:36:08 PST 2015
                  SOURCE: /Users/qiuzhexin/Documents/workspace/SOC_final/SOC-Fall-2015/ApacheCMDA-Backend/app/views/share.scala.html
                  HASH: 2243320231f209623f343f6fc918531bc40a0273
                  MATRIX: 723->1|828->18|856->21|887->44|926->46|958->52|999->67|1013->73|1064->104|1314->324
                  LINES: 26->1|29->1|31->3|31->3|31->3|33->5|33->5|33->5|33->5|43->15
                  -- GENERATED --
              */
          