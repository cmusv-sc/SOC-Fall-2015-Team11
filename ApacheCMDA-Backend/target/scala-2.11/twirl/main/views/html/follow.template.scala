
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
object follow extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(message: String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.19*/("""

"""),_display_(/*3.2*/main("Welcome to Play")/*3.25*/ {_display_(Seq[Any](format.raw/*3.27*/("""

"""),format.raw/*5.1*/("""<form action=""""),_display_(/*5.16*/routes/*5.22*/.FollowController.addFollow()),format.raw/*5.51*/("""" method="post">
    follower email:
    <input type="text" name="followerEmail">
    <br>
    followee email:
    <input type="text" name="followeeEmail">
    <br>
    <button>add follow</button>
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
                  DATE: Fri Nov 20 10:48:47 PST 2015
                  SOURCE: /Users/qiuzhexin/Documents/workspace/SOC_final/SOC-Fall-2015/ApacheCMDA-Backend/app/views/follow.scala.html
                  HASH: 416e3b701de5513c57812ae99807ac3337e1d757
                  MATRIX: 724->1|829->18|857->21|888->44|927->46|955->48|996->63|1010->69|1059->98|1296->305
                  LINES: 26->1|29->1|31->3|31->3|31->3|33->5|33->5|33->5|33->5|43->15
                  -- GENERATED --
              */
          