package ui

object FontSize {

  val xs    = FontSize("0.75rem")
  val sm    = FontSize("0.875rem")
  val base  = FontSize("1rem")
  val lg    = FontSize("1.125rem")
  val xl    = FontSize("1.25rem")
  val `2xl` = FontSize("1.5rem")
  val `3xl` = FontSize("1.875rem")
  val `4xl` = FontSize("2.25rem")
  val `5xl` = FontSize("3rem")
  val `6xl` = FontSize("3.75rem")
  val `7xl` = FontSize("4.5rem")
  val `8xl` = FontSize("6rem")
  val `9xl` = FontSize("8rem")

}

final case class FontSize(size: String)
