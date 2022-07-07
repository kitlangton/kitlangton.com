package ui

import components.Component
import zio.start.Icons
import com.raquo.laminar.api.L._
import animus._

case class Egghead() extends Component {
  def body =
    div(
      cls("h-screen bg-gray-900 text-gray-100"),
      View
        .vertical(
          NavBar,
          View
            .vertical(32, HorizontalAlignment.Right)(
              EggheadTitles,
              View
                .horizontal(
                  View
                    .horizontal(
                      View(Icons.mailFill).fill(Color.Gray.`300`).padding(right = 8),
                      "you@company.com".color(Color.Gray.`400`)
                    )
                    .padding(10)
                    .padding(right = 90),
                  View.spacer,
                  "Start learning"
                    .fontSize(FontSize.sm)
                    .medium
                    .color(Color.Gray.`100`)
                    .padding(20, 12)
                    .background(Color.Blue.`600`)
                )
                .border(1, Color.Gray.`600`.css, 8)
                .selfCenter
            )
            .center
        )
        .heightFull
    )

  private def NavBar =
    View
      .horizontal(12)(
        "egghead.io".bold
          .fontSize(FontSize.xl),
        "Browse".fontSize(FontSize.sm),
        View.spacer,
        View
          .horizontal(12)(
            "Build Your Dev Portfolio"
              .padding(8, 3)
              .background(Color.Blue.`600`)
              .rounded(4),
            "Sign In"
          )
          .fontSize(FontSize.sm)
      )
      .padding(24, 12)
      .borderBottom(1, Color.Gray.`800`)

  private def EggheadTitles =
    View
      .vertical(8)(
        "Concise Full Stack Courses for Busy Web Developers"
          .fontSize(FontSize.`4xl`)
          .width(550)
          .textCenter
          .lineHeight(40)
          .bold,
        "high-quality video tutorials and curated learning resources with zero cruft"
          .color(Color.Yellow.`400`)
      )

}
