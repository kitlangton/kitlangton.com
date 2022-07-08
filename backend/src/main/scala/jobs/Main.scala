package jobs

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.{HttpRequest, HttpRequestInitializer}
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import zio.{ZIO, ZIOAppDefault}
import zio.json._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.jdk.CollectionConverters._

object Main extends ZIOAppDefault {
  val initializer =
    new HttpRequestInitializer() {
      override def initialize(request: HttpRequest): Unit =
        request.setInterceptor(intercepted => intercepted.getUrl.set("key", "AIzaSyCC-y4bg26K0X_YByTM1ywbhkSf0wPwOt0"))
    }

  lazy val youtubeService: YouTube =
    new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport, GsonFactory.getDefaultInstance, initializer)
      .setApplicationName("kitlangton.com")
      .build

  // shared/src/main/resources/youtube

  def getVideos = ZIO.attempt {
    // Define and execute the API request
    val request  = youtubeService.playlistItems.list(List("contentDetails", "id", "snippet", "status").asJava)
    val response = request.setMaxResults(50L).setPlaylistId("PLvdARMfvom9C8ss18he1P5vOcogawm5uC").execute
    println(JsonParsing.caseClassify(response.toString))
    println(response)
    val parsed = response.toString.fromJson[PayloadRoot]
    println(parsed)
    val videos = parsed.toOption.get.items.map(YouTubeVideo.fromItem)

    videos
//    videos.toJsonPretty
//
//    System.out.println(videos.mkString("\n"))
  }

  val run =
    for {
      videos <- getVideos
      _      <- ZIO.writeFile("./shared/src/main/resources/youtube/videos.json", videos.toJsonPretty)
      _      <- ZIO.debug(s"Wrote ${videos.size} videos to ./shared/src/main/resources/youtube/videos.json")
    } yield ()

}

final case class PayloadRoot(
  etag: String,
  nextPageToken: Option[String],
  items: List[Items],
  pageInfo: PageInfo,
  kind: String
)

object PayloadRoot {
  implicit val codec: JsonCodec[PayloadRoot] = DeriveJsonCodec.gen
}

final case class Items(
  etag: String,
  snippet: Snippet,
  id: String,
  status: Status,
  contentDetails: ContentDetails,
  kind: String
)

object Items {
  implicit val codec: JsonCodec[Items] = DeriveJsonCodec.gen
}

final case class Snippet(
  resourceId: ResourceId,
  description: String,
  videoOwnerChannelId: Option[String],
  channelTitle: String,
  title: String,
  channelId: String,
  thumbnails: Thumbnails,
  position: Int,
  playlistId: String,
  publishedAt: String,
  videoOwnerChannelTitle: Option[String]
)

object Snippet {
  implicit val codec: JsonCodec[Snippet] = DeriveJsonCodec.gen
}

final case class ResourceId(
  kind: String,
  videoId: String
)

object ResourceId {
  implicit val codec: JsonCodec[ResourceId] = DeriveJsonCodec.gen
}

final case class Thumbnails(
  standard: Option[Standard],
  maxres: Option[Maxres],
  medium: Option[Medium],
  default: Option[Default],
  high: Option[High]
)

object Thumbnails {
  implicit val codec: JsonCodec[Thumbnails] = DeriveJsonCodec.gen
}

final case class Standard(
  height: Int,
  url: String,
  width: Int
)

object Standard {
  implicit val codec: JsonCodec[Standard] = DeriveJsonCodec.gen
}

final case class Maxres(
  height: Int,
  url: String,
  width: Int
)

object Maxres {
  implicit val codec: JsonCodec[Maxres] = DeriveJsonCodec.gen
}

final case class Medium(
  height: Int,
  url: String,
  width: Int
)

object Medium {
  implicit val codec: JsonCodec[Medium] = DeriveJsonCodec.gen
}

final case class Default(
  height: Int,
  url: String,
  width: Int
)

object Default {
  implicit val codec: JsonCodec[Default] = DeriveJsonCodec.gen
}

final case class High(
  height: Int,
  url: String,
  width: Int
)

object High {
  implicit val codec: JsonCodec[High] = DeriveJsonCodec.gen
}

final case class Status(
  privacyStatus: String
)

object Status {
  implicit val codec: JsonCodec[Status] = DeriveJsonCodec.gen
}

final case class ContentDetails(
  videoId: String,
  videoPublishedAt: Option[String]
)

object ContentDetails {
  implicit val codec: JsonCodec[ContentDetails] = DeriveJsonCodec.gen
}

final case class PageInfo(
  resultsPerPage: Int,
  totalResults: Int
)

object PageInfo {
  implicit val codec: JsonCodec[PageInfo] = DeriveJsonCodec.gen
}

final case class YouTubeVideo(
  id: String,
  title: String,
  description: String,
  thumbnails: Thumbnails,
  publishedAt: LocalDateTime
) {
  def url: String = s"https://youtube.com/watch?v=${id}"
}

object YouTubeVideo {

  def fromItem(item: Items): YouTubeVideo =
    YouTubeVideo(
      item.id,
      item.snippet.title,
      item.snippet.description,
      item.snippet.thumbnails,
      LocalDateTime.parse(item.snippet.publishedAt, DateTimeFormatter.ISO_DATE_TIME)
    )

  implicit val codec: JsonCodec[YouTubeVideo] = DeriveJsonCodec.gen
}
