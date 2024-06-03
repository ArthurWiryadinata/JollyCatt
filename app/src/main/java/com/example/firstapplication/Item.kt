package com.example.firstapplication

data class Item(
    val name: String,
    val price: String,
    val picture: Int,
    val descltl: String,
    val description: String,
    val id: Int
)

fun getSampleItems(): List<Item> {
    return listOf(
        Item("Persia",
            "10",
            R.drawable.persia_cat,
            "Wajahnya datar dan bulunya yang panjang menjadikan kucing ini banyak disukai orang.",
            "Meski termasuk kucing ras berbulu panjang, kucing Persia tidak membutuhkan banyak perawatan khusus. Namun, karena bulunya yang panjang, Anda harus rutin menyisir rambut untuk menghindari rambut kusut dan kutu di badan mereka.\n" +
                    "\n" +
                    "Dengan penampilannya yang anggun, kucing Persia ternyata sangat suka dipeluk dan tidur di pangkuan pemiliknya.",
            1),

        Item("Ragdoll",
            "12",
            R.drawable.ragdoll_cat,
            "Ingin punya kucing yang mengikuti Anda ke mana pun, menyambut dengan hangat saat Anda pulang",
            "Kucing ini memiliki bulu panjang, berwarna cerah, dan kedua matanya berwarna biru. Dengan penampilan yang elegan ini mereka sangatlah clingy dan lebih suka bermain dengan manusia dibandingkan dengan sesama kucing.\n" +
                    "\n" +
                    "Ragdoll juga termasuk dalam jenis yang baik hati, patuh, kooperatif, dan bisa bermain dengan anak-anak. ",
            2),

        Item("Munchkin",
            "12",
            R.drawable.munchkin_cat,
            "Kucing Munchkin merupakan model kucing yang memiliki ciri-ciri fisik unik, yaitu kaki pendek atau kaki yang lebih pendek dari rata-rata kucing lainnya. ",
            "Munchkin merupakan kucing yang penuh kasih sayang. Mereka tidak enggan menunjukkan rasa sayang pada pemiliknya, seperti minta dielus atau tidur dalam pangkuan pemiliknya.\n" +
                    "\n" +
                    "Jenis kucing ini juga umumnya mudah beradaptasi dan bergaul dengan orang asing. Meski kadang menunjukkan rasa takut, lama-kelamaan ia akan mudah bermain dengan manusia yang baru ditemuinya.",
            3),

        Item("Siam",
            "12",
            R.drawable.siam_cat,
            "Kucing ini sering disebut sebagai ‘pangeran kucing’ karena cara berjalannya, bentuk wajahnya, serta warna dan ketebalan bulunya. ",
            "Selain itu, kucing Siam terkenal karena cerewet. Ia sering mengeong sebagai bentuk komunikasi pada manusia. Kucing Siam juga punya banyak energi sehingga Anda sebaiknya menyediakan banyak mainan untuk menghabiskan energinya.\n" +
                    "\n" +
                    "Karena cukup aktif, kucing Siam terkadang lebih banyak menghabiskan makanan kucing untuk mengisi kembali energi mereka. ",
            4),
    )
}