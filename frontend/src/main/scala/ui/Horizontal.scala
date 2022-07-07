package ui

import com.raquo.laminar.api.L._
import animus._
import com.raquo.laminar.api.L

final case class Horizontal(views: List[View], spacing: Int = 0) extends View {

  val size: Int = views.size

  def body: HtmlElement =
    div(
      // stretch
      cls("flex items-center"),
      views.zipWithIndex.map { case (view, idx) =>
        view.amend(
          Option.when(idx < size - 1)(marginRight(s"${spacing}px"))
        )
      }
    )
}

final case class HorizontalSignal($views: Signal[List[View]], spacing: Int = 0) extends View {
  def body: HtmlElement =
    div(
      cls("flex items-center"),
      children <-- $views.splitTransition(_.viewId) { case (_, view, _, t) =>
        view.amend(
          marginRight(s"${spacing}px"),
          t.height,
          t.opacity
        )
      }
    )
}

final case class Vertical(
  views: List[View],
  spacing: Int = 0,
  alignment: HorizontalAlignment = HorizontalAlignment.Left
) extends View {
  val size: Int = views.size

  def body: HtmlElement =
    div(
      cls(s"flex flex-col"), //  ${alignment.toVertical.css}"),
      views.zipWithIndex.map { case (view, idx) =>
        view.amend(
          Option.when(idx < size - 1)(marginBottom(s"${spacing}px"))
        )
      }
    )
}

final case class VerticalSignal($views: Signal[List[View]], spacing: Int = 0) extends View {

  def body: HtmlElement =
    div(
      cls("flex flex-col"),
      children <-- $views.splitTransition(_.viewId) { case (_, view, _, t) =>
        view.amend(
          marginBottom(s"${spacing}px"),
          t.height,
          t.opacity
        )
      }
    )
}

final case class ZStack(views: List[View], alignment: Alignment = Alignment.center) extends View {

  val size: Int = views.size

  // stack the views along the z-axis
  def body: HtmlElement =
    div(
      cls("grid items-center justify-center"),
//      cls("flex"),
      position.relative,
      views.map { view =>
        div(
          cls(s"flex ${alignment.css} h-full"),
          pointerEvents.none,
          div(view.amend(pointerEvents.auto)),
          L.customStyle("grid-area")(s"1/1/1/1")
        )
      }
    )

}

object VerticalAlignment {
  case object Top    extends VerticalAlignment
  case object Middle extends VerticalAlignment
  case object Bottom extends VerticalAlignment
}

sealed trait VerticalAlignment extends Product with Serializable { self =>
  def css: String =
    self match {
      case VerticalAlignment.Top    => "items-start"
      case VerticalAlignment.Middle => "items-center"
      case VerticalAlignment.Bottom => "items-end"
    }

  def toHorizontal =
    self match {
      case VerticalAlignment.Top    => HorizontalAlignment.Left
      case VerticalAlignment.Middle => HorizontalAlignment.Center
      case VerticalAlignment.Bottom => HorizontalAlignment.Right
    }
}

sealed trait HorizontalAlignment extends Product with Serializable { self =>
  def css: String =
    self match {
      case HorizontalAlignment.Left   => "justify-start"
      case HorizontalAlignment.Center => "justify-center"
      case HorizontalAlignment.Right  => "justify-end"
    }

  def toVertical =
    self match {
      case HorizontalAlignment.Left   => VerticalAlignment.Top
      case HorizontalAlignment.Center => VerticalAlignment.Middle
      case HorizontalAlignment.Right  => VerticalAlignment.Bottom
    }
}

object HorizontalAlignment {
  case object Left   extends HorizontalAlignment
  case object Center extends HorizontalAlignment
  case object Right  extends HorizontalAlignment
}

final case class Alignment(
  vertical: VerticalAlignment,
  horizontal: HorizontalAlignment
) {
  def css: String =
    s"${vertical.css} ${horizontal.css}"
}

object Alignment {
  def topLeft: Alignment     = Alignment(VerticalAlignment.Top, HorizontalAlignment.Left)
  def top: Alignment         = Alignment(VerticalAlignment.Top, HorizontalAlignment.Center)
  def topRight: Alignment    = Alignment(VerticalAlignment.Top, HorizontalAlignment.Right)
  def left: Alignment        = Alignment(VerticalAlignment.Middle, HorizontalAlignment.Left)
  def center: Alignment      = Alignment(VerticalAlignment.Middle, HorizontalAlignment.Center)
  def right: Alignment       = Alignment(VerticalAlignment.Middle, HorizontalAlignment.Right)
  def bottomLeft: Alignment  = Alignment(VerticalAlignment.Bottom, HorizontalAlignment.Left)
  def bottom: Alignment      = Alignment(VerticalAlignment.Bottom, HorizontalAlignment.Center)
  def bottomRight: Alignment = Alignment(VerticalAlignment.Bottom, HorizontalAlignment.Right)
}
