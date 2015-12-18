
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
object user extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(message: String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.19*/("""

"""),_display_(/*3.2*/main("Welcome to Play")/*3.25*/ {_display_(Seq[Any](format.raw/*3.27*/("""



"""),format.raw/*7.1*/("""<form action=""""),_display_(/*7.16*/routes/*7.22*/.UserController.addUserForTest()),format.raw/*7.54*/("""" enctype="application/json" method="post">
    userName:
    <input type="text" name="userName">
    <br>
    password:
    <input type="text" name="password">
    <br>
    firstName:
    <input type="text" name="firstName">
    <br>
    lastName:
    <input type="text" name="lastName">
    <br>
    middleInitial:
    <input type="text" name="middleInitial">
    <br>
    affiliation:
    <input type="text" name="affiliation">
    <br>
    title:
    <input type="text" name="title">
    <br>
    email:
    <input type="text" name="email">
    <br>
    mailingAddress:
    <input type="text" name="mailingAddress">
    <br>
    phoneNumber:
    <input type="text" name="phoneNumber">
    <br>
    faxNumber:
    <input type="text" name="faxNumber">
    <br>
    researchFields:
    <input type="text" name="researchFields">
    <br>
    <button>add user</button>
</form>

""")))}),format.raw/*47.2*/("""
"""))}
  }

  def render(message:String): play.twirl.api.HtmlFormat.Appendable = apply(message)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (message) => apply(message)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Fri Nov 20 11:36:22 PST 2015
                  SOURCE: /Users/qiuzhexin/Documents/workspace/SOC_final/SOC-Fall-2015/ApacheCMDA-Backend/app/views/user.scala.html
                  HASH: b1dd98eb744ccdd92b3296d4ed6be91beb080ead
                  MATRIX: 722->1|827->18|855->21|886->44|925->46|955->50|996->65|1010->71|1062->103|1970->981
                  LINES: 26->1|29->1|31->3|31->3|31->3|35->7|35->7|35->7|35->7|75->47
                  -- GENERATED --
              */
          