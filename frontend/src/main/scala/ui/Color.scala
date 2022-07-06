package ui

import animus.{Animatable, Spring}

final case class Color(
  red: Double = 0.0,
  green: Double = 0.0,
  blue: Double = 0.0,
  alpha: Double = 1.0
) {

  lazy val css: String = {
    val r = (red * 255).toInt
    val g = (green * 255).toInt
    val b = (blue * 255).toInt
    s"rgba($r, $g, $b, $alpha)"
  }

  def withAlpha(alpha: Double): Color = copy(alpha = alpha)

  def transparent: Color = withAlpha(0.0)
}

object Color {
  def transparent: Color =
    Color(alpha = 0.0)

  def fromHex(hex: String): Color = {
    val hexWithoutHash = hex.drop(1)
    val r              = Integer.parseInt(hexWithoutHash.substring(0, 2), 16)
    val g              = Integer.parseInt(hexWithoutHash.substring(2, 4), 16)
    val b              = Integer.parseInt(hexWithoutHash.substring(4, 6), 16)
    Color(r / 255.0, g / 255.0, b / 255.0)
  }

  object Stone {
    val `50`: Color  = Color.fromHex("#fafaf9")
    val `100`: Color = Color.fromHex("#f5f5f4")
    val `200`: Color = Color.fromHex("#e7e5e4")
    val `300`: Color = Color.fromHex("#d6d3d1")
    val `400`: Color = Color.fromHex("#a8a29e")
    val `500`: Color = Color.fromHex("#78716c")
    val `600`: Color = Color.fromHex("#57534e")
    val `700`: Color = Color.fromHex("#44403c")
    val `800`: Color = Color.fromHex("#292524")
    val `850`: Color = Color.fromHex("#1f1e1d")
    val `900`: Color = Color.fromHex("#1c1917")
  }

  object Red {
    val `50`: Color  = Color.fromHex("#fef2f2")
    val `100`: Color = Color.fromHex("#fee2e2")
    val `200`: Color = Color.fromHex("#fecaca")
    val `300`: Color = Color.fromHex("#fca5a5")
    val `400`: Color = Color.fromHex("#f87171")
    val `500`: Color = Color.fromHex("#ef4444")
    val `600`: Color = Color.fromHex("#dc2626")
    val `700`: Color = Color.fromHex("#b91c1c")
    val `800`: Color = Color.fromHex("#991b1b")
    val `900`: Color = Color.fromHex("#7f1d1d")
  }

  implicit val animatable: Animatable[Color] =
    new Animatable[Color] {
      val da: Animatable[Double] { type Anim = Spring } =
        Animatable.animatableDouble.asInstanceOf[Animatable[Double] { type Anim = Spring }]

      override type Anim = (da.Anim, da.Anim, da.Anim, da.Anim)

      override def toAnim(value: Color): (da.Anim, da.Anim, da.Anim, da.Anim) =
        (da.toAnim(value.red), da.toAnim(value.green), da.toAnim(value.blue), da.toAnim(value.alpha))

      override def fromAnim(anim: (da.Anim, da.Anim, da.Anim, da.Anim)): Color =
        Color(anim._1.value, anim._2.value, anim._3.value, anim._4.value)

      override def tick(anim: (da.Anim, da.Anim, da.Anim, da.Anim), time: Double): Boolean = {
        val a = da.tick(anim._1, time)
        val b = da.tick(anim._2, time)
        val c = da.tick(anim._3, time)
        val d = da.tick(anim._4, time)
        a && b && c && d
      }

      override def update(anim: (da.Anim, da.Anim, da.Anim, da.Anim), newValue: Color): Unit = {
        da.update(anim._1, newValue.red)
        da.update(anim._2, newValue.green)
        da.update(anim._3, newValue.blue)
        da.update(anim._4, newValue.alpha)
      }
    }
}
