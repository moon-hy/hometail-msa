from django.urls import path

from ingredient.views import Ingredients


urlpatterns = [
    path('', Ingredients.as_view())
]
