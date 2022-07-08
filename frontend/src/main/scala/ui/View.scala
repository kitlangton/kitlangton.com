package ui

import animus.SignalOps
import com.raquo.laminar.api.L._
import com.raquo.laminar.api.L
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.html

import scala.language.implicitConversions

object View {

  def separatorH(height: Int, color: Color): View =
    View(
      div(
        L.height(s"${height}px"),
        L.background(color.css),
        cls("w-full")
      )
    )

  def separatorV(width: Int, color: Color): View =
    View(
      div(
        L.width(s"${width}px"),
        L.background(color.css),
        cls("h-full")
      )
    )

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

  def vertical(spacing: Int, alignment: HorizontalAlignment = HorizontalAlignment.Left)(views: View*): View =
    Vertical(views.toList, spacing, alignment)

  def stack(views: View*): View =
    ZStack(views.toList)

  def stack(alignment: Alignment)(views: View*): View =
    ZStack(views.toList, alignment)

  def box(
    content: View,
    padding: Int,
    background: Color,
    border: Color,
    radius: Int = 8
  ) =
    content
      .padding(padding)
      .heightFull
      .background(background)
      .border(1, border.css, radius)

  def rect(width: Int = 0, height: Int = 0): View =
    View(
      div(
        L.width(s"${width}px"),
        L.height(s"${height}px")
      )
    )

  def circle(diameter: Int): View =
    View(
      div(
        L.width(s"${diameter}px"),
        L.height(s"${diameter}px"),
        L.borderRadius(s"${diameter}px")
      )
    )

  def width(px: Int) =
    View(div(L.width(s"${px}px")))

  def grid(cols: Int, gap: Int = 0)(views: View*): View =
    View(
      div(
        cls("grid"),
        views.toList,
        L.customStyle("grid-template-columns")(s"repeat($cols, minmax(0,1fr))"),
        L.customStyle("grid-gap")(s"${gap}px")
      )
    )

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
    amended(
      L.backgroundColor(color)
    )

