fun makeMyWishList(wishList: Map<String, Int>, limit: Int): MutableMap<String, Int> {
    // write here

    val outList = mutableMapOf<String,Int>()

    for ((key, value) in wishList) {
        if (value <= limit) {
            outList[key] = value
        }
    }

    return outList

}