package ui

import components.Component
import com.raquo.laminar.api.L._
import animus._
import com.raquo.laminar.api.L
import zio.start.Icons

object GitHubView extends View {

  override def body: HtmlElement =
    View
      .box(
        View.horizontal(
          ProjectInfo,
          View
            .horizontal(
              View
                .horizontal(4)(
                  View(Icons.star).width(16).stroke(Color.Gray.`400`),
                  "Star"
                )
                .paddingH(12),
              View
                .rect(1, 24)
                .background(Color.Gray.`600`),
              View(Icons.chevronDownFill)
                .width(16)
                .stroke(Color.Gray.`400`)
                .paddingH(6)
            )
            .fontXSmall
            .medium
            .background(Color.Gray.`700`)
            .border(1, Color.Gray.`600`.css, 4)
            .selfStart
        ),
        12,
        Color.Gray.`800`,
        Color.Gray.`700`
      )
      .center
      .color(Color.Gray.`200`)

  private def ProjectInfo = {
    val activated       = Var(false)
    var intrinsicHeight = Var(0.0)
    View.vertical(
      "kitlangton/zio-start".medium
        .colorHover(Color.Blue.`400`)
        .cursorPointer
        .onClick(activated.update(!_)),
      "Update your Scala dependencies interactively" //
        .fontSmall
        .color(Color.Gray.`400`),
      View.rect(height = 12),
      View
        .horizontal(12)(
          View
            .horizontal(4)(
              View
                .rect(10, 10)
                .background(Color.Red.`500`)
                .border(1, Color.Red.`400`.css, 12),
              "Scala"
            ),
          View
            .horizontal(4)(
              View(Icons.star).stroke(Color.Gray.`400`).width(18),
              "68"
            ),
          "1 issue needs help",
          s"Updated Jul 4"
//          intrinsicHeight.map(_.toString)
        )
        .fontXSmall
        .intrinsicHeight(intrinsicHeight)
        .minHeight(activated.signal.map(if (_) 120.0 else intrinsicHeight.now()).spring)
        .color(Color.Gray.`400`)
    )
  }
}
