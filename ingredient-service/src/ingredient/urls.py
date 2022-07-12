from django.urls import path

from ingredient.views import IngredientView, IngredientsView


urlpatterns = [
    path('/', IngredientsView.as_view()),
    path('/<int:pk>', IngredientView.as_view())
]
