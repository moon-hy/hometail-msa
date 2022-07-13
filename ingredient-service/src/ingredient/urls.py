from django.urls import path

from ingredient.views import CategoryView, IngredientView, IngredientsView


urlpatterns = [
    path('/ingredients', IngredientsView.as_view()),
    path('/ingredients/<int:pk>', IngredientView.as_view()),
    path('/categories', CategoryView.as_view()),
    
]
