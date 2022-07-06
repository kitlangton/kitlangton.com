package ui

import animus._
import com.raquo.laminar.api.L._
import components.Component

import scala.language.implicitConversions
import scala.util.Random

object Main extends Component {

  def wrap(htmlElement: HtmlElement): View =
    new View {
      override def body: HtmlElement = htmlElement
    }

  val randomPadding: Signal[Int] =
    EventStream
      .periodic(1000)
      .map(_ => Random.nextInt(8) + 1)
      .startWith(3)

  def body: HtmlElement =
    div(
      cls("h-screen bg-gray-900 text-gray-100"),
      Raycast
    )

  private def Raycast =
    View
      .vertical(
        View
          .horizontal(
            View(
              div(
                height("18px"),
                width("1px"),
                backgroundColor(Color.Stone.`200`.css)
              )
            ),
            "Search for apps and commands...".color(Color.Stone.`500`)
          )
          .padding(12),
        wrap(
          div(
            cls("h-px w-full bg-gray-500")
          )
        ),
        View
          .vertical(
            View
              .text("Suggestions")
              .medium
              .fontSize(FontSize.xs)
              .padding(bottom = 6)
              .color(Color.Stone.`400`)
              .left(4),
            row(true, "Toggle Floating Notes", "Floating Notes"),
            row(name = "Store", subtitle = "Raycast", alias = Some("st")),
            row(name = "Open Typefully", subtitle = "Typefully")
          )
          .padding(12)
          .padding(top = 8),
        View
          .vertical(
            View
              .text("Commands")
              .medium
              .fontSize(FontSize.xs)
              .padding(bottom = 6)
              .color(Color.Stone.`400`)
              .left(4),
            View.vertical(
              randomPadding.map { total =>
                List.tabulate(total)(n =>
                  row(false, s"Command ${n + 1}", "Floating Notes")
                    .id(s"command-$n")
                )
              //                  row(name = "Store", subtitle = "Raycast"),
              //                  row(name = "Open Typefully", subtitle = "Typefully")
              }
            )
          )
          .padding(0, 12, 12, 12)
        //            .padding(top = 8)
      )
      .width(600)
      //        .blur(80)
      .background(Color.Stone.`900`.withAlpha(0.95))
      .border(1, "gray", 8)
      .shadow(BoxShadow.lg)
      .center

  private def row(
    isSelected: Boolean = false,
    name: String,
    subtitle: String,
    alias: Option[String] = None
  ): View = {
    val isHover = Var(false)
    View
      .horizontal(12)(
        name,
        subtitle.color(Color.Stone.`400`),
        alias.map { alias =>
          View
            .text(alias)
            .color(Color.Stone.`400`)
            .paddingH(6)
            .border(1, Color.Stone.`600`.css, 4)
            .fontSize(FontSize.xs)
        }.getOrElse(View.empty),
        View.spacer,
        "Command".color(Color.Stone.`400`)
      )
      .hover(isHover)
      .padding(8)
      .background {
        isHover.signal.map { hover =>
          if (isSelected) Color.Stone.`700`
          else if (hover) Color.Stone.`800`
          else Color.Stone.`800`.transparent
        }
      }
      .rounded(6)
      .fontSize(FontSize.sm)
  }
}

final case class BoxShadow(css: String) extends AnyVal

object BoxShadow {
  val sm: BoxShadow = BoxShadow("0 1px 2px 0 rgb(0 0 0 / 0.05)")
  val md: BoxShadow = BoxShadow("0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1)")
  val lg: BoxShadow = BoxShadow("0 10px 15px -3px rgb(0 0 0 / 0.1), 0 4px 6px -4px rgb(0 0 0 / 0.1)")
  val xl: BoxShadow = BoxShadow("0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1)")
}
