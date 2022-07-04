package zio.start

import com.raquo.laminar.api.L._
import components.Component
import animus._

object PrimaryPage extends Component {
  def body =
    div(
      cls("h-screen bg-gray-900 text-gray-100 font-mono overflow-scroll"),
      div(
        cls("max-w-screen-sm md:max-w-screen-lg mx-auto flex flex-col h-full"),
        NavBar,
        div(
          cls("px-8 pb-16"),
          cls("grid grid-cols-1 md:grid-cols-2 md:gap-16"),
          div(
            WhomstSection,
            LinksSection,
            OtherSection
          ),
          OpenSourceSection
        )
//        div(cls("h-full")),
//        Footer
      )
    )

  private def NavBar =
    div(
      cls("px-8 mt-10 text-xl font-bold tracking-widest"),
      cls("justify-between flex items-center whitespace-nowrap"),
      cls("text-yellow-500"),
      div(
        "KIT LANGTON"
      ),
      SolidBar().amend(cls("mx-4")),
      div("DOT COM")
    )

  def Footer =
    div(
      cls("p-8 text-right font-bold text-gray-500"),
      "GitHub"
    )

  private def OpenSourceSection =
    div(
      Header("Open Source Projects"),
      Project(
        "ZIO Start",
        "A web-based wizard for generating new ZIO applications.",
        "https://zio-start.surge.sh",
        List("NEW!")
      ),
      Project(
        "Scala Update",
        "A CLI for interactively updating your Scala dependencies.",
        "https://github.com/kitlangton/scala-update",
        List("NEW!")
      ),
      Project(
        "Animus",
        "A spring animation library for Scala.js and Laminar.",
        "https://github.com/kitlangton/animus"
      ),
      Project(
        "Parallel For",
        "Automatically parallelize your for-comprehensions at compile time.",
        "https://github.com/kitlangton/parallel-for"
      ),
      Project(
        "ZIO Magic",
        "Friendly, compile-time dependency injection for ZIO. Integrated into ZIO 2.",
        "https://github.com/kitlangton/zio-magic"
      )
    )

  private def LinksSection =
    div(
      cls("flex items-center"),
      Project("Twitter", "", "https://twitter.com/kitlangton"),
      div(cls("w-8")),
      Project("GitHub", "", "https://github.com/kitlangton"),
      div(cls("w-8")),
      Project(
        "Email",
        "",
        "mailto:kit.langton+my-little-old-website@gmail.com?subject=I%20Hate%20Your%20Stupid%20Website&body=Dearest%20Kit,"
      )
    )

  private def WhomstSection =
    div(
      Header("WHOMST?"),
      div(
        cls("mt-8 text-gray-300 leading-relaxed text-justify"),
        div(
          em("Greetings!"),
          " My name is Kit. I am a programmer, teacher, and designer ",
          span("[sic]", cls("text-gray-400")),
          "."
        ),
        div(
          cls("mt-4"),
          "I mostly write Scala at ",
          link("Ziverge", "https://ziverge.com"),
          ", where we're trying to build ",
          em("The Future of Functional Programming"),
          " with ",
          link("ZIO", "https://zio.dev"),
          "."
        ),
        div(
          cls("mt-4"),
          "Below are some links you might find handy."
        )
      )
    )

  private def OtherSection =
    div(
      Header("TEACHING"),
      div(
        cls("mt-8 text-gray-300 leading-relaxed text-justify"),
        div(
          "Every Friday, ",
          link("Adam Fraser", "https://twitter.com/adamfraser", "text-gray-400"),
          " and I teach a free, public Scala course called ",
          link("Zymposium", "https://www.meetup.com/zio-meetup-sf/"),
          "."
        ),
        div(
          cls("mt-4 italic"),
          "Come check it out!"
        )
      )
    )

  private def link(name: String, url: String, color: String = "text-yellow-500") =
    a(
      cls(s"font-bold $color hover:text-yellow-400 hover:underline cursor-pointer"),
      name,
      target("_blank"),
      href(url)
    )
}

final case class HorizontalRule() extends Component {
  def body =
    div(cls("h-px bg-gray-800 w-full"))
}

final case class SolidBar() extends Component {
  def body =
    div(
      cls("h-4 w-full bg-gray-800")
    )
}

final case class Header(name: String) extends Component {
  def body =
    div(
      cls("font-bold tracking-widest"),
      div(cls("h-10")),
      HorizontalRule(),
      cls("font-bold tracking-widest"),
      div(cls("h-10")),
      div(
        div(
          cls("whitespace-nowrap text-gray-200"),
          name.toUpperCase
        ),
        SolidBar().amend(cls("ml-4")),
        cls("flex justify-between items-center")
      )
    )
}

case class Project( //
  name: String,
  description: String,
  url: String,
  labels: List[String] = Nil
) extends Component {
  def body =
    div(
      cls("mt-8"),
      cls(
        "stroke-gray-400 fill-gray-400",
        "hover:stroke-yellow-600 hover:fill-yellow-600 hover:text-yellow-400 cursor-pointer"
      ),
      a(
        target("_blank"),
        href(url),
        div(
          cls(
            "font-bold tracking-wider flex items-center",
            "hover:text-yellow-400 cursor-pointer"
          ),
          div(
            cls("flex items-center"),
            div(
              cls("w-4 mr-2"),
              Icons.externalLink
            ),
            div(
              name
            )
          ),
          div(
            cls("flex items-center"),
            labels.map { label =>
              div(
                cls("ml-2 p-0.5 px-1 text-xs bg-gray-800 text-gray-400 rounded-sm font-normal"),
                label
              )
            }
          )
        ),
        div(
          cls("mt-2 text-gray-300"),
          description
        )
      )
    )
}
