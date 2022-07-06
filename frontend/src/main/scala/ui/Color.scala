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

  object Slate {
    val `50`: Color  = Color.fromHex("#f8fafc")
    val `100`: Color = Color.fromHex("#f1f5f9")
    val `200`: Color = Color.fromHex("#e2e8f0")
    val `300`: Color = Color.fromHex("#cbd5e1")
    val `400`: Color = Color.fromHex("#94a3b8")
    val `500`: Color = Color.fromHex("#64748b")
    val `600`: Color = Color.fromHex("#475569")
    val `700`: Color = Color.fromHex("#334155")
    val `800`: Color = Color.fromHex("#1e293b")
    val `900`: Color = Color.fromHex("#0f172a")
  }

  object Gray {
    val `50`: Color  = Color.fromHex("#f9fafb")
    val `100`: Color = Color.fromHex("#f3f4f6")
    val `200`: Color = Color.fromHex("#e5e7eb")
    val `300`: Color = Color.fromHex("#d1d5db")
    val `400`: Color = Color.fromHex("#9ca3af")
    val `500`: Color = Color.fromHex("#6b7280")
    val `600`: Color = Color.fromHex("#4b5563")
    val `700`: Color = Color.fromHex("#374151")
    val `800`: Color = Color.fromHex("#1f2937")
    val `900`: Color = Color.fromHex("#111827")
  }

  object Zinc {
    val `50`: Color  = Color.fromHex("#fafafa")
    val `100`: Color = Color.fromHex("#f4f4f5")
    val `200`: Color = Color.fromHex("#e4e4e7")
    val `300`: Color = Color.fromHex("#d4d4d8")
    val `400`: Color = Color.fromHex("#a1a1aa")
    val `500`: Color = Color.fromHex("#71717a")
    val `600`: Color = Color.fromHex("#52525b")
    val `700`: Color = Color.fromHex("#3f3f46")
    val `800`: Color = Color.fromHex("#27272a")
    val `900`: Color = Color.fromHex("#18181b")
  }

  object Neutral {
    val `50`: Color  = Color.fromHex("#fafafa")
    val `100`: Color = Color.fromHex("#f5f5f5")
    val `200`: Color = Color.fromHex("#e5e5e5")
    val `300`: Color = Color.fromHex("#d4d4d4")
    val `400`: Color = Color.fromHex("#a3a3a3")
    val `500`: Color = Color.fromHex("#737373")
    val `600`: Color = Color.fromHex("#525252")
    val `700`: Color = Color.fromHex("#404040")
    val `800`: Color = Color.fromHex("#262626")
    val `900`: Color = Color.fromHex("#171717")
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

  object Cyan {
    val `50`: Color  = Color.fromHex("#ecfeff")
    val `100`: Color = Color.fromHex("#cffafe")
    val `200`: Color = Color.fromHex("#a5f3fc")
    val `300`: Color = Color.fromHex("#67e8f9")
    val `400`: Color = Color.fromHex("#22d3ee")
    val `500`: Color = Color.fromHex("#06b6d4")
    val `600`: Color = Color.fromHex("#0891b2")
    val `700`: Color = Color.fromHex("#0e7490")
    val `800`: Color = Color.fromHex("#155e75")
    val `900`: Color = Color.fromHex("#164e63")
  }

  object Sky {
    val `50`: Color  = Color.fromHex("#f0f9ff")
    val `100`: Color = Color.fromHex("#e0f2fe")
    val `200`: Color = Color.fromHex("#bae6fd")
    val `300`: Color = Color.fromHex("#7dd3fc")
    val `400`: Color = Color.fromHex("#38bdf8")
    val `500`: Color = Color.fromHex("#0ea5e9")
    val `600`: Color = Color.fromHex("#0284c7")
    val `700`: Color = Color.fromHex("#0369a1")
    val `800`: Color = Color.fromHex("#075985")
    val `900`: Color = Color.fromHex("#0c4a6e")
  }

  object Blue {
    val `50`: Color  = Color.fromHex("#eff6ff")
    val `100`: Color = Color.fromHex("#dbeafe")
    val `200`: Color = Color.fromHex("#bfdbfe")
    val `300`: Color = Color.fromHex("#93c5fd")
    val `400`: Color = Color.fromHex("#60a5fa")
    val `500`: Color = Color.fromHex("#3b82f6")
    val `600`: Color = Color.fromHex("#2563eb")
    val `700`: Color = Color.fromHex("#1d4ed8")
    val `800`: Color = Color.fromHex("#1e40af")
    val `900`: Color = Color.fromHex("#1e3a8a")
  }

  object Orange {
    val `50`: Color  = Color.fromHex("#fff7ed")
    val `100`: Color = Color.fromHex("#ffedd5")
    val `200`: Color = Color.fromHex("#fed7aa")
    val `300`: Color = Color.fromHex("#fdba74")
    val `400`: Color = Color.fromHex("#fb923c")
    val `500`: Color = Color.fromHex("#f97316")
    val `600`: Color = Color.fromHex("#ea580c")
    val `700`: Color = Color.fromHex("#c2410c")
    val `800`: Color = Color.fromHex("#9a3412")
    val `900`: Color = Color.fromHex("#7c2d12")
  }

  object Amber {
    val `50`: Color  = Color.fromHex("#fffbeb")
    val `100`: Color = Color.fromHex("#fef3c7")
    val `200`: Color = Color.fromHex("#fde68a")
    val `300`: Color = Color.fromHex("#fcd34d")
    val `400`: Color = Color.fromHex("#fbbf24")
    val `500`: Color = Color.fromHex("#f59e0b")
    val `600`: Color = Color.fromHex("#d97706")
    val `700`: Color = Color.fromHex("#b45309")
    val `800`: Color = Color.fromHex("#92400e")
    val `900`: Color = Color.fromHex("#78350f")
  }

  object Yellow {
    val `50`: Color  = Color.fromHex("#fefce8")
    val `100`: Color = Color.fromHex("#fef9c3")
    val `200`: Color = Color.fromHex("#fef08a")
    val `300`: Color = Color.fromHex("#fde047")
    val `400`: Color = Color.fromHex("#facc15")
    val `500`: Color = Color.fromHex("#eab308")
    val `600`: Color = Color.fromHex("#ca8a04")
    val `700`: Color = Color.fromHex("#a16207")
    val `800`: Color = Color.fromHex("#854d0e")
    val `900`: Color = Color.fromHex("#713f12")
  }

  object Lime {
    val `50`: Color  = Color.fromHex("#f7fee7")
    val `100`: Color = Color.fromHex("#ecfccb")
    val `200`: Color = Color.fromHex("#d9f99d")
    val `300`: Color = Color.fromHex("#bef264")
    val `400`: Color = Color.fromHex("#a3e635")
    val `500`: Color = Color.fromHex("#84cc16")
    val `600`: Color = Color.fromHex("#65a30d")
    val `700`: Color = Color.fromHex("#4d7c0f")
    val `800`: Color = Color.fromHex("#3f6212")
    val `900`: Color = Color.fromHex("#365314")
  }

  object Green {
    val `50`: Color  = Color.fromHex("#f0fdf4")
    val `100`: Color = Color.fromHex("#dcfce7")
    val `200`: Color = Color.fromHex("#bbf7d0")
    val `300`: Color = Color.fromHex("#86efac")
    val `400`: Color = Color.fromHex("#4ade80")
    val `500`: Color = Color.fromHex("#22c55e")
    val `600`: Color = Color.fromHex("#16a34a")
    val `700`: Color = Color.fromHex("#15803d")
    val `800`: Color = Color.fromHex("#166534")
    val `900`: Color = Color.fromHex("#14532d")
  }

  object Emerald {
    val `50`: Color  = Color.fromHex("#ecfdf5")
    val `100`: Color = Color.fromHex("#d1fae5")
    val `200`: Color = Color.fromHex("#a7f3d0")
    val `300`: Color = Color.fromHex("#6ee7b7")
    val `400`: Color = Color.fromHex("#34d399")
    val `500`: Color = Color.fromHex("#10b981")
    val `600`: Color = Color.fromHex("#059669")
    val `700`: Color = Color.fromHex("#047857")
    val `800`: Color = Color.fromHex("#065f46")
    val `900`: Color = Color.fromHex("#064e3b")
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
