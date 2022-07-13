from django.urls import path

from ingredient.views import IngredientView, IngredientsView


urlpatterns = [
    path('/ingredients', IngredientsView.as_view()),
    path('/ingredients/<int:pk>', IngredientView.as_view())
]