  def background(color: Color): View =
    amended(
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

  def left: View =
    modified(
      display.flex,
      L.width("100%"),
      L.height("100%"),
      justifyContent.flexStart,
      alignItems.center
    )

  def margin(px: Int): View =
    modified(
      L.margin(s"${px}px")
    )

  def marginH(px: Int): View =
    amended(
      L.marginRight(s"${px}px"),
      L.marginLeft(s"${px}px")
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

  def paddingTop(px: Int): View =
    modified(
      L.paddingTop(s"${px}px")
    )

  def padding($px: Signal[Double]): View =
    modified(
      L.padding <-- $px.map(px => s"${px}px")
    )

  def border(px: Int, color: String, radius: Int): View =
    modified(
      L.overflow.hidden,
      L.border(s"${px}px solid $color"),
      L.borderRadius(s"${radius}px")
    )

  def border(top: Int, right: Int, bottom: Int, left: Int, color: String, radius: Int): View =
    modified(
      L.overflow.hidden,
      L.borderTop(s"${top}px solid $color"),
      L.borderRight(s"${right}px solid $color"),
      L.borderBottom(s"${bottom}px solid $color"),
      L.borderLeft(s"${left}px solid $color"),
      L.borderRadius(s"${radius}px")
    )

  def borderLeft(i: Int, color: Color): View =
    amended(
      L.borderLeft(s"${i}px solid ${color.css}")
    )

  def borderRight(i: Int, color: Color): View =
    amended(
      L.borderRight(s"${i}px solid ${color.css}")
    )

  def borderTop(i: Int, color: Color): View =
    amended(
      L.borderTop(s"${i}px solid ${color.css}")
    )

  def borderBottom(i: Int, color: Color): View =
    amended(
      L.borderBottom(s"${i}px solid ${color.css}")
    )

  def width(px: Int): View =
    modified(
      L.width(s"${px}px")
    )

  def height(px: Int): View =
    modified(
      L.height(s"${px}px")
    )

  def heightFull =
    amended(
      L.height("100%")
    )

  def widthFull =
    modified(
      L.width("100%")
    )

  def height(px: Signal[Double]): View =
    modified(
      L.height <-- px.px
    )

  def minHeight(spring: Signal[Double]) =
    modified(
      L.minHeight <-- spring.px
    )

  def maxHeight(spring: Signal[Double]) =
    modified(
      L.maxHeight <-- spring.px
    )

  def color(str: String): View =
    modified(
      L.color(str)
    )

  def color(color: Color): View =
    amended(
      L.color(color.css)
    )

  def colorHover(color0: Color): View =
    View {
      val hovering = new EventBus[Boolean]

      val css    = color0.css
      val $color = hovering.events.map(if (_) css else "inherit")

      self.modified(
        L.color <-- $color,
        L.onMouseEnter.mapTo(true) --> hovering,
        L.onMouseLeave.mapTo(false) --> hovering
      )
    }

  def cursorPointer: View =
    amended(L.cursor.pointer)

  def onClick(expr: => Unit): View =
    amended(
      L.onClick --> { _ => expr }
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

  def fontSmall: View =
    fontSize(FontSize.sm)

  def fontXSmall: View =
    fontSize(FontSize.xs)

  def fontMedium: View =
    fontSize(FontSize.base)

  def fontLarge: View =
    fontSize(FontSize.lg)

  def fontXLarge: View =
    fontSize(FontSize.xl)

  def whitespaceNoWrap: View =
    amended(L.whiteSpace.nowrap)

  def bold: View =
    amended(L.fontWeight.bold)

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
    modified(L.position.relative)

  def shadow(shadow: BoxShadow): View =
    modified(L.boxShadow(shadow.css))

  def shadow: View =
    shadow(BoxShadow.md)

  def fontMono: View =
    amended(cls("font-mono"))

  def opacity(d: Double): View =
    amended(L.opacity(d))

  def zIndex(level: Int): View =
    amended(L.zIndex(level))

  def stroke(color: Color): View =
    amended(L.customStyle("stroke")(color.css))

  def fill(color: Color): View =
    amended(L.customStyle("fill")(color.css))

  def textCenter =
    amended(L.textAlign.center)

  def textRight =
    amended(L.textAlign.right)

  def textLeft =
    amended(L.textAlign.left)

  def textJustify =
    amended(L.textAlign.justify)

  def lineHeight(px: Int): View =
    amended(L.lineHeight(s"${px}px"))

  // # Alignment

  def selfStart: View =
    amended(L.alignSelf.flexStart)

  def selfEnd: View =
    amended(L.alignSelf.flexEnd)

  def selfCenter: View =
    amended(L.alignSelf.center)

  def selfStretch: View =
    amended(L.alignSelf.stretch)

  //  def flex: View =
//    modified(L.display.flex)

  def noShrink: View = amended(L.flexShrink(0))

  def intrinsicHeight(heightVar: Var[Double]): View =
    modified(
      L.onMountCallback { el =>
        heightVar.set(el.thisNode.ref.getBoundingClientRect().height)
      }
    )

  def overflowYScroll: View =
    modified(L.overflowY.scroll)

  def overflowEllipsis: View =
    amended(
      L.whiteSpace.nowrap,
      L.overflow.hidden,
      L.textOverflow.ellipsis
    )

  def overflowEllipsis(maxLines: Int): View =
    amended(
      L.customStyle("-webkit-line-clamp")(maxLines.toString),
      L.customStyle("line-clamp")(maxLines.toString),
      L.customStyle("-webkit-box-orient")("vertical"),
      L.customStyle("display")("-webkit-box"),
      L.overflow.hidden,
      L.textOverflow.ellipsis
    )

  def overflowHidden: View =
    amended(L.overflow.hidden)

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
