package ui

import components.Component
import com.raquo.laminar.api.L._
import animus._

object GitHubView extends View {

  override def body: HtmlElement =
    View
      .box(
        View.vertical(
          "kitlangton/zio-start".bold,
          "Update your Scala dependencies interactively" //
            .fontSmall
        ),
        12,
        Color.Gray.`800`,
        Color.Gray.`700`
      )
      .center
      .color(Color.Gray.`200`)

}
