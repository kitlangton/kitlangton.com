package ui

import components.Component
import com.raquo.laminar.api.L._
import animus._

sealed trait DayOfWeek extends Product with Serializable

object DayOfWeek {
  case object Monday    extends DayOfWeek
  case object Tuesday   extends DayOfWeek
  case object Wednesday extends DayOfWeek
  case object Thursday  extends DayOfWeek
  case object Friday    extends DayOfWeek
  case object Saturday  extends DayOfWeek
  case object Sunday    extends DayOfWeek
}

sealed trait Month extends Product with Serializable

object Month {
  case object January   extends Month
  case object February  extends Month
  case object March     extends Month
  case object April     extends Month
  case object May       extends Month
  case object June      extends Month
  case object July      extends Month
  case object August    extends Month
  case object September extends Month
  case object October   extends Month
  case object November  extends Month
  case object December  extends Month
}

final case class DateInfo( //
  dayOfWeek: DayOfWeek,
  dayOfMonth: Int,
  month: Month
)

object Calendar extends View {

  override def body: HtmlElement =
    View
      .vertical(0)(
        View.horizontal(0)(Cal, Cal, Cal, Cal),
        View.horizontal(0)(Cal, Cal, Cal, Cal),
        View.horizontal(0)(Cal, Cal, Cal, Cal)
      )
      .center

  private def Cal =
    View
      .vertical(
        List.tabulate(5) { w =>
          View
            .horizontal(
              List.tabulate(7) { d =>
                DayView(d + (w * 7))
              }: _*
            )
        }: _*
      )
      .padding(2)
//      .background(Color.Gray.`600`)
      .border(1, Color.Gray.`700`.css, 0)

  private def DayView(d: Int) = {
    val hovering = Var(false)

    val $bg = hovering.signal.map {
      case true  => Color.Blue.`600`
      case false => Color.Gray.`700`
    }

    View
      .text(s"")
      .color(Color.Gray.`300`)
      .fontSize(FontSize.xs)
      .center
      .height(8)
      .width(8)
      .background($bg)
      .border(1, Color.Gray.`600`.css, 0)
      .padding(2)
      .hover(hovering)
  }

}
