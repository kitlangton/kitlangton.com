package ui

import animus._
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L._
import components.Component
import zio.start.Icons

import scala.language.implicitConversions
import scala.util.Random

object Main extends Component {

  def wrap(htmlElement: HtmlElement): View =
    new View {
      override def body: HtmlElement = htmlElement
    }

  val randomPadding: Signal[Double] =
    EventStream
      .periodic(1000)
      .map(_ => Random.nextInt(8) + 1)
      .startWith(3)
      .map(_.toDouble)

  def body: HtmlElement =
    div(
      cls("h-screen bg-stone-900 text-stone-100"),
      GitHubView
//      Calendar
//      Taxes
//      Egghead()
//      Ecosystem.center
//      Typefully.center
//      Raycast
    )

  def Ecosystem =
    View
      .vertical(
        Library(true, "zio-cache"),
        Library(false, "zio-ampq"),
        Details,
        Library(true, "caliban"),
        Library(true, "sttp"),
        Library(false, "zio-query"),
        Library(false, "zio-app")
      )
      .border(1, 1, 0, 1, Color.Stone.`700`.css, 8)
      .borderTop(1, Color.Stone.`600`)
      .width(600)
      .shadow(BoxShadow.lg)

  def Details =
    View
      .vertical(
        "Depends On".medium.fontSize(FontSize.sm),
        View
          .vertical(
            Dependency("zio-streams"),
            Dependency("zio-test"),
            Dependency("zio-interop-reactive-streams")
          )
          .left(4)
      )
      .padding(12)
//      .height(0)
//      .height(randomPadding.spring)
      .border(0, 0, 1, 0, Color.Stone.`600`.css, 0)

  private def Dependency(name: String): View =
    name.color(Color.Stone.`300`)

  private def Library(updated: Boolean, name: String) = {
    val iconView =
      if (updated)
        View(Icons.checkmark).stroke(Color.Green.`500`)
      else
        View(Icons.exclamationSolid).fill(Color.Amber.`500`)
    View
      .horizontal(8)(
        iconView,
        View.text(name).medium
      )
      .padding(8)
      .background(Color.Stone.`800`)
      .border(0, 0, 1, 0, Color.Stone.`700`.css, 0)
  }

  def Typefully =
    View
      .vertical(12)(
        View
          .horizontal(8)(
            "Tomorrow".medium,
            "July 7".opacity(0.5)
          )
          .fontSize(FontSize.sm)
          .padding(left = 8),
        Toot,
        OnlyTimeToot,
        OnlyTimeToot,
        Toot,
        Toot,
        OnlyTimeToot
      )
      .width(500)

  private def Toot =
    View
      .horizontal(
        View.vertical(4)(
          "5:00 pm"
            .color(Color.Zinc.`500`),
          "My zettelkasten is fallow!"
            .color(Color.Zinc.`200`)
            .paddingV(4)
        )
      )
      .padding(16, 12)
//      .borderLeft(3, Color.Slate.`600`)
      .background(Color.Zinc.`900`)
      .border(1, Color.Slate.`600`.css, 8)
      .fontSize(FontSize.sm)

  def OnlyTimeToot =
    "5:00 pm"
      .color(Color.Zinc.`500`)
      .padding(16, 12)
      .border(1, Color.Slate.`600`.css, 8)
      .fontSize(FontSize.sm)

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
        View.separatorH(1, Color.Stone.`500`),
        View
          .stack(Alignment.bottomRight)(
            CommandsAndSuggestion.width(550),
            CommandMenu.zIndex(100).relative
            //.absolute(bottom = Some(0), right = Some(0))
          )
//          .relative
          .padding(12)
      )
      //        .blur(80)
      .background(Color.Stone.`900`.withAlpha(0.95))
      .border(1, "gray", 8)
      .shadow(BoxShadow.lg)
//      .width(600)
      .centerH
      .padding(top = 48)

  private def CommandMenu =
    View
      .horizontal(
        "Open Command"
          .fontSize(FontSize.sm)
          .medium
          .padding(12, 3),
        View.separatorV(1, Color.Stone.`500`).height(30),
        ">_"
          .padding(12, 3)
          .fontMono
          .fontSize(FontSize.xs)
          .medium
      )
      .background(Color.Stone.`700`)
      .border(1, Color.Stone.`500`.css, 6)

  private def CommandsAndSuggestion =
    View
      .vertical(
        View
          .vertical(
            View
              .text("Suggestions")
              .medium
              .fontSize(FontSize.xs)
              .padding(top = 12, bottom = 6)
              .color(Color.Stone.`400`)
              .left(4),
            row(false, "Toggle Floating Notes", "Floating Notes"),
            row(true, name = "Store", subtitle = "Raycast", alias = Some("st")),
            row(name = "Open Typefully", subtitle = "Typefully")
          ),
        View
          .vertical(
            View
              .text("Commands")
              .medium
              .fontSize(FontSize.xs)
              .padding(top = 12, bottom = 6)
              .color(Color.Stone.`400`)
              .left(4),
            View.vertical(
              randomPadding.map(_.toInt).map { total =>
                List.tabulate(total)(n =>
                  row(false, s"Command ${n + 1}", "Floating Notes")
                    .id(s"command-$n")
                )
              //                  row(name = "Store", subtitle = "Raycast"),
              //                  row(name = "Open Typefully", subtitle = "Typefully")
              }
            )
          )
      )

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
