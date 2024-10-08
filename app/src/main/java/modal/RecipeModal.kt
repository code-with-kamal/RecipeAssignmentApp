package modal

data class RecipeModal(
    val limit: Int?, // 30
    val recipes: List<Recipe?>?,
    val skip: Int?, // 0
    val total: Int? // 50
) {
    data class Recipe(
        val caloriesPerServing: Int?, // 300
        val cookTimeMinutes: Int?, // 15
        val cuisine: String?, // Italian
        val difficulty: String?, // Easy
        val id: Int?, // 1
        val image: String?, // https://cdn.dummyjson.com/recipe-images/1.webp
        val ingredients: List<String?>?,
        val instructions: List<String?>?,
        val mealType: List<String?>?,
        val name: String?, // Classic Margherita Pizza
        val prepTimeMinutes: Int?, // 20
        val rating: Double?, // 4.6
        val reviewCount: Int?, // 98
        val servings: Int?, // 4
        val tags: List<String?>?,
        val userId: Int? // 166

    )
}