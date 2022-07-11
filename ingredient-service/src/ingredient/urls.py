from django.urls import path

from ingredient.views import IngredientsView


urlpatterns = [
    path('', IngredientsView.as_view())
]
