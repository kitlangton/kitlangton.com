package ui

import animus._
import com.raquo.laminar.api.L._
import zio.start.Icons

object VideoSite extends View {

  override def body: HtmlElement =
    View
      .vertical(
        View
          .horizontal(8)(
            View(Icons.academicCap).stroke(Color.Gray.`500`).width(20),
            "SCALA SCHOOL".medium
          )
          .padding(24, 12)
          .borderBottom(1, Color.Gray.`800`),
        View
          .grid(2, 12)(
            videos.map(VideoCardView(_)): _*
          )
          .padding(24)
//          .center
      )
      .heightFull

  private def VideoCardView(video: Video) =
    View
      .box(
        View
          .vertical(6)(
            View.horizontal(6)(
              View(Icons.videoFill).fill(Color.Gray.`500`).width(14).noShrink,
              "VIDEO".fontXSmall.medium.color(Color.Gray.`400`)
            ),
            video.name.medium
//            View
//              .horizontal(8)(
//                View(Icons.videoFill).fill(Color.Gray.`500`).width(20).noShrink,
//                video.name.medium
//              )
//            video.description //
//              .overflowEllipsis(2)
//              .color(Color.Gray.`400`)
          ),
        8,
        Color.Gray.`800`,
        Color.Gray.`700`,
        4
      )

  final case class Video(
    name: String,
    description: String
  )

  lazy val videos = List(
    Video("Using Refs", "An introduction to the Ref type in ZIO."),
    Video("Introduction to ZIO", "An introduction to ZIO, starting from the basics."),
    Video("ZIO and the Effect System", "An introduction to the Effect System."),
    Video("Functional Programming with ZIO", "A tutorial on how to use ZIO."),
    Video(
      "Basic Concurrency",
      "An exploration of concurrency primitives in the ZIO Effect System. This is a very basic tutorial, but it will get you started."
    ),
    Video("Sane Local Dependency Workflow", "An improved workflow for local dependencies using sbt.")
  )
}
