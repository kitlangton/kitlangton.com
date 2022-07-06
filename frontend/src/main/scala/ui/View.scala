package ui

import animus.SignalOps
import com.raquo.laminar.api.L._
import com.raquo.laminar.api.L
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.html

import scala.language.implicitConversions

object View {

  def empty: View = View(span())

  def text(text: String): View =
    View(div(text))

  def spacer: View =
    View(div(flexGrow(1)))

  def apply(html: => HtmlElement): View =
    new View {
      override def body: HtmlElement = html
    }

  def horizontal(views: View*): View =
    Horizontal(views.toList)

  def horizontal(views: Signal[List[View]]): View =
    HorizontalSignal(views)

  def horizontal(spacing: Int)(views: View*): View =
    Horizontal(views.toList, spacing)

  def vertical(views: View*): View =
    Vertical(views.toList)

  def vertical(views: Signal[List[View]]): View =
    VerticalSignal(views)

  def vertical(spacing: Int)(views: View*): View =
    Vertical(views.toList, spacing)

  def stack(views: View*): View =
    ZStack(views.toList)

  def stack(alignment: Alignment)(views: View*): View =
    ZStack(views.toList, alignment)

  implicit def viewToComponent(view: View): HtmlElement =
    view.body
}

trait View { self =>

  private[ui] def viewId: Int = self.hashCode()

  def id(any: Any): View = new View {
    override def body: HtmlElement = self.body
    override def viewId: Int       = any.hashCode()
  }

  def body: HtmlElement

  def background(color: String): View =
    modified(
      L.backgroundColor(color)
    )

  def background(color: Color): View =
    modified(
      L.backgroundColor(color.css)
    )

  def background(color: Signal[String]): View =
    modified(
      L.backgroundColor <-- color
    )

  def background(color: Signal[Color])(implicit dummy: DummyImplicit): View =
    modified(
      L.backgroundColor <-- color.map(_.css)
    )

  def centerH: View =
    modified(
      display.flex,
      justifyContent.center,
      L.width("100%")
    )

  def centerV: View =
    modified(
      display.flex,
      flexDirection("column"),
      justifyContent.center,
      L.height("100%")
    )

  def center: View =
    modified(
      display.flex,
      justifyContent.center,
      alignItems.center,
      L.width("100%"),
      L.height("100%")
    )

  def padding(px: Int): View =
    modified(
      L.padding(s"${px}px")
    )

  // padding with options for all sides, defaulting to 0 each
  def padding(top: Int = 0, right: Int = 0, bottom: Int = 0, left: Int = 0): View =
    modified(
      L.padding(s"${top}px ${right}px ${bottom}px ${left}px")
    )

  def padding(x: Int, y: Int): View =
    modified(
      L.padding(s"${y}px ${x}px")
    )

  def paddingH(px: Int): View =
    modified(
      L.padding(s"0 ${px}px")
    )

  def paddingV(px: Int): View =
    modified(
      L.padding(s"${px}px 0")
    )

  def padding($px: Signal[Double]): View =
    modified(
      L.padding <-- $px.map(px => s"${px}px")
    )

  def border(px: Int, color: String, radius: Int = 8): View =
    modified(
      L.overflow.hidden,
      L.border(s"${px}px solid ${color}"),
      L.borderRadius(s"${radius}px")
    )

  def width(px: Int): View =
    modified(
      L.width(s"${px}px")
    )

  def color(str: String): View =
    modified(
      L.color(str)
    )

  def color(color: Color): View =
    modified(
      L.color(color.css)
    )

  def blur(px: Int = 12) =
    modified(
      L.customStyle("backdrop-filter")(s"blur(${px}px)"),
      L.customStyle("-webkit-backdrop-filter")(s"blur(${px}px)")
    )

  def rounded(px: Int) =
    amended(
//      L.overflow.hidden,
      L.borderRadius(s"${px}px")
    )

  def rounded: View =
    rounded(8)

  def hover(isHover: Var[Boolean]): View =
    amended(
      L.onMouseEnter.mapToStrict(true) --> isHover,
      L.onMouseLeave.mapToStrict(false) --> isHover
    )

  def fontSize(size: FontSize): View =
    amended(
      L.fontSize(size.size)
    )

  def bold: View =
    amended(
      L.fontWeight.bold
    )

  def medium: View =
    amended(
      L.fontWeight(500)
    )

  def top(value: Int): View =
    modified(
      L.position.relative,
      L.top(s"${value}px")
    )

  def right(value: Int): View =
    modified(
      L.position.relative,
      L.right(s"${value}px")
    )

  def bottom(value: Int): View =
    modified(
      L.position.relative,
      L.bottom(s"${value}px")
    )

  def left(value: Int): View =
    modified(
      L.position.relative,
      L.left(s"${value}px")
    )

  def absolute: View =
    amended(
      L.position.absolute
    )

  def absolute(
    top: Option[Int] = None,
    right: Option[Int] = None,
    bottom: Option[Int] = None,
    left: Option[Int] = None
  ): View =
    amended(
      L.position.absolute,
      top.map(top => L.top(s"${top}px")),
      right.map(right => L.right(s"${right}px")),
      bottom.map(bottom => L.bottom(s"${bottom}px")),
      left.map(left => L.left(s"${left}px"))
    )

  def relative: View =
    modified(
      L.position.relative
    )

  def shadow(shadow: BoxShadow): View =
    modified(
      L.boxShadow(shadow.css)
    )

  def fontMono: View =
    amended(
      cls("font-mono")
    )

  private def modified(mod: Mod[HtmlElement]*): View =
    new View {
      def body =
        div(
          self.body,
          mod
        )
    }

  private def amended(mod: Mod[HtmlElement]*): View =
    new View {
      def body =
        self.body.amend(mod)
    }

}
