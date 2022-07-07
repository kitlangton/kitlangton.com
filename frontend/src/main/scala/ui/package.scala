import com.raquo.laminar.api.L._

import scala.language.implicitConversions

package object ui {
  implicit def string2View(string: String): View = new View {
    override def body: HtmlElement = View.text(string)
  }

  implicit def stringSignal2View($string: Signal[String]): View = new View {
    override def body: HtmlElement = View(div(child.text <-- $string))
  }
}
