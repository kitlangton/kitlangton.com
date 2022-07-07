package ui

import components.Component
import com.raquo.laminar.api.L._
import animus._

object Taxes extends View {
  override def body: HtmlElement =
    View
      .vertical(
        View
          .horizontal(
            "A".width(120),
            "B".width(80),
            "C".width(50),
            "D".width(80),
            "E".width(80)
          )
          .fontSize(FontSize.xs)
          .medium
          .color(Color.Gray.`200`),
        View
          .horizontal(
            Segment(120, Color.Blue.`700`),
            Segment(80, Color.Blue.`600`, true),
            Segment(50, Color.Blue.`700`, true),
            Segment(80, Color.Blue.`700`, true),
            Segment(80, Color.Blue.`700`, true)
          )
          .border(1, Color.Blue.`500`.css, 4)
      )
      .center

  private def Segment(width: Int, color: Color, hasBorder: Boolean = false) =
    View.empty
      .height(25)
      .width(width)
      .background(color)
      .borderLeft(if (hasBorder) 1 else 0, Color.Blue.`500`)
}
